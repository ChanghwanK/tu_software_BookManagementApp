package io.bloobook.bookmanageapp.controller;

import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.common.dto.response.BookDetailResponse;
import io.bloobook.bookmanageapp.service.ApiBookService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        log.info("Book Save Request >> {}", bookSaveRequest);
        bookService.saveNeBook(bookSaveRequest);
        return ResponseEntity.created(URI.create("/api/books")).build();
    }
    /**
     * [ 메모 ]
     * - 해당 메서드를 통해서는 단순히 도서 정보만 조회 합니다.
     * - 대여 정보는 RentalController 를 통해서 조회 합니다.
     */
    @GetMapping ("{id}")
    public ResponseEntity<BookDetailResponse> findBookById (@PathVariable Long id ) {
        log.info("Book Id: {} ", id);
        return ResponseEntity.ok().body(bookService.findBookById(id));
    }
}
