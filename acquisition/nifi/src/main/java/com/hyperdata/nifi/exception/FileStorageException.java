package com.hyperdata.nifi.exception;

public class FileStorageException extends BaseRuntimeException {
    public FileStorageException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
