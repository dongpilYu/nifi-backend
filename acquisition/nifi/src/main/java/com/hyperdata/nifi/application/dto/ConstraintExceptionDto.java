package com.hyperdata.nifi.application.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.hyperdata.nifi.exception.ErrorCode;

public class ConstraintExceptionDto {
    private final int status;
    private final String errorCode;
    private final String message;
    private final LocalDateTime timeStamp;
    private final List<String> violations;

    public ConstraintExceptionDto(ErrorCode errorCode, String message, ConstraintViolationException exception) {
        this.status = errorCode.getStatus();
        this.errorCode = errorCode.getCode();
        this.message = message;

        List<String> errors = new ArrayList<>();
        exception.getConstraintViolations().forEach(violation ->
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                + violation.getMessage())
        );

        this.violations = errors;
        this.timeStamp = LocalDateTime.now();
    }

}
