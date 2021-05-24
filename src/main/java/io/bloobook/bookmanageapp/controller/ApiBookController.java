package io.bloobook.bookmanageapp.controller;

import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.common.dto.request.BookUpdateRequest;
import io.bloobook.bookmanageapp.common.dto.response.BookDetailResponse;
import io.bloobook.bookmanageapp.common.dto.response.BookSimpleResponse;
import io.bloobook.bookmanageapp.common.dto.response.BookStockCountResponse;
import io.bloobook.bookmanageapp.service.ApiBookService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo
 * - URL RESTful 에 맞게 개선 해야 함
 * - 현재 search 라는 URL 이 들어가 있는데 명사로 변경해야 함
 *
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
        bookService.saveNewBook(bookSaveRequest);
        return ResponseEntity.created(URI.create("/api/books")).build();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<BookDetailResponse> findBookById ( @PathVariable Long id ) {
        log.info("Book Id: {} ", id);
        return ResponseEntity.ok().body(bookService.findBookDetailById(id));
    }

    @GetMapping ("")
    public ResponseEntity<List<BookSimpleResponse>> findBooksByTitle ( @RequestParam String title ) {
        log.info("Book Title: {}", title);
        return ResponseEntity.ok().body(bookService.findBooksByTitle(title));
    }

    @GetMapping ("/category/{categoryId}") //todo  빼고 그냥 카테고리만 남기기
    public ResponseEntity<List<BookSimpleResponse>> findBooksByCategoryId ( @PathVariable Long categoryId ) {
        log.info("Category Id: {}", categoryId);
        return ResponseEntity.ok().body(bookService.findAllByCategoryId(categoryId));
    }

    @GetMapping("/stock/category/{id}")
    public ResponseEntity<List<BookStockCountResponse>> findBooksStockCountInfo ( @PathVariable Long id) {
        return ResponseEntity.ok().body(bookService.findAllBooksStockCount(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateBookStockCount(@PathVariable Long id, @RequestParam int stockCount) {
        bookService.stockCountUpdate(id, stockCount);
        return ResponseEntity.ok().build();
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Void> updateBookInfo ( @PathVariable Long id, @RequestBody BookUpdateRequest updateRequest ) {
        log.info("Update Request Info {}", updateRequest);
        bookService.updateBookInfo(id, updateRequest);
        return ResponseEntity.ok().build();
    }
}
