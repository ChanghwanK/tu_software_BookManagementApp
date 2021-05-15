package io.bloobook.bookmanageapp.common.dto.request;

import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
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

    public Book createNewBook ( Category category, Publisher publisher, BookLocation bookLocation ) {
        return Book.builder()
            .bookCode(bookCode)
            .title(title)
            .bookIntroduction(bookIntroduction)
            .author(author)
            .thumbnail(thumbnail)
            .publicationAt(publicationAt)
            .category(category)
            .publisher(publisher)
            .bookLocation(bookLocation)
            .build();
    }
}
