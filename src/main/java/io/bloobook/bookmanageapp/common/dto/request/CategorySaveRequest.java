package io.bloobook.bookmanageapp.common.dto.request;

import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.entity.category.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */

@ToString
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class CategorySaveRequest {

    private String categoryCode;
    private String categoryName;

    @Builder
    public CategorySaveRequest ( String categoryCode, String categoryName ) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public Category toEntity () {
        return Category.builder()
            .categoryCode(categoryCode)
            .categoryName(categoryName)
            .categoryStatus(CategoryStatus.REGISTER)
            .build();
    }
}
