package io.bloobook.bookmanageapp.entity.category;

import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.entity.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */
@ToString
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String categoryCode;

    @NonNull
    private String categoryName;

    @NonNull
    @Enumerated (value = EnumType.STRING)
    private CategoryStatus categoryStatus;

    @Builder
    public Category ( @NonNull String categoryCode, @NonNull String categoryName,
        @NonNull CategoryStatus categoryStatus ) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.categoryStatus = categoryStatus;
    }
}
