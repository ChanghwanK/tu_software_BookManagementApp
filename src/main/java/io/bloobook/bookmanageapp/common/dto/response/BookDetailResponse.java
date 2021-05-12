package io.bloobook.bookmanageapp.common.dto.response;

import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/12
 */

@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class BookDetailResponse {
    // TODO: 2021.05.12 -Blue BookResponse 객체를 구현해주세요
//    private Long bookId;
//    private String bookCode;
//    private String title;
//    private String bookIntroduction;
//    private String author;
//    private String thumbnailImage;
//    private int stockCount;
//    private int totalRentalCount;
//    private String bookStatus;
//    private LocalDate publicationAt;
//    private String publisherName;
//    private String categoryName;


//    public static BookDetailResponse of ( Book savedBook ) {
//        return new BookDetailResponse (
//            savedBook.getId(),
//            savedBook.getBookCode(),
//            savedBook.getTitle(),
//            savedBook.getBookIntroduction(),
//            savedBook.getAuthor(),
//            savedBook.getThumbnail(),
//            savedBook.getStockCount(),
//            savedBook.getTotalRentalCount(),
//            savedBook.getBookStatus().getDescription(),
//            savedBook.getPublicationAt(),
//            savedBook.getPublisher().getName(),
//            savedBook.getCategory().getCategoryName()
//        );
//    }

    private Book savedBookInfo;
    private Publisher publisherInfo;
    private Category categoryInfo;

    public static BookDetailResponse of ( Book savedBook ) {
        return new BookDetailResponse(savedBook, savedBook.getPublisher(), savedBook.getCategory());
    }

}
