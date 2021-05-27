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
 * @Date: 2021/05/27
 */
public class PublisherDocument {

    public static ResultHandler registPublisher() {
        return document("publisher/regist",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("출판사 명"),
                    fieldWithPath("address").type(JsonFieldType.STRING).description("출판사 주소"),
                    fieldWithPath("businessNumber").type(JsonFieldType.STRING).description("사업자 번호"),
                    fieldWithPath("telNumber").type(JsonFieldType.STRING).description("출판사 대표 번호")
                )
            );
    }
}
