package com.hyperdata.nifi.application.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hyperdata.nifi.application.FlowFileService;
import com.hyperdata.nifi.application.dto.FlowUploadRequestDto;
import com.hyperdata.nifi.application.dto.FlowUploadResponseDto;
import com.hyperdata.nifi.config.FileStorageProperties;
import com.hyperdata.nifi.domain.FlowFile;
import com.hyperdata.nifi.domain.repository.FlowFileRepository;
import com.hyperdata.nifi.exception.ErrorCode;
import com.hyperdata.nifi.exception.FileStorageException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlowFileServiceImpl implements FlowFileService {

    private final Path fileStorageLocation;
    private final FlowFileRepository flowFileRepository;

    @Autowired
    public FlowFileServiceImpl(FileStorageProperties fileStorageProperties, FlowFileRepository flowFileRepository) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
            .toAbsolutePath().normalize();
        this.flowFileRepository = flowFileRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException(ErrorCode.INVALID_FILE_NAME, "지정된 경로에 디렉토리를 생성할 수 없습니다. ");
        }
    }

    @Override
    @Transactional
    public FlowUploadResponseDto store(MultipartFile file,
        FlowUploadRequestDto flowUploadRequestDto) {

        /*
            TODO
            파일의 널 여부 확인 로직 추가
            전달 받은 파일명을 확인하여 유효하지 않은 문자열 포함 유무를 확인한 후 타겟 디렉토리에 파일 저장
         */

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new FileStorageException(ErrorCode.INVALID_FILE_NAME, "파일 이름에 유효하지 않은 문자가 있습니다. ");
        }

        fileName = flowUploadRequestDto.getMinifi() + "_" + fileName;
        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException(ErrorCode.INVALID_FILE_COPY, "파일 복사 도중에 IOException 발생했습니다. ");
        }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/downloadFile/")
            .path(fileName)
            .toUriString();

        // DB에 Flow template 메타 데이터 저장
        // minifi 내 동작하고 있는 flow는 하나라고 가정

        FlowFile flowFile = FlowFile.of(flowUploadRequestDto.getMinifi(), fileName, fileDownloadUri);
        // String id, String minifiID, String fileName, String fileDownloadUri, String fileContentType, Long fileSize

        flowFileRepository.save(flowFile);

        return FlowUploadResponseDto.from(flowFile);
    }

    @Override
    public Resource load(String minifiID) {
        /*
            minifi ID를 통해 filename을 얻고, 해당 파일을 서버에서 가져와서 반환
         */
        FlowFile flowFile = flowFileRepository.findByMinifiID(minifiID);
        String filename = flowFile.getFileName();

        Path file = fileStorageLocation.resolve(filename);
        Resource resource = null;
        try {
            resource = new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            return null;
        }


    }
}
