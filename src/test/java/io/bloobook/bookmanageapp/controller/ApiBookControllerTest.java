package io.bloobook.bookmanageapp.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.common.dto.request.BookUpdateRequest;
import io.bloobook.bookmanageapp.common.dto.response.BookDetailResponse;
import io.bloobook.bookmanageapp.common.dto.response.BookSimpleResponse;
import io.bloobook.bookmanageapp.common.dto.response.BookStockCountResponse;
import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.common.exception.BookNotFoundException;
import io.bloobook.bookmanageapp.docs.BookDocumentation;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.service.ApiBookService;
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
 * @Date: 2021/05/10
 */

@ExtendWith (RestDocumentationExtension.class)
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
    private Book testBook;

    @BeforeEach
    void setUp ( WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider provider ) {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8"))
            .apply(documentationConfiguration(provider))
            .alwaysDo(print())
            .build();

        bookSaveRequest = BookSaveRequest.builder()
            .categoryId(1L)
            .locationCode("A??? 2??? ??????")
            .bookCode("A-230-394")
            .title("King Of Lear")
            .bookIntroduction("?????? ????????? ?????? ????????? ?????????.")
            .author("?????????")
            .thumbnail("www.google.com")
            .publisherBusinessNumber("2309-A3933-2222")
            .publicationAt(LocalDate.of(2015, 4, 23))
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

        baseBook = Book.builder()
            .id(1L)
            .bookCode(bookSaveRequest.getBookCode())
            .title(bookSaveRequest.getTitle())
            .bookIntroduction(bookSaveRequest.getBookIntroduction())
            .author(bookSaveRequest.getAuthor())
            .thumbnail(bookSaveRequest.getThumbnail())
            .publicationAt(bookSaveRequest.getPublicationAt())
            .category(category)
            .publisher(publisher)
            .bookLocation(bookLocation)
            .build();

        testBook = Book.builder()
            .id(2L)
            .bookCode("B-2948")
            .title("?????? ?????????")
            .bookIntroduction("????????? ???????????????.")
            .author("Martin")
            .thumbnail("www.naver.com")
            .publicationAt(bookSaveRequest.getPublicationAt())
            .category(category)
            .publisher(publisher)
            .bookLocation(bookLocation)
            .build();
    }

    @DisplayName ("?????? ?????? ????????? ????????? ??????.")
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

    @DisplayName ("?????? ?????? ?????? ???????????? ?????????")
    @Test
    void findBookById () throws Exception {
        // given
        BookDetailResponse bookDetailResponse = BookDetailResponse.of(baseBook);
        // when
        when(bookService.findBookDetailById(anyLong()))
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

    @DisplayName ("????????? ?????? ?????? ????????? ?????? ?????????")
    @Test
    void findAllByTitle () throws Exception {
        // given
        // when
        when(bookService.findBooksByTitle(anyString()))
            .thenReturn(BookSimpleResponse.listOf(List.of(baseBook, testBook)));

        // then
        mockMvc.perform(get("/api/books").param("title","??????"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].title").value(baseBook.getTitle()))
            .andExpect(jsonPath("$.[1].title").value(testBook.getTitle()))
            .andDo(BookDocumentation.findBookByTitle());
    }

    @DisplayName ("???????????? Id ??? ?????? ?????? ?????? ?????? ?????????")
    @Test
    void findAllByCategoryIdx () throws Exception {
        when(bookService.findAllByCategoryId(anyLong()))
            .thenReturn(BookSimpleResponse.listOf(List.of(baseBook, testBook)));

        // then
        mockMvc.perform(get("/api/books//category/{categoryId}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].title").value(baseBook.getTitle()))
            .andExpect(jsonPath("$.[0].author").value(baseBook.getAuthor()))
            .andExpect(jsonPath("$.[0].publisherName").value(baseBook.getPublisher().getName()))
            .andExpect(jsonPath("$.[1].title").value(testBook.getTitle()))
            .andExpect(jsonPath("$.[1].author").value(testBook.getAuthor()))
            .andExpect(jsonPath("$.[1].publisherName").value(testBook.getPublisher().getName()))
            .andDo(BookDocumentation.findAllByCategoryId());
    }

    @DisplayName ("??????????????? ?????? ?????? ?????? ?????????")
    @Test
    void findAllBooksStockCountByCategoryId () throws Exception {
        // given
        // when
        when(bookService.findAllBooksStockCount(anyLong()))
            .thenReturn(BookStockCountResponse.listOf(List.of(baseBook, testBook)));
        // then

        mockMvc.perform(get("/api/books/stock/category/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].bookId").value(1L))
            .andExpect(jsonPath("$.[0].title").value(baseBook.getTitle()))
            .andExpect(jsonPath("$.[0].publisherName").value(baseBook.getPublisher().getName()))
            .andExpect(jsonPath("$.[1].bookId").value(2L))
            .andExpect(jsonPath("$.[1].title").value(testBook.getTitle()))
            .andExpect(jsonPath("$.[1].publisherName").value(testBook.getPublisher().getName()))
            .andDo(BookDocumentation.findAllStockCountByCategoryId())
            ;
    }

    @DisplayName ("?????? ?????? ????????? ?????????")
    @Test
    void bookStockCountUpdate () throws Exception {
        // given
        // when
        // then
        mockMvc.perform(patch("/api/books/{id}",1L)
            .param("stockCount", "10")
        )
            .andExpect(status().isOk())
            .andDo(BookDocumentation.updateStockCount());
    }

    @DisplayName ("???????????? ????????? ?????????")
    @Test
    void bookUpdateTest () throws Exception {
        // give
        BookUpdateRequest updateRequest = BookUpdateRequest.builder()
            .title("?????? ?????? ????????? ?????????")
            .bookIntroduction("?????? ????????? ?????? ?????????.")
            .thumbnailUrl("update image test")
            .build();

        // when
        // then
        mockMvc.perform(put("/api/books/{id}",1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest))
        )
            .andExpect(status().isOk())
            .andDo(BookDocumentation.updateBookInfo());
    }

    @DisplayName ("????????? ?????? Id??? ?????? ?????? ?????????")
    @Test
    void ifBookNotFound () throws Exception {
        // given
        // when
        when(bookService.findBookDetailById(anyLong()))
            .thenThrow(new BookNotFoundException(12L));

        // then
        mockMvc.perform(get("/api/books/{id}", 12))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorCode").value(400))
            .andExpect(jsonPath("$.errorMessage").value("?????? ID??? ????????? ????????? ??? ????????????." + "  ?????? ID: " + 12));
    }
}