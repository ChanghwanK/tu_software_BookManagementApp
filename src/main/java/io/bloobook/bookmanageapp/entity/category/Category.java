package io.bloobook.bookmanageapp.entity.category;

import io.bloobook.bookmanageapp.common.enumclass.CategoryName;
import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.entity.BaseTimeEntity;
import io.bloobook.bookmanageapp.entity.book.Book;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column (nullable = false)
    @Enumerated (value = EnumType.STRING)
    private CategoryName categoryName;

    @Enumerated (value = EnumType.STRING)
    private CategoryStatus categoryStatus;

    @OneToMany (
        mappedBy = "category",
        fetch = FetchType.LAZY,
        cascade = CascadeType.REFRESH,
        orphanRemoval = true
    )
    private final List<Book> books = new ArrayList<> ();

    @Builder
    public Category ( @NonNull CategoryName categoryName, CategoryStatus categoryStatus ) {
        this.categoryName = categoryName;
        this.categoryStatus = categoryStatus;
    }
}
