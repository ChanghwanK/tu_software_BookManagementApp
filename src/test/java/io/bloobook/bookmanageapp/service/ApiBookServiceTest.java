package io.bloobook.bookmanageapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.book.BookRepository;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.category.CategoryRepository;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.publisher.PublisherRepository;
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
 * @Date: 2021/05/10
 */


@ExtendWith (MockitoExtension.class)
class ApiBookServiceTest {

    @InjectMocks
    private ApiBookService apiBookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PublisherRepository publisherRepository;

    private BookSaveRequest bookSaveRequest;
    private Category category;
    private Publisher publisher;
    private BookLocation bookLocation;
    private Book book;

    @BeforeEach
    void setUp () {
        bookSaveRequest = BookSaveRequest.builder()
            .categoryId(1L)
            .locationCode("A열 2층 선반")
            .bookCode("A-230-394")
            .title("King Of Lear")
            .bookIntroduction("도서 소개글 도서 소개글 입니다.")
            .author("김작가")
            .thumbnail("www.google.com")
            .publisherBusinessNumber("2309-A3933-2222")
            .publicationAt(LocalDate.of(2015, 4, 23))
            .build();

        book = bookSaveRequest.toBaseBookEntity();
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
    }

    @DisplayName ("도서 저장 서비스 테스트")
    @Test
    void saveNewBook () {
        // given

        // when
        when(categoryRepository.findById(1L))
            .thenReturn(Optional.of(category));

        when(publisherRepository.findByBusinessNumber("2309-A3933-2222"))
            .thenReturn(Optional.of(publisher));

        apiBookService.saveNeBook(bookSaveRequest);

        // then
        verify(bookRepository,times(1)).save(any());
    }
}