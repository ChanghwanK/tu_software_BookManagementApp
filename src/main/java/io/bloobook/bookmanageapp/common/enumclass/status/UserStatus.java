package io.bloobook.bookmanageapp.common.enumclass.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */

@AllArgsConstructor
@Getter
public enum UserStatus {

    REGISTER(1, "회원 등록 상태"),
    UNREGISTER(2, "회원 해지 상태");

    private final int id;
    private final String description;
}
