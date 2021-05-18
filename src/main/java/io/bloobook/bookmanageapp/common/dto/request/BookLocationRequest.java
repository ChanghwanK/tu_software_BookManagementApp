package io.bloobook.bookmanageapp.common.dto.request;

import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class BookLocationRequest {

    private String categoryName;
    private String locationCode;

    public BookLocation toEntity () {
        return BookLocation.builder()
            .categoryName(categoryName)
            .locationCode(locationCode)
            .build();
    }
}
