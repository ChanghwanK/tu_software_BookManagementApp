package io.bloobook.bookmanageapp.common.dto.response;

import io.bloobook.bookmanageapp.entity.rental.Rental;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Created by Bloo
 * @Date: 2021/05/29
 */


@ToString
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class NonReturnBooks {

    private Long bookId;
    private Long rentalId;
    private String email;
    private String bookTitle;

    public static NonReturnBooks of ( Rental rental ) {
        return new NonReturnBooks(
            rental.getBook().getId(),
            rental.getId(),
            rental.getUser().getEmail(),
            rental.getBook().getTitle()
        );
    }

    public static List<NonReturnBooks> listOf ( List<Rental> rentals ) {
        List<NonReturnBooks> nonReturnBooks = new ArrayList<>();

        for ( Rental rental : rentals ) {
            nonReturnBooks.add(NonReturnBooks.of(rental));
        }

        return nonReturnBooks;
    }
}
