package com.hyperdata.nifi.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyperdata.nifi.application.TemplateService;
import com.hyperdata.nifi.application.dto.TemplateUploadRequestDto;
import com.hyperdata.nifi.application.dto.TemplateUploadResponseDto;
import com.hyperdata.nifi.application.impl.TemplateServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/template")
public class TemplateController {
    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateServiceImpl templateService) {
        this.templateService = templateService;
    }

    @PostMapping("/upload")
    @ResponseStatus(value = HttpStatus.OK)
    public TemplateUploadResponseDto upload(@RequestParam(value = "template") MultipartFile file,
        TemplateUploadRequestDto templateUploadRequestDto) throws IOException {
        return templateService.store(file, templateUploadRequestDto);
    }

}
