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
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/19
 */
public class RentalDocument {

    public static ResultHandler registRental() {
        return document("rental/regist",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("bookId").type(JsonFieldType.NUMBER).description("도서 ID"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 ID")
            )
        );
    }

    public static ResultHandler findAllBetweenPeriod() {
        return document("rental/findAllBetweenPeriod",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestParameters(
                parameterWithName("startedAt").description("대여 날짜"),
                parameterWithName("expiredAt").description("반납 날짜")
            ),
            responseFields(
                fieldWithPath("[].bookId").type(JsonFieldType.NUMBER).description("도서 ID"),
                fieldWithPath("[].rentalId").type(JsonFieldType.NUMBER).description("대여 ID"),
                fieldWithPath("[].title").type(JsonFieldType.STRING).description("도서 제목"),
                fieldWithPath("[].author").type(JsonFieldType.STRING).description("도서 작가"),
                fieldWithPath("[].publisherName").type(JsonFieldType.STRING).description("출판사 이름"),
                fieldWithPath("[].startedAt").type(JsonFieldType.STRING).description("대여 날짜"),
                fieldWithPath("[].expiredAt").type(JsonFieldType.STRING).description("반납 날짜")
            )
        );
    }

    public static ResultHandler findAllRentalsByUserEmail () {
        return document("rental/findAllByUserEmail",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestParameters(
                parameterWithName("email").description("사용자 이메일")
            ),
            responseFields(
                fieldWithPath("[].bookId").type(JsonFieldType.NUMBER).description("도서 ID"),
                fieldWithPath("[].rentalId").type(JsonFieldType.NUMBER).description("대여 ID"),
                fieldWithPath("[].title").type(JsonFieldType.STRING).description("도서 제목"),
                fieldWithPath("[].author").type(JsonFieldType.STRING).description("도서 작가"),
                fieldWithPath("[].publisherName").type(JsonFieldType.STRING).description("출판사 이름"),
                fieldWithPath("[].startedAt").type(JsonFieldType.STRING).description("대여 날짜"),
                fieldWithPath("[].expiredAt").type(JsonFieldType.STRING).description("반납 날짜")
            )
        );
    }

    public static ResultHandler expandRentalPeriod () {
        return document("rental/expandPeriod",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id").description("대여 ID")
                )
            );
    }

    public static ResultHandler returnRentalBook () {
        return document("rental/return",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("id").description("대여 ID")
            )
        );
    }
}
