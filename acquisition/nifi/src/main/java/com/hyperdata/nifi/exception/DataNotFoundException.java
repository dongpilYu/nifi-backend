package com.hyperdata.nifi.exception;

public class DataNotFoundException extends BaseRuntimeException {
    public DataNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
