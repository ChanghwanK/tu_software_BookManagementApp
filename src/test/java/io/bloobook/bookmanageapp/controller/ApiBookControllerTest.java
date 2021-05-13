package io.bloobook.bookmanageapp.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.common.dto.response.BookDetailResponse;
import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.docs.BookDocumentation;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.service.ApiBookService;
import java.time.LocalDate;
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
 * @Date: 2021/05/10
 */

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest (ApiBookController.class)
class ApiBookControllerTest {

    @MockBean
    private ApiBookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BookSaveRequest bookSaveRequest;
    private Category category;
    private Publisher publisher;
    private BookLocation bookLocation;
    private Book baseBook;

    @BeforeEach
    void setUp ( WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8"))
            .apply(documentationConfiguration(provider))
            .alwaysDo(print())
            .build();

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

        baseBook = bookSaveRequest.toBaseBookEntity();
        baseBook.setBookLocation(bookLocation);
        baseBook.setRelationWithCategory(category);
        baseBook.setRelationWithPublisher(publisher);
    }

    @DisplayName ("신규 도서 등록을 테스트 한다.")
    @Test
    void saveNewBook () throws Exception {
        // given
        // when
        mockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookSaveRequest))
        )
            .andExpect(status().isCreated())
            .andDo(BookDocumentation.saveNewBook());
    }

    @DisplayName ("도서 상세 조회 컨트롤러 테스트")
    @Test
    void findBookById () throws Exception {
        // given
        // TODO: 2021.05.13 -Blue - 상세 조회 테스트 구현하기 및 RESTDocs 적용 해주세요
        BookDetailResponse bookDetailResponse = BookDetailResponse.of(baseBook);
        // when
        when(bookService.findBookById(anyLong()))
            .thenReturn(bookDetailResponse);

        // then
        mockMvc.perform(get("/api/books/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.bookCode").value(baseBook.getBookCode()))
            .andExpect(jsonPath("$.title").value(baseBook.getTitle()))
            .andExpect(jsonPath("$.author").value(baseBook.getAuthor()))
            .andExpect(jsonPath("$.publisherName").value(publisher.getName()))
            .andExpect(jsonPath("$.categoryName").value(category.getCategoryName()))
            .andExpect(jsonPath("$.bookLocation").value(bookLocation.getLocationInfo()))
            .andDo(BookDocumentation.findBookDetail());
    }

    @DisplayName ("잘못된 도서 Id에 대한 예외 테스트")
    @Test
    void ifBookNotFound () {
        // given
        // TODO: 2021.05.13 -Blue  -> 잘못된 도서 Id에 대한 테스트 구현 및 RESTDocs 적용 해주세요
        // when

        // then

    }

    @DisplayName ("제목을 통한 도서 리스트 조회 테스트")
    @Test
    void findBooksByTitle () {
        // given
        // TODO: 2021.05.13 -Blue   -> 제목을 이용한 도서 리스트 테스트 구현
        // when

        // then

    }
}