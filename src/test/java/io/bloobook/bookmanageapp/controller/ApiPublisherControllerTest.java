package io.bloobook.bookmanageapp.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bloobook.bookmanageapp.common.dto.request.PublisherSaveRequest;
import io.bloobook.bookmanageapp.docs.PublisherDocument;
import io.bloobook.bookmanageapp.service.ApiPublisherService;
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
 * @Date: 2021/05/27
 */

@ExtendWith (RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(ApiPublisherController.class)
class ApiPublisherControllerTest {

    @MockBean
    private ApiPublisherService publisherService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp ( WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter("UTF-8"))
            .apply(documentationConfiguration(provider))
            .alwaysDo(print())
            .build();
    }

    @DisplayName ("출판사 등록 테스트")
    @Test
    void registPublisher ()  throws Exception {
        // given
        PublisherSaveRequest saveRequest = PublisherSaveRequest.builder()
            .name("책 사랑")
            .address("서울특별시 강북")
            .businessNumber("A-2938-293")
            .telNumber("02-3948-3932")
            .build();
        // when
        // then
        mockMvc.perform(post("/api/publisher")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saveRequest))
        )
            .andExpect(status().isCreated())
            .andDo(PublisherDocument.registPublisher());

    }

    @DisplayName ("출판사 계약 해지 테스트")
    @Test
    void terminatePublisher () throws Exception{
        // give
        // when
        // then
        mockMvc.perform(put("/api/publisher/{id}", 1L))
            .andExpect(status().isOk());

    }
}