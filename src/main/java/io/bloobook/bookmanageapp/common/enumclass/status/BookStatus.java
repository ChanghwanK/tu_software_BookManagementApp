package io.bloobook.bookmanageapp.common.enumclass.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */

@AllArgsConstructor
@Getter
public enum BookStatus {

    REGISTER(1, "도서 등록 상태"),
    UNREGISTER(2, "도서 해지 상태");

    private final int id;
    private final String description;
}
