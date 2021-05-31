package io.bloobook.bookmanageapp.entity.rental;

import io.bloobook.bookmanageapp.common.enumclass.status.RentalStatus;
import io.bloobook.bookmanageapp.entity.BaseTimeEntity;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.user.User;
import java.time.LocalDate;
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
@ToString
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Rental extends BaseTimeEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated (value = EnumType.STRING)
    private RentalStatus rentalStatus;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @OneToOne (fetch = FetchType.LAZY)
    private Book book;

    @Column (nullable = false)
    private boolean onRental;

    @NonNull
    @Column (nullable = false)
    private LocalDate startAt;

    @NonNull
    @Column (nullable = false)
    private LocalDate expiredAt;

    @Builder
    public Rental ( Long id, User user, Book book ) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.rentalStatus = RentalStatus.RENTAL;
        this.onRental = true;
        this.startAt = LocalDate.now();
        this.expiredAt = LocalDate.now().plusWeeks(2);
    }

    public static Rental of (Book book, User user) {
        return Rental.builder()
            .user(user)
            .book(book)
            .build();
    }

    public void returnPeriodExtend () {
        this.expiredAt = expiredAt.plusWeeks(1);
    }

    public void returnRentalBook () {
        this.onRental = false;
    }

    public void updateRentalStatusToNonReturn() {
       this.rentalStatus = RentalStatus.NON_RETURN;
    }
}