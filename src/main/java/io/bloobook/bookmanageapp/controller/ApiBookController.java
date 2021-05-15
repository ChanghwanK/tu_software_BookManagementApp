package io.bloobook.bookmanageapp.controller;

import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.common.dto.request.BookUpdateRequest;
import io.bloobook.bookmanageapp.common.dto.response.BookDetailResponse;
import io.bloobook.bookmanageapp.common.dto.response.BookSimpleResponse;
import io.bloobook.bookmanageapp.service.ApiBookService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Void> saveNewBook ( @RequestBody @Valid BookSaveRequest bookSaveRequest ) {

        log.info("Book Save Request >> {}", bookSaveRequest);
        bookService.saveNeBook(bookSaveRequest);
        return ResponseEntity.created(URI.create("/api/books")).build();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<BookDetailResponse> findBookById ( @PathVariable Long id ) {
        log.info("Book Id: {} ", id);
        return ResponseEntity.ok().body(bookService.findBookById(id));
    }

    @GetMapping ("/search/{title}")
    public ResponseEntity<List<BookSimpleResponse>> findBooksByTitle ( @PathVariable String title ) {
        log.info("Book Title: {}", title);
        return ResponseEntity.ok().body(bookService.findBooksByTitle(title));
    }

    @GetMapping ("/search/category/{categoryId}")
    public ResponseEntity<List<BookSimpleResponse>> findBooksByCategoryId ( @PathVariable Long categoryId ) {
        log.info("Category Id: {}", categoryId);
        return ResponseEntity.ok().body(bookService.findAllByCategoryId(categoryId));
    }
    /**
     * [ 메모 ]
     * 작가의 요청으로 인한 도서 제목, 소개글, 썸네일 이미지 수정을 지원합니다.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBookInfo (@PathVariable BookUpdateRequest updateRequest ) {
        log.info("Update Request Info {}", updateRequest);
        bookService.updateBookInfo(updateRequest);
        return ResponseEntity.ok().build();
    }
}
