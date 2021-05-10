package io.bloobook.bookmanageapp.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/10
 */
public class BookDocumentation {

    public static ResultHandler saveNewBook () {
        return document("books/save",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("조회할 도서 ID"),
                fieldWithPath("locationCode").type(JsonFieldType.STRING).description("도서 위치"),
                fieldWithPath("bookCode").type(JsonFieldType.STRING).description("도서 등록 코드"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("bookIntroduction").type(JsonFieldType.STRING).description("소개글"),
                fieldWithPath("author").type(JsonFieldType.STRING).description("작가명"),
                fieldWithPath("thumbnail").type(JsonFieldType.STRING).description("썸네일 URL"),
                fieldWithPath("publisherBusinessNumber").type(JsonFieldType.STRING).description("출판사 사업자 번호"),
                fieldWithPath("publicationAt").type(JsonFieldType.STRING).description("도서 설명")
            )
        );
    }
}
