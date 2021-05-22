package io.bloobook.bookmanageapp.service;

import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.common.dto.response.RentalSimpleResponse;
import io.bloobook.bookmanageapp.common.exception.BookNotFoundException;
import io.bloobook.bookmanageapp.common.exception.RentalNotFoundException;
import io.bloobook.bookmanageapp.common.exception.UserNotFoundException;
import io.bloobook.bookmanageapp.common.exception.NotExistEmailException;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.book.BookRepository;
import io.bloobook.bookmanageapp.entity.rental.Rental;
import io.bloobook.bookmanageapp.entity.rental.RentalRepository;
import io.bloobook.bookmanageapp.entity.user.User;
import io.bloobook.bookmanageapp.entity.user.UserRepository;
import java.time.LocalDate;
import java.util.Collections;
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
        log.info("Rental Request: {} ", rentalRequest);
        Book savedBook = findBookById(rentalRequest.getBookId());
        savedBook.checkStockCount();
        savedBook.increaseTotalRentalCount();
        savedBook.decreaseStockCount();

        User savedUser = findUserById(rentalRequest.getUserId());
        System.out.println(rentalRequest.getUserId());
        savedUser.increaseBookRentalCount();

        Rental newRental = Rental.of(savedBook, savedUser);
        rentalRepository.save(newRental);

        return newRental;
    }

    @Transactional (readOnly = true)
    public List<RentalSimpleResponse> findRentalOnWeek ( LocalDate startedAt, LocalDate expiredAt ) {
        return Collections.unmodifiableList(RentalSimpleResponse
            .listOf(rentalRepository.findAllByStartAtAndExpiredAtAndOnRental(startedAt, expiredAt)));
    }

    @Transactional (readOnly = true)
    public List<RentalSimpleResponse> findRentalsByUserEmail ( String email ) {
        findUserByEmail(email);
        return Collections.unmodifiableList(RentalSimpleResponse
            .listOf(rentalRepository.findRentalByUserEmail(email)));
    }

    @Transactional
    public Rental expandRentalPeriod ( Long rentalId ) {
        Rental rental = findRentalById(rentalId);
        rental.returnPeriodExtend();
        return rental;
    }

    @Transactional
    public Rental returnRentalBook ( Long rentalId ) {
        Rental rental = findRentalById(rentalId);
        rental.returnRentalBook();
        return rental;
    }

    private Book findBookById ( Long id ) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    }

    private Rental findRentalById ( Long id ) {
        return rentalRepository.findById(id)
            .orElseThrow(() -> new RentalNotFoundException(id));
    }

    private User findUserById ( Long id ) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    private User findUserByEmail ( String email ) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotExistEmailException(email));
    }
}
