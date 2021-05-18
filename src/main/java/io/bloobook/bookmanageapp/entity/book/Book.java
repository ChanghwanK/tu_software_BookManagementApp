package io.bloobook.bookmanageapp.entity.book;

import io.bloobook.bookmanageapp.common.dto.request.BookUpdateRequest;
import io.bloobook.bookmanageapp.common.enumclass.status.BookStatus;
import io.bloobook.bookmanageapp.entity.BaseTimeEntity;
import io.bloobook.bookmanageapp.entity.bestBook.BestBook;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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


@ToString (exclude = { "publisher", "bookLocation", "rental", "category", "bestBook" })
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column (nullable = false, unique = true)
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
    @Column (nullable = false)
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


    @JoinColumn (name = "publisher_id")
    @ManyToOne (fetch = FetchType.LAZY)
    private Publisher publisher;


    @OneToOne (
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    private BookLocation bookLocation;


    @OneToOne (
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private BestBook bestBook;


    @JoinColumn (name = "category_id")
    @ManyToOne (fetch = FetchType.LAZY)
    private Category category;

    @Builder
    public Book ( Long id, @NonNull String bookCode, @NonNull String title,
        @NonNull String bookIntroduction, @NonNull String author,
        @NonNull String thumbnail, @NonNull LocalDate publicationAt, Publisher publisher,
        BookLocation bookLocation, Category category ) {
        this.id = id;
        this.bookCode = bookCode;
        this.title = title;
        this.bookIntroduction = bookIntroduction;
        this.author = author;
        this.thumbnail = thumbnail;
        this.stockCount = 5;
        this.totalRentalCount = 0;
        this.bookStatus = BookStatus.REGISTER;
        this.publicationAt = publicationAt;
        this.publisher = publisher;
        this.bookLocation = bookLocation;
        this.category = category;
    }

    public Book updateBook ( BookUpdateRequest updateRequest ) {
        this.title = updateRequest.getTitle();
        this.bookIntroduction = updateRequest.getBookIntroduction();
        this.thumbnail = updateRequest.getThumbnailUrl();
        return this;
    }

    public void increaseStockCount ( int stockCount ) {
        this.stockCount = stockCount;
    }

    public void increaseTotalRentalCount () {
        this.totalRentalCount += 1;
    }
}
