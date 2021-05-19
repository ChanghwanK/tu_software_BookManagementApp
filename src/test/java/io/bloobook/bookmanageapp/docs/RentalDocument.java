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
}
