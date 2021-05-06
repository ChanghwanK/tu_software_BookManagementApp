package io.bloobook.bookmanageapp.common.enumclass.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */

@AllArgsConstructor
@Getter
public enum CategoryStatus {

    REGISTER (1, "카테고리 운영 상태"),
    UNREGISTER (2, "카테고리 미운영 상태")
    ;

    private final int id;
    private final String description;
}
