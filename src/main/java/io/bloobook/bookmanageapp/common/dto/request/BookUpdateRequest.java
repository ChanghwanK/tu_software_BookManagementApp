package io.bloobook.bookmanageapp.common.dto.request;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * - 작가의 요청으로 인한 도서 제목, 소개글, 썸네일 이미지 수정을 지원합니다.
 *
 * @CreateBy: Bloo
 * @Date: 2021/05/15
 */

@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class BookUpdateRequest {

    private String title;
    private String bookIntroduction;
    private String thumbnailUrl;

    @Builder
    public BookUpdateRequest ( String title, String bookIntroduction, String thumbnailUrl ) {
        this.title = title;
        this.bookIntroduction = bookIntroduction;
        this.thumbnailUrl = thumbnailUrl;
    }
}
