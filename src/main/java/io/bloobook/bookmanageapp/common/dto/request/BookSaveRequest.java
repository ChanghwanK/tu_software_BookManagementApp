package io.bloobook.bookmanageapp.common.dto.request;

import io.bloobook.bookmanageapp.entity.book.Book;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @CreateBy: Bloo
 * @Date: 2021/05/08
 */


@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class BookSaveRequest {
    private Long categoryId;
    private String locationCode;
    private String bookCode;
    private String title;
    private String bookIntroduction;
    private String author;
    private String thumbnail;
    private String publisherBusinessNumber;
    private LocalDate publicationAt;

    public Book toBaseBookEntity () {
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
