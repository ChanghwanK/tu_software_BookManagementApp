package io.bloobook.bookmanageapp.controller;

import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.common.dto.response.RentalSimpleResponse;
import io.bloobook.bookmanageapp.service.ApiRentalService;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/18
 */

@Slf4j
@RequestMapping ("/api/rental")
@RequiredArgsConstructor
@RestController
public class ApiRentalController {

    private final ApiRentalService rentalService;

    @PostMapping ("")
    public ResponseEntity<Void> registRental ( @RequestBody RentalRequest rentalRequest ) {
        rentalService.registRental(rentalRequest);
        return ResponseEntity.created(URI.create("/api/rental")).build();
    }

    @GetMapping ("")
    public ResponseEntity<List<RentalSimpleResponse>> findRentalsOnWeek (
        @RequestParam (value = "startedAt", required = true) @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate startedAt,
        @RequestParam (value = "expiredAt", required = true) @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate expiredAt ) {

        return ResponseEntity.ok().body(rentalService.findRentalOnWeek(startedAt, expiredAt));
    }

    @GetMapping("/user")
    public ResponseEntity<List<RentalSimpleResponse>> findAllRentalSimpleInfo (@RequestParam(value = "email", required = true) String email) {

       return ResponseEntity.ok().body(rentalService.findRentalsByUserEmail(email));
    }

    // TODO: 2021.05.21 -Blue
    /**
     *   [대여 연장 흐름]
     *   사용자 이메일을 통해서 대여 목록을 가져온다.
     *   대여 목록에는 대여 Id가 있다.
     *   대여 아이디로 대여 상세 정보를 찾고
     *   반납 기간을 수정해서 연장한다.
     *
     *   따라서 모듈을 분리해서 먼저 email 을 통한 대여 도서 목록 조회 기능을 만든다.
     *   그리고 거기에 있는 retalId 를 통해 해당 Patch 메소드를 call 한다.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> expendRentalPeriod (@PathVariable(name = "id") Long rentalId ) {
        rentalService.expandRentalPeriod(rentalId);
        return ResponseEntity.ok().build();
    }
}
