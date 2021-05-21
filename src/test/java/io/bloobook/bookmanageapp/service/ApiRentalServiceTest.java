package io.bloobook.bookmanageapp.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.common.dto.response.RentalSimpleResponse;
import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
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

    @DisplayName ("특정 기간동안 대여 도서 목록 조회를 테스트")
    @Test
    void findAllRentalBetweenStartedAtAndExpiredAt () {
        // given
        Rental rental_01 = Rental.builder()
            .book(testBook)
            .user(testUser)
            .build();
        Rental rental_02 = Rental.builder()
            .book(testBook)
            .user(testUser)
            .build();
        LocalDate startedAt = LocalDate.now();
        LocalDate expiredAt = startedAt.plusWeeks(2);

        // when
        when(rentalRepository.findAllByStartAtAndExpiredAt(startedAt, expiredAt))
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
}