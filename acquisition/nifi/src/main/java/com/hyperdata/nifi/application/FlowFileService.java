package com.hyperdata.nifi.application;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.hyperdata.nifi.application.dto.FlowUploadRequestDto;
import com.hyperdata.nifi.application.dto.FlowUploadResponseDto;

public interface FlowFileService {

    FlowUploadResponseDto store(MultipartFile file,
        FlowUploadRequestDto flowUploadRequestDto) throws
        IOException;

    Resource load(String minifiID) throws MalformedURLException;
}
