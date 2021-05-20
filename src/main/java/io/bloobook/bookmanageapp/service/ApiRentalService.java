package io.bloobook.bookmanageapp.service;

import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.common.dto.response.RentalSimpleResponse;
import io.bloobook.bookmanageapp.common.exception.BookNotFoundException;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.book.BookRepository;
import io.bloobook.bookmanageapp.entity.rental.Rental;
import io.bloobook.bookmanageapp.entity.rental.RentalRepository;
import io.bloobook.bookmanageapp.entity.user.User;
import io.bloobook.bookmanageapp.entity.user.UserRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/18
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiRentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public Rental registRental ( RentalRequest rentalRequest ) {

        return rentalRepository.save(Rental.builder()
            .user(findUserById(rentalRequest.getUserId()))
            .book(findBookById(rentalRequest.getBookId()))
            .build());
    }

    @Transactional (readOnly = true)
    public List<RentalSimpleResponse> findRentalOnWeek () {
        // TODO: 2021.05.21 -Blue 인자 값으로 날짜 받기로 변경
        // RentalRepository 에 @query 사용하기
        LocalDate startAt = LocalDate.now();
        log.info("대여 리스트 {}", rentalRepository.findAllByStartAtAndExpiredAt(startAt, startAt.plusWeeks(2) ));
        System.out.println("################ >>>>>> " + startAt.plusWeeks(2));
        return RentalSimpleResponse.listOf(rentalRepository.findAllByStartAtAndExpiredAt(startAt, startAt.plusWeeks(2) ));
    }

    private Book findBookById ( Long id ) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    }

    private User findUserById ( Long id ) {
        return userRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    }
}
