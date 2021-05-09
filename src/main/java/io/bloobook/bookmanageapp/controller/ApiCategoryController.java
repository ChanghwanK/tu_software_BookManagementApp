package io.bloobook.bookmanageapp.controller;

import io.bloobook.bookmanageapp.common.dto.request.CategorySaveRequest;
import io.bloobook.bookmanageapp.service.ApiCategoryService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */

@Slf4j
@RequestMapping ("/api/category")
@RequiredArgsConstructor
@RestController
public class ApiCategoryController {

    private final ApiCategoryService categoryService;

    @PostMapping ("")
    public ResponseEntity<Void> saveNewCategory ( @RequestBody CategorySaveRequest request ) {
        categoryService.saveNewCategory ( request );
        return ResponseEntity.created ( URI.create ( "/api/category" ) ).build ();
    }
}
