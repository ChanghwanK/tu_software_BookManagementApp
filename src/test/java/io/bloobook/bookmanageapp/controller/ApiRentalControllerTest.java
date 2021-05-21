package io.bloobook.bookmanageapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.common.dto.response.RentalSimpleResponse;
import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.docs.RentalDocument;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.rental.Rental;
import io.bloobook.bookmanageapp.entity.user.User;
import io.bloobook.bookmanageapp.service.ApiRentalService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/19
 */


@ExtendWith (RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest (ApiRentalController.class)
class ApiRentalControllerTest {

    @MockBean
    private ApiRentalService rentalService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RentalRequest rentalRequest;
    private Category category;
    private Publisher publisher;
    private BookLocation bookLocation;
    private User testUser;
    private Book testBook;


    @BeforeEach
    void setUp ( WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8"))
            .apply(documentationConfiguration(provider))
            .alwaysDo(print())
            .build();

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

    @DisplayName ("도서 대여 등록 테스트")
    @Test
    void registRental  () throws Exception {
        // given
        // when
        // then
        mockMvc.perform(post("/api/rental")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(rentalRequest))
        )
            .andExpect(status().isCreated())
            .andDo(RentalDocument.registRental());

    }

    @DisplayName ("기간별 대여 목록 조회를 테스트")
    @Test
    void findAllRentalBetweenStartedAtExpiredAt () throws Exception {
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
        when(rentalService.findRentalOnWeek(startedAt, expiredAt))
            .thenReturn(List.of(RentalSimpleResponse.of(rental_01), RentalSimpleResponse.of(rental_02)));

        // then
        mockMvc.perform(get("/api/rental")
            .param("startedAt", String.valueOf(startedAt))
            .param("expiredAt", String.valueOf(expiredAt))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].bookId").value(1L))
            .andExpect(jsonPath("$.[0].title").value(testBook.getTitle()))
            .andExpect(jsonPath("$.[0].author").value(testBook.getAuthor()))
            .andExpect(jsonPath("$.[0].publisherName").value(testBook.getPublisher().getName()))
            .andExpect(jsonPath("$.[1].title").value(testBook.getTitle()))
            .andExpect(jsonPath("$.[1].author").value(testBook.getAuthor()))
            .andExpect(jsonPath("$.[1].publisherName").value(testBook.getPublisher().getName()))
            .andDo(RentalDocument.findAllBetweenPeriod());
    }
}