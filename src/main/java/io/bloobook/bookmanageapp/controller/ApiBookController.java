package io.bloobook.bookmanageapp.controller;

import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.service.ApiBookService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/08
 */
@Slf4j
@RequestMapping ("/api/books")
@RequiredArgsConstructor
@RestController
public class ApiBookController {

    private final ApiBookService bookService;

    @PostMapping ("")
    public ResponseEntity<Void> saveNewBook (
        @RequestBody @Valid BookSaveRequest bookSaveRequest ) {

        log.info ( "Book Save Request >> {}", bookSaveRequest );
        bookService.saveNeBook ( bookSaveRequest );
        return ResponseEntity.created ( URI.create ( "/api/books" ) ).build ();
    }
}
