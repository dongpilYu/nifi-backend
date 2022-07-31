package com.hyperdata.nifi.web;

import java.io.IOException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hyperdata.nifi.application.dto.ConstraintExceptionDto;
import com.hyperdata.nifi.application.dto.ExceptionDto;
import com.hyperdata.nifi.application.dto.FileStorageExceptionDto;
import com.hyperdata.nifi.exception.BadRequestException;
import com.hyperdata.nifi.exception.ErrorCode;
import com.hyperdata.nifi.exception.UnauthorizedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleBadRequestException(BadRequestException exception) {
        GlobalControllerAdvice.log.error(exception.toString());
        return new ExceptionDto(exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(value = {IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FileStorageExceptionDto handleIoException(IOException exception) {
        GlobalControllerAdvice.log.error(exception.toString());
        return new FileStorageExceptionDto(ErrorCode.INVALID_FILE_NAME, exception.getMessage(), exception);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ConstraintExceptionDto handleConstraintViolationException(ConstraintViolationException exception) {
        GlobalControllerAdvice.log.error(exception.toString());
        return new ConstraintExceptionDto(ErrorCode.INVALID_PARAMETER, exception.getMessage(), exception);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleIllegalArgumentException(IllegalArgumentException exception) {
        GlobalControllerAdvice.log.error(exception.toString());
        return new ExceptionDto(ErrorCode.INVALID_PARAMETER, exception.getMessage());
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDto handleUnauthorizedException(UnauthorizedException exception) {
        GlobalControllerAdvice.log.error(exception.toString());
        return new ExceptionDto(exception.getErrorCode(), exception.getMessage());
    }

}
