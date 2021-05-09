package io.bloobook.bookmanageapp.common.dto.request;

import io.bloobook.bookmanageapp.entity.book.Book;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/08
 */

@ToString
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
public class BookSaveRequest {
    private Long categoryId;
    private String bookCode;
    private String title;
    private String bookIntroduction;
    private String author;
    private String thumbnail;
    private String publisherBusinessNumber;
    private LocalDate publicationAt;

    @Builder
    public BookSaveRequest ( Long categoryId, String bookCode, String title, String bookIntroduction,
        String author, String thumbnail, String categoryName, String publisherBusinessNumber,
        LocalDate publicationAt ) {
        this.categoryId = categoryId;
        this.bookCode = bookCode;
        this.title = title;
        this.bookIntroduction = bookIntroduction;
        this.author = author;
        this.thumbnail = thumbnail;
        this.publisherBusinessNumber = publisherBusinessNumber;
        this.publicationAt = publicationAt;
    }

    public Book toEntity () {
        return Book.builder ()
            .title ( title )
            .bookCode ( bookCode )
            .bookIntroduction ( bookIntroduction )
            .author ( author )
            .thumbnail ( thumbnail )
            .publicationAt ( publicationAt )
            .build ();
    }
}
