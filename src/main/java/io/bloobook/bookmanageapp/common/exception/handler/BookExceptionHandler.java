package io.bloobook.bookmanageapp.common.exception.handler;

import io.bloobook.bookmanageapp.common.dto.response.ErrorResponse;
import io.bloobook.bookmanageapp.common.exception.AlreadyExistBookException;
import io.bloobook.bookmanageapp.common.exception.BookNotFoundException;
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
public class BookExceptionHandler {

    /**
     * 이미 존재하는 도서코드 예외
     */
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (AlreadyExistBookException.class)
    public ErrorResponse handleAlreadyExistBookException ( AlreadyExistBookException ex ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * 잘못된 Book Id 예외
     */
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (BookNotFoundException.class)
    public ErrorResponse handleBookNotFoundException ( BookNotFoundException ex ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
