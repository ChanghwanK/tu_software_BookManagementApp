package io.bloobook.bookmanageapp.common.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/20
 */

@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class RentalSimpleResponse {

    private Long bookId;
    private String title;
    private String publisherName;
    private LocalDate rentalAt;
    private LocalDate expiredAt;

    public static List<BookSimpleResponse> listOf () {
        // TODO: 2021.05.20 -Blue 리스트 메소드 구현
       return null;
    }
}
