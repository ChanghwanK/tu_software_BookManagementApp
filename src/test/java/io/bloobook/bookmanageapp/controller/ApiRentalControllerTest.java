package io.bloobook.bookmanageapp.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bloobook.bookmanageapp.common.dto.request.RentalRequest;
import io.bloobook.bookmanageapp.common.dto.response.NonReturnBooks;
import io.bloobook.bookmanageapp.common.dto.response.RentalSimpleResponse;
import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.docs.RentalDocument;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.rental.Rental;
import io.bloobook.bookmanageapp.entity.rental.RentalRepository;
import io.bloobook.bookmanageapp.entity.user.User;
import io.bloobook.bookmanageapp.service.ApiRentalService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

    @Mock
    private RentalRepository rentalRepository;

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
    private Rental rental_01, rental_02;


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
            .categoryName("??????")
            .locationCode("A??? 2??? ??????")
            .build();

        category = Category.builder()
            .categoryCode("NOVEL")
            .categoryName("??????")
            .categoryStatus(CategoryStatus.REGISTER)
            .build();

        publisher = Publisher.builder()
            .businessNumber("2309-A3933-2222")
            .telNumber("02-1522-3948")
            .name("??? ?????? ?????????")
            .address("??????????????? ??????")
            .publisherStatus(PublisherStatus.REGISTER)
            .build();

        testBook = Book.builder()
            .id(1L)
            .bookCode("B-2948")
            .title("?????? ?????????")
            .bookIntroduction("????????? ???????????????.")
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

    @DisplayName ("?????? ?????? ?????? ?????????")
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

    @DisplayName ("????????? ?????? ?????? ????????? ?????????")
    @Test
    void findAllRentalBetweenStartedAtExpiredAt () throws Exception {
        // given
        LocalDate startedAt = LocalDate.now();
        LocalDate expiredAt = startedAt.plusWeeks(2);
        List<Rental> rentals = List.of(rental_01, rental_02);

        // when
        when(rentalService.findRentalOnWeek(startedAt, expiredAt))
            .thenReturn(RentalSimpleResponse.listOf(rentals));

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
            .andDo(RentalDocument.findAllBetweenPeriod())
            ;
    }

    @DisplayName ("????????? ???????????? ???????????? ?????? ?????? ????????? ????????? ??????.")
    @Test
    void findAllRentalsByUserEmail () throws Exception {
        // given
        List<Rental> rentals = List.of(rental_01, rental_02);
        // when
        when(rentalService.findRentalsByUserEmail(anyString()))
            .thenReturn(RentalSimpleResponse.listOf(rentals));
        // then
        mockMvc.perform(get("/api/rental/user")
            .param("email", "testUser@naver.com")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].bookId").value(1L))
            .andExpect(jsonPath("$.[0].title").value(testBook.getTitle()))
            .andExpect(jsonPath("$.[0].author").value(testBook.getAuthor()))
            .andExpect(jsonPath("$.[0].publisherName").value(testBook.getPublisher().getName()))
            .andExpect(jsonPath("$.[1].title").value(testBook.getTitle()))
            .andExpect(jsonPath("$.[1].author").value(testBook.getAuthor()))
            .andExpect(jsonPath("$.[1].publisherName").value(testBook.getPublisher().getName()))
            .andDo(RentalDocument.findAllRentalsByUserEmail());
    }

    @DisplayName ("?????? ????????? ????????? ")
    @Test
    void expandRentalPeriod () throws Exception {
        // given
        // when
        // then
        mockMvc.perform(patch("/api/rental/{id}", 1L))
            .andExpect(status().isOk())
            .andDo(RentalDocument.expandRentalPeriod());
    }

    @DisplayName ("?????? ????????? ?????????")
    @Test
    void returnRentalBook () throws Exception {
        // given
        // when
        // then
        mockMvc.perform(delete("/api/rental/{id}", 1L))
            .andExpect(status().isOk())
            .andDo(RentalDocument.returnRentalBook());
    }

    @DisplayName ("????????? ?????? ?????? ?????????")
    @Test
    void  findNonReturnRentals() throws Exception {
        // given
        NonReturnBooks nonReturnBooks_01 = NonReturnBooks.of(rental_01);
        NonReturnBooks nonReturnBooks_02 = NonReturnBooks.of(rental_02);

        List<NonReturnBooks> nonReturnBooks = List.of (nonReturnBooks_01, nonReturnBooks_02);

        // when
        when(rentalService.findAllNonReturnBook())
            .thenReturn(nonReturnBooks);

        // then
        mockMvc.perform(get("/api/rental/non"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].bookId").value(1L))
            .andExpect(jsonPath("$.[0].rentalId").value(1L))
            .andExpect(jsonPath("$.[0].email").value(testUser.getEmail()))
            .andExpect(jsonPath("$.[0].bookTitle").value(testBook.getTitle()))
            .andDo(RentalDocument.findAllNonReturnBook());
    }
}