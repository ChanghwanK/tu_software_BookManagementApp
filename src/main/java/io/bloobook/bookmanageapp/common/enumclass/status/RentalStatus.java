package io.bloobook.bookmanageapp.common.enumclass.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */

@AllArgsConstructor
@Getter
public enum RentalStatus {
    RENTAL(0, "도서 대여 상태"),
    RETURN(1, "도서 반납 상태"),
    NON_RETURN(2, "미반납 상태");

    private final int id;
    private final String description;
}
