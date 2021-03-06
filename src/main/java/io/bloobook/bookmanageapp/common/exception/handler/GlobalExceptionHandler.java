package io.bloobook.bookmanageapp.common.exception.handler;

import io.bloobook.bookmanageapp.common.dto.response.ErrorResponse;
import io.bloobook.bookmanageapp.common.exception.AlreadyExistCategoryException;
import io.bloobook.bookmanageapp.common.exception.AlreadyExistPublisherException;
import io.bloobook.bookmanageapp.common.exception.CategoryNotFoundException;
import io.bloobook.bookmanageapp.common.exception.BusinessNumberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (AlreadyExistCategoryException.class)
    public ErrorResponse handleAlreadyExistCategoryException ( AlreadyExistCategoryException ex ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (AlreadyExistPublisherException.class)
    public ErrorResponse handleAlreadyExistPublisherException (
        AlreadyExistPublisherException ex ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (BusinessNumberNotFoundException.class)
    public ErrorResponse handlePublisherNotFoundException ( BusinessNumberNotFoundException ex ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (CategoryNotFoundException.class)
    public ErrorResponse handleCategoryNotFoundException ( CategoryNotFoundException ex ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException (
        MethodArgumentNotValidException ex ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getFieldError());
    }

    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (RuntimeException.class)
    public ErrorResponse handleRuntimeException ( RuntimeException ex ) {
        log.error("?????? ?????? ?????? >>> { }", ex);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "??? ??? ?????? ?????? ????????? ??????????????????.");
    }

}
