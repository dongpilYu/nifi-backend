package com.hyperdata.nifi.exception;

public class ServerUnavailableException extends BaseRuntimeException {
    public ServerUnavailableException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
