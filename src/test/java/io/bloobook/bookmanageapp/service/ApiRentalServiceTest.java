package io.bloobook.bookmanageapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.book.BookRepository;
import io.bloobook.bookmanageapp.entity.rental.RentalRepository;
import io.bloobook.bookmanageapp.entity.user.User;
import io.bloobook.bookmanageapp.entity.user.UserRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/19
 */

@ExtendWith(MockitoExtension.class)
class ApiRentalServiceTest {

    @InjectMocks
    private ApiRentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    private RentalRequest rentalRequest;
    private User testUser;
    private Book testBook;


    @BeforeEach
    void setUp () {
        rentalRequest = RentalRequest.builder()
            .bookId(1L)
            .userId(1L)
            .build();

        testUser = User.builder()
            .email("test1@naver.com")
            .password("1234")
            .phoneNumber("010-1111-2222")
            .build();

        testBook = Book.builder()
            .id(1L)
            .bookCode("B-2948")
            .title("자바 도서관")
            .bookIntroduction("자바를 정복합시다.")
            .author("Martin")
            .thumbnail("www.naver.com")
            .publicationAt(LocalDate.of(2014,8,12))
            .build();
    }

    @DisplayName ("도서 저장을 테스트")
    @Test
    void registRental () {
        // when
        when(userRepository.findById(anyLong()))
            .thenReturn(Optional.of(testUser));

        when(bookRepository.findById(anyLong()))
            .thenReturn(Optional.of(testBook));

        rentalService.registRental(rentalRequest);

        // then
        verify(rentalRepository, times(1)).save(any());
    }
}