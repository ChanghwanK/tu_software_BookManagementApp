package io.bloobook.bookmanageapp.common.dto.response;

import io.bloobook.bookmanageapp.entity.rental.Rental;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/20
 */


@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class RentalSimpleResponse {

    private Long bookId;
    private String title;
    private String publisherName;
    private LocalDate rentalAt;
    private LocalDate expiredAt;

    public static RentalSimpleResponse of ( Rental rental ) {
        return new RentalSimpleResponse (
            rental.getBook().getId(),
            rental.getBook().getTitle(),
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
