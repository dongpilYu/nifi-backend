package com.hyperdata.nifi.application.dto;

import java.time.LocalDateTime;

import com.hyperdata.nifi.exception.ErrorCode;

public class FileStorageExceptionDto {
    private final int status;
    private final String errorCode;
    private final String message;
    private final LocalDateTime timeStamp;

    public FileStorageExceptionDto(ErrorCode errorCode, String message, Exception exception) {
        this.status = errorCode.getStatus();
        this.errorCode = errorCode.getCode();
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

}
