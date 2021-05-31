package io.bloobook.bookmanageapp.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.common.dto.response.NonReturnBooks;
import io.bloobook.bookmanageapp.common.dto.response.RentalSimpleResponse;
import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.RentalStatus;
import io.bloobook.bookmanageapp.common.exception.BookNotFoundException;
import io.bloobook.bookmanageapp.common.exception.NotExistBookStockException;
import io.bloobook.bookmanageapp.common.exception.UserNotFoundException;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.book.BookRepository;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.rental.Rental;
import io.bloobook.bookmanageapp.entity.rental.RentalRepository;
import io.bloobook.bookmanageapp.entity.user.User;
import io.bloobook.bookmanageapp.entity.user.UserRepository;
import java.time.LocalDate;
import java.util.List;
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
    private Category category;
    private Publisher publisher;
    private BookLocation bookLocation;
    private User testUser;
    private Book testBook;
    private Rental rental_01, rental_02;

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

        bookLocation = BookLocation.builder()
            .categoryName("문학")
            .locationCode("A열 2층 선반")
            .build();

        category = Category.builder()
            .categoryCode("NOVEL")
            .categoryName("문학")
            .categoryStatus(CategoryStatus.REGISTER)
            .build();

        publisher = Publisher.builder()
            .businessNumber("2309-A3933-2222")
            .telNumber("02-1522-3948")
            .name("책 사랑 출판사")
            .address("서울특별시 서초")
            .publisherStatus(PublisherStatus.REGISTER)
            .build();

        testBook = Book.builder()
            .id(1L)
            .bookCode("B-2948")
            .title("자바 도서관")
            .bookIntroduction("자바를 정복합시다.")
            .author("Martin")
            .thumbnail("www.naver.com")
            .publicationAt(LocalDate.of(2014,8,12))
            .publisher(publisher)
            .category(category)
            .bookLocation(bookLocation)
            .build();

        rental_01 = Rental.builder()
            .id(1L)
            .book(testBook)
            .user(testUser)
            .build();

         rental_02 = Rental.builder()
            .id(2L)
            .book(testBook)
            .user(testUser)
            .build();
    }


    @DisplayName ("특정 기간동안 대여 도서 목록 조회를 테스트")
    @Test
    void findAllRentalBetweenStartedAtAndExpiredAt () {
        // given
        LocalDate startedAt = LocalDate.now();
        LocalDate expiredAt = startedAt.plusWeeks(2);

        // when
        when(rentalRepository.findAllByStartAtAndExpiredAtAndOnRental(startedAt, expiredAt))
            .thenReturn(List.of(rental_01, rental_02));

        List<RentalSimpleResponse> result = rentalService.findRentalOnWeek(startedAt, expiredAt);

        // then
        assertAll (
            () -> assertThat(result.size()).isEqualTo(2),
            () -> assertThat(result.get(0).getBookId()).isEqualTo(testBook.getId()),
            () -> assertThat(result.get(0).getPublisherName()).isEqualTo(testBook.getPublisher().getName()),
            () -> assertThat(result.get(0).getTitle()).isEqualTo(testBook.getTitle()),
            () -> assertThat(result.get(1).getBookId()).isEqualTo(testBook.getId()),
            () -> assertThat(result.get(1).getPublisherName()).isEqualTo(testBook.getPublisher().getName()),
            () -> assertThat(result.get(1).getTitle()).isEqualTo(testBook.getTitle())
        );
    }

    @DisplayName ("사용자 이메일을 통해 대여 목록을 조회 한다.")
    @Test
    void findRentalsByUserEmail () {
        // given

        // when
        when(userRepository.findByEmail(anyString()))
            .thenReturn(Optional.of(testUser));

        when(rentalRepository.findRentalByUserEmail(anyString()))
            .thenReturn(List.of(rental_01, rental_02));
        // then
        List<RentalSimpleResponse> result = rentalService.findRentalsByUserEmail(anyString());

        assertAll (
            () -> assertThat(result.size()).isEqualTo(2),
            () -> assertThat(result.get(0).getBookId()).isEqualTo(testBook.getId()),
            () -> assertThat(result.get(0).getRentalId()).isEqualTo(rental_01.getId()),
            () -> assertThat(result.get(0).getTitle()).isEqualTo(testBook.getTitle()),
            () -> assertThat(result.get(0).getPublisherName()).isEqualTo(publisher.getName()),
            () -> assertThat(result.get(0).getStartedAt()).isEqualTo(rental_01.getStartAt()),
            () -> assertThat(result.get(0).getExpiredAt()).isEqualTo(rental_01.getExpiredAt()),
            () -> assertThat(result.get(1).getBookId()).isEqualTo(testBook.getId()),
            () -> assertThat(result.get(1).getRentalId()).isEqualTo(rental_02.getId()),
            () -> assertThat(result.get(1).getTitle()).isEqualTo(testBook.getTitle()),
            () -> assertThat(result.get(1).getPublisherName()).isEqualTo(publisher.getName()),
            () -> assertThat(result.get(1).getStartedAt()).isEqualTo(rental_01.getStartAt()),
            () -> assertThat(result.get(1).getExpiredAt()).isEqualTo(rental_01.getExpiredAt())
        );
    }

    @DisplayName ("대여 연장을 테스트")
    @Test
    void expandRentalPeriod () {
        // given
        // when
        when(rentalRepository.findById(anyLong()))
            .thenReturn(Optional.of(rental_01));
        // then
        // 기존 대여 만료일 06-05
        Rental rental = rentalService.expandRentalPeriod(anyLong());

        /**
         * 대여 날짜는 변경되지 않기 때문에 대여 날을 기준으로 + 3 weeks 가 된다.
         */
        assertThat(rental.getExpiredAt()).isEqualTo(rental.getStartAt().plusWeeks(3));
    }

    @DisplayName ("미반납 도서 조회를 테스트")
    @Test
    void findNonReturnBook () {
        // given
        Rental nonReturnBook_01 = Rental.builder()
            .id(3L)
            .book(testBook)
            .user(testUser)
            .build();

        Rental nonReturnBook_02 = Rental.builder()
            .id(4L)
            .book(testBook)
            .user(testUser)
            .build();

        nonReturnBook_01.updateRentalStatusToNonReturn();
        nonReturnBook_02.updateRentalStatusToNonReturn();

        // when
        when(rentalRepository.findAllNonRentals(RentalStatus.NON_RETURN))
            .thenReturn(List.of(nonReturnBook_01, nonReturnBook_02));

        List<NonReturnBooks> nonReturnBooks = rentalService.findAllNonReturnBook();

        // then
//        assertThat(nonReturnBooks.size()).isEqualTo(2);
        assertAll (
            () -> assertThat(nonReturnBooks.size()).isEqualTo(2),
            () -> assertThat(nonReturnBooks.get(0).getRentalId()).isEqualTo(nonReturnBook_01.getId()),
            () -> assertThat(nonReturnBooks.get(1).getRentalId()).isEqualTo(nonReturnBook_02.getId())
        );

    }

    @DisplayName ("대여 반납을 테스트")
    @Test
    void returnRentalBook () {
        // given
        // when
        when(rentalRepository.findById(anyLong()))
            .thenReturn(Optional.of(rental_01));

        Rental rental = rentalService.returnRentalBook(anyLong());
        // then
        assertThat(rental.isOnRental()).isFalse();
    }

    @DisplayName ("잘못된 도서 Id 예외 테스트")
    @Test
    void ifBookNotFound () {
        // given
        // when
        when(bookRepository.findById(anyLong()))
            .thenReturn(Optional.empty());

        // then
        assertThrows(
            BookNotFoundException.class, () -> rentalService.registRental(rentalRequest)
        );

    }
    @DisplayName ("잘못된 사용자 Id 예외 테스트 ")
    @Test
    void ifUserNotFound () {
        // given
        // when
        when(bookRepository.findById(anyLong()))
            .thenReturn(Optional.of(testBook));

        when(userRepository.findById(anyLong()))
            .thenReturn(Optional.empty());
        // then
        assertThrows(
            UserNotFoundException.class, () -> rentalService.registRental(rentalRequest)
        );

    }
    @DisplayName ("도서 재고 개수가 0일 경우 예외 테스트")
    @Test
    void ifBookStockCountIsZero () {
        // given
        Book stockCountZeroBook = Book.builder()
            .id(3L)
            .bookCode("C-2948")
            .title("Spring Boot 정복하기")
            .bookIntroduction("자바를 정복합시다.")
            .author("David")
            .thumbnail("www.zpdoweudk.com")
            .publicationAt(LocalDate.of(2017,2,12))
            .publisher(publisher)
            .category(category)
            .bookLocation(bookLocation)
            .build();

        for ( int i = 0; i < 5; i++ ) {
            stockCountZeroBook.decreaseStockCount();
        }

        // when
        when(bookRepository.findById(anyLong()))
            .thenReturn(Optional.of(stockCountZeroBook));

        // then
        assertThrows(
            NotExistBookStockException.class, () -> rentalService.registRental(rentalRequest)
        );
    }
}