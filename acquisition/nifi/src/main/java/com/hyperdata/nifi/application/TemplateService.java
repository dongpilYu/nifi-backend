package com.hyperdata.nifi.application;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hyperdata.nifi.application.dto.TemplateUploadRequestDto;
import com.hyperdata.nifi.application.dto.TemplateUploadResponseDto;

public interface TemplateService {
    TemplateUploadResponseDto store(MultipartFile file, TemplateUploadRequestDto templateUploadRequestDto) throws
        IOException;
}
