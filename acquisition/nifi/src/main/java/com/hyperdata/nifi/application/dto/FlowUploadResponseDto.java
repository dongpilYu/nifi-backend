package com.hyperdata.nifi.application.dto;

import com.hyperdata.nifi.domain.FlowFile;

public class FlowUploadResponseDto {
    private String fileName;
    private String fileDownloadUri;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public FlowUploadResponseDto(String fileName, String fileDownloadUri) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
    }

    public static FlowUploadResponseDto from(FlowFile flowFile) {
        return new FlowUploadResponseDto(flowFile.getFileName(), flowFile.getFileDownloadUri());
    }

}
