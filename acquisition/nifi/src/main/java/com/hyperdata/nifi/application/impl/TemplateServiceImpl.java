package com.hyperdata.nifi.application.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hyperdata.nifi.application.TemplateService;
import com.hyperdata.nifi.application.dto.TemplateUploadRequestDto;
import com.hyperdata.nifi.application.dto.TemplateUploadResponseDto;
import com.hyperdata.nifi.domain.Template;
import com.hyperdata.nifi.domain.repository.TemplateRepository;
import com.hyperdata.nifi.exception.ErrorCode;
import com.hyperdata.nifi.exception.FileStorageException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final WebClient webClient;

    @Autowired
    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
        this.webClient = WebClient.builder().baseUrl("http://192.1.1.92:31147/nifi-api").build();
    }

    public TemplateUploadResponseDto store(MultipartFile file, TemplateUploadRequestDto templateUploadRequestDto) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new FileStorageException(ErrorCode.INVALID_FILE_NAME, "파일 이름에 유효하지 않은 문자가 있습니다. ");
        }

        templateRepository.findByTemplateName(fileName).ifPresent(
            t -> {
                throw new IllegalStateException("템플릿이 이미 존재합니다. ");
            }
        );

        JsonElement element;
        JsonObject object;

        String rootProcessGroupJson = webClient.get()
            .uri("/process-groups/root/")
            .retrieve()
            .bodyToMono(String.class)
            .block();
        element = JsonParser.parseString(Objects.requireNonNull(rootProcessGroupJson));
        object = element.getAsJsonObject();
        String rootProcessGroupId = object.get("id").getAsString();

        log.debug("Debug: ", rootProcessGroupId);

        String uploadTemplateJson = webClient.post()
            .uri("/process-groups/{rootProcessGroupId}/templates/upload", rootProcessGroupId)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        element = JsonParser.parseString(Objects.requireNonNull(uploadTemplateJson));
        object = element.getAsJsonObject();
        String templateId = object.get("id").getAsString();
        String templateUri = object.get("template.uri").getAsString();
        String templateName = object.get("template.name").getAsString();

        log.info("Info: ", rootProcessGroupId, templateId, templateUri, templateName);


        templateRepository.save(Template.of(templateId, templateUri, templateName));

        /*
            파일 이름이 유효한지 검증

            이미 업로드된 템플릿인지 확인 (NiFi API에서 확인하지 않기 때문에 우리 백엔드에서 체크)
            - filename으로 중복 여부를 확인?
            - 파일 전체를 비교해서 중복 여부를 확인?
            - NiFi는 템플릿의 중복 여부를 어떻게 체크하나?
                - 그냥 nifi-admin-backend에서 관리
            - 템플릿 중복 여부 판단
                - 파일명 같으면 중복

            root process group id 얻기
            template 업로드
            성공 시 DB에 템플릿 메타 데이터 저장
            실패 시 예외 발생
         */

        return null;
    }
}
