package io.bloobook.bookmanageapp.service;

import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.common.dto.response.RentalSimpleResponse;
import io.bloobook.bookmanageapp.common.exception.BookNotFoundException;
import io.bloobook.bookmanageapp.common.exception.UserNotFoundException;
import io.bloobook.bookmanageapp.common.exception.notExistEmailException;
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
    public List<RentalSimpleResponse> findRentalOnWeek ( LocalDate startedAt, LocalDate expiredAt ) {

        return RentalSimpleResponse.listOf(rentalRepository.findAllByStartAtAndExpiredAt(startedAt, expiredAt));
    }



    @Transactional
    public Rental expandRentalPeriod ( Long rentalId ) {

        // 사용자 이메일을 통해서 대여 목록을 가져온다.
        // 대여 목록에는 대여 Id가 있다.
        // 대여 아이디로 대여 상세 정보를 찾고
        // 반납 기간을 수정해서 연장한다.
        return null;
    }

    private Book findBookById ( Long id ) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    }

    private User findUserById ( Long id ) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    private User findUserByEmail ( String email ) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new notExistEmailException(email));
    }

}
