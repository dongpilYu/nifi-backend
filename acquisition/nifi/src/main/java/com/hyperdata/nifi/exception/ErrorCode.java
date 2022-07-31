package com.hyperdata.nifi.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    ROW_DOES_NOT_EXIST(400, "C001", "Row does not exist"),
    INVALID_PARAMETER(400, "C002", "Invalid parameter"),
    INVALID_FILE_NAME(400, "C003", "Invalid file name"),
    INVALID_FILE_COPY(400, "C004", "Invalid file copy");

    private final int status;
    private final String code;
    private final String message;


    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
