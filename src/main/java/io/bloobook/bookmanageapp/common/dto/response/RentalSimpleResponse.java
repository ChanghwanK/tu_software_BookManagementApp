package io.bloobook.bookmanageapp.common.dto.response;

import io.bloobook.bookmanageapp.entity.rental.Rental;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/20
 */


@ToString
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class RentalSimpleResponse {

    private Long bookId;
    private Long rentalId;
    private String title;
    private String author;
    private String publisherName;
    private LocalDate startedAt;
    private LocalDate expiredAt;

    public static RentalSimpleResponse of ( Rental rental ) {
        return new RentalSimpleResponse (
            rental.getBook().getId(),
            rental.getId(),
            rental.getBook().getTitle(),
            rental.getBook().getAuthor(),
            rental.getBook().getPublisher().getName(),
            rental.getStartAt(),
            rental.getExpiredAt()
        );
    }

    public static List<RentalSimpleResponse> listOf (List<Rental> rentals) {

        List<RentalSimpleResponse> responses = new ArrayList<>();

        for ( Rental rental : rentals) {
            responses.add(of(rental));
        }
       return responses;
    }
}
