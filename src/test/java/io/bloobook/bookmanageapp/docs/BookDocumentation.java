package io.bloobook.bookmanageapp.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

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

    public static ResultHandler findBookDetail () {
        return document("books/findById",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("id").description("조회 도서 ID")
            ),
            responseFields(
                fieldWithPath("bookCode").type(JsonFieldType.STRING).description("도서 코드"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("도서 제목"),
                fieldWithPath("bookIntroduction").type(JsonFieldType.STRING).description("도서 소개글"),
                fieldWithPath("author").type(JsonFieldType.STRING).description("도서 작가"),
                fieldWithPath("thumbnailUrl").type(JsonFieldType.STRING).description("썸네일 URL"),
                fieldWithPath("stockCount").type(JsonFieldType.NUMBER).description("도서 재고 수량"),
                fieldWithPath("publicationAt").type(JsonFieldType.STRING).description("도서 출판일"),
                fieldWithPath("publisherName").type(JsonFieldType.STRING).description("출판사 이름"),
                fieldWithPath("publisherTelNumber").type(JsonFieldType.STRING).description("출판사 대표번호"),
                fieldWithPath("categoryName").type(JsonFieldType.STRING).description("카테고리 명"),
                fieldWithPath("bookLocation").type(JsonFieldType.STRING).description("도서 위치")
            )
        );
    }

    public static ResultHandler findBookByTitle () {
        return document("books/findByTitle",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("title").description("도서 제목")
            ),
            responseFields(
                fieldWithPath("[].bookId").type(JsonFieldType.NUMBER).description("도서 Id"),
                fieldWithPath("[].title").type(JsonFieldType.STRING).description("도서 제목"),
                fieldWithPath("[].author").type(JsonFieldType.STRING).description("도서 작가"),
                fieldWithPath("[].publisherName").type(JsonFieldType.STRING).description("출판사 명"),
                fieldWithPath("[].thumbnailUrl").type(JsonFieldType.STRING).description("썸네일 URL")
            )
        );
    }

    public static ResultHandler findAllByCategoryId () {
        return document("books/findAllByCategoryId",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("categoryId").description("카테고리 ID")
            ),
            responseFields(
                fieldWithPath("[].bookId").type(JsonFieldType.NUMBER).description("도서 Id"),
                fieldWithPath("[].title").type(JsonFieldType.STRING).description("도서 제목"),
                fieldWithPath("[].author").type(JsonFieldType.STRING).description("도서 작가"),
                fieldWithPath("[].publisherName").type(JsonFieldType.STRING).description("출판사 명"),
                fieldWithPath("[].thumbnailUrl").type(JsonFieldType.STRING).description("썸네일 URL")
            )
        );
    }

    public static ResultHandler updateBookInfo () {
        return document("books/updateBook",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id").description("도서 ID")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목").optional(),
                    fieldWithPath("bookIntroduction").type(JsonFieldType.STRING).description("수정할 소개글").optional(),
                    fieldWithPath("thumbnailUrl").type(JsonFieldType.STRING).description("수정할 썸네일").optional()
                )
            );
    }
}
