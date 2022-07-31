package com.hyperdata.nifi.application.dto;

import java.time.LocalDateTime;

import com.hyperdata.nifi.exception.ErrorCode;

public class ExceptionDto {
    private final int status;
    private final String errorCode;
    private final String message;
    private final LocalDateTime timestamp;

    public ExceptionDto(ErrorCode errorCode, String message) {
        this.status = errorCode.getStatus();
        this.errorCode = errorCode.getCode();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
