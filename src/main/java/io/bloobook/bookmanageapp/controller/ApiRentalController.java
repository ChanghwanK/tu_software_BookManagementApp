package io.bloobook.bookmanageapp.controller;

import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.common.dto.response.RentalSimpleResponse;
import io.bloobook.bookmanageapp.service.ApiRentalService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("")
    public ResponseEntity<Void> registRental(@RequestBody RentalRequest rentalRequest) {
        rentalService.registRental (rentalRequest);
        return ResponseEntity.created(URI.create("/api/rental")).build();
    }

    @GetMapping("")
    public ResponseEntity<List<RentalSimpleResponse>> findRentalsOnWeek() {
        return ResponseEntity.ok().body(rentalService.findRentalOnWeek());
    }
}
