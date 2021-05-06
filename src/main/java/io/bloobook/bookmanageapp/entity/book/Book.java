package io.bloobook.bookmanageapp.entity.book;

import io.bloobook.bookmanageapp.common.enumclass.status.BookStatus;
import io.bloobook.bookmanageapp.entity.BaseTimeEntity;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.bestBook.BestBook;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.rental.Rental;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */

@ToString(exclude = {"publisher", "bookLocation", "rental", "category","bestBook"})
@NoArgsConstructor
@Getter
@Entity
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column (nullable = false)
    private String bookCode;

    @NonNull
    @Column (nullable = false)
    private String title;

    @NonNull
    @Column (nullable = false, columnDefinition = "TEXT")
    private String bookIntroduction;

    @NonNull
    @Column (nullable = false)
    private String author;

    @NonNull
    @Column(nullable = false)
    private String thumbnail;

    @Column (nullable = false)
    private int stockCount;

    @Column (nullable = false)
    private int totalRentalCount;

    @NonNull
    @Column (nullable = false)
    @Enumerated (value = EnumType.STRING)
    private BookStatus bookStatus;

    @NonNull
    @Column (nullable = false)
    private LocalDate publicationAt;                     // 초판 발행일

    @ManyToOne (fetch = FetchType.LAZY)
    private Rental rental;

    @ManyToOne (fetch = FetchType.LAZY)
    private Publisher publisher;

    @OneToOne
    private BookLocation bookLocation;

    @OneToOne
    private BestBook bestBook;

    @ManyToOne (fetch = FetchType.LAZY)
    private Category category;

    @Builder
    public Book ( @NonNull String bookCode, @NonNull String title,
        @NonNull String bookIntroduction, @NonNull String author,
        @NonNull String thumbnail, int stockCount, int totalRentalCount,
        @NonNull BookStatus bookStatus, @NonNull LocalDate publicationAt,
        BookLocation bookLocation, BestBook bestBook,
        Category category ) {
        this.bookCode = bookCode;
        this.title = title;
        this.bookIntroduction = bookIntroduction;
        this.author = author;
        this.thumbnail = thumbnail;
        this.stockCount = stockCount;
        this.totalRentalCount = totalRentalCount;
        this.bookStatus = bookStatus;
        this.publicationAt = publicationAt;
        this.bookLocation = bookLocation;
        this.bestBook = bestBook;
        this.category = category;
    }

    public void setRelationWithPublisher ( Publisher publisher ) {
        if ( publisher != null ) {
            this.publisher = publisher;
        }
    }

    public void addRentalInfo ( Rental rental ) {
        if (this.rental == null) {
            this.rental = rental;
        } else {
            // TODO: 2021.05.06 -Blue "이미 등록된 대여 정보가 있습니다." 반환
        }
    }

    public void removeBookRental () {
        this.rental = null;
    }
}