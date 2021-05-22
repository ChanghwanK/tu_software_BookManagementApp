package io.bloobook.bookmanageapp.common.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/18
 */
@ToString
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class RentalRequest {
    private Long bookId;
    private Long userId;

    @Builder
    public RentalRequest ( Long bookId, Long userId ) {
        this.bookId = bookId;
        this.userId = userId;
    }
}
