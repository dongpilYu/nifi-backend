package com.hyperdata.nifi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyperdata.nifi.application.dto.CollectorCreateRequestDto;
import com.hyperdata.nifi.application.dto.CollectorCreateResponseDto;
import com.hyperdata.nifi.application.dto.FlowUploadRequestDto;
import com.hyperdata.nifi.application.dto.FlowUploadResponseDto;
import com.hyperdata.nifi.application.impl.FlowFileServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/collector")
public class CollectorController {
    private final FlowFileServiceImpl flowFileService;

    @Autowired
    public CollectorController(FlowFileServiceImpl flowFileService) {
        this.flowFileService = flowFileService;
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.OK)
    public CollectorCreateResponseDto create(CollectorCreateRequestDto collectorCreateRequestDto) {
        /*
            - Nifi variables를 받아서 템플릿 인스턴스화
            - 인스턴스화
            해당 템플릿에서 minifi 플로우 다운받아서 db에 저장
         */
        return null;
    }

    @PostMapping("/upload")
    @ResponseStatus(value = HttpStatus.OK)
    public FlowUploadResponseDto upload(@RequestParam(value = "file") MultipartFile file,
        FlowUploadRequestDto flowUploadRequestDto) {

        /*
            전달된 파일이 유효성 검사 로직 필요
            - 파일 크기, 확장자, 형식 검사

            파일 전달받아서 서버에 이름과 확장자를 변경하여 저장한 후 DB에 메타정보 저장
            - fileName, fileDownloadUri, uploadDate, minifiID
            - 이 로직은 service layer에서 처리
         */

        return flowFileService.store(file, flowUploadRequestDto);
    }

    @GetMapping("/download")
    public Resource download(@RequestParam("minifi") String minifiID) {
        /*
            minifi pullHttpIngestor 방식에서 minifi config 얻기 위한 api

            파일 존재 유무 확인 로직 필요

         */
        return flowFileService.load(minifiID);
    }
}
