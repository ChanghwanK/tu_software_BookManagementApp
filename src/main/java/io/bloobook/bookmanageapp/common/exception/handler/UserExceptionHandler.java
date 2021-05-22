package io.bloobook.bookmanageapp.common.exception.handler;

import io.bloobook.bookmanageapp.common.dto.response.ErrorResponse;
import io.bloobook.bookmanageapp.common.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/22
 */

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException ( UserNotFoundException ex ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
