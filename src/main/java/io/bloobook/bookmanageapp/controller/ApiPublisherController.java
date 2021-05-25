package io.bloobook.bookmanageapp.controller;

import io.bloobook.bookmanageapp.common.dto.request.PublisherSaveRequest;
import io.bloobook.bookmanageapp.service.ApiPublisherService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * [ 구현 기능 ] - 신규 등록 (구현) - 출판사 정보 수정 - 출판사 정보 조회 - 등록 해제
 *
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */

@Slf4j
@RequestMapping ("/api/publisher")
@RequiredArgsConstructor
@RestController
public class ApiPublisherController {

    private final ApiPublisherService publisherService;

    @PostMapping
    public ResponseEntity<Void> saveNewPublisher ( @RequestBody PublisherSaveRequest request ) {
        publisherService.saveNewPublisher(request);
        return ResponseEntity.created(URI.create("/api/publisher")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> terminatePublisher ( @PathVariable Long id ) {
        publisherService.terminatePublisher(id);
        return ResponseEntity.ok().build();
    }
}
