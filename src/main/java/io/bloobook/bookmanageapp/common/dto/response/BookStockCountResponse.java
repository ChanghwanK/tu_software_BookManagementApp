package io.bloobook.bookmanageapp.common.dto.response;

import io.bloobook.bookmanageapp.entity.book.Book;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 *  도서 ID, 도서 Title, 도서 재고 개수
 *
 *  @CreateBy: Bloo
 *  @Date: 2021/05/23
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class BookStockCountResponse {

    private Long bookId;
    private String title;
    private String publisherName;
    private int stockCount;

    public static BookStockCountResponse of ( Book book ) {
        return BookStockCountResponse.builder()
            .bookId(book.getId())
            .title(book.getTitle())
            .publisherName(book.getPublisher().getName())
            .stockCount(book.getStockCount())
            .build();
    }

    public static List<BookStockCountResponse> listOf ( List<Book> books ) {
        List<BookStockCountResponse> stockCountResponses = new ArrayList<>();
        for ( Book book : books ) {
            stockCountResponses.add(of(book));
        }
        return stockCountResponses;
    }
}
