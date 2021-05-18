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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @ManyToOne
    private User user;

    @OneToOne
    private Book book;

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
        this.startAt = LocalDate.now();
        this.expiredAt = LocalDate.now().plusWeeks(2);
    }

    public void setRelationWithUserForRental ( User user ) {
        if ( this.user == null ) {
            this.user = user;
        } else {
            // TODO: 2021.05.06 -Blue  '해당 도서에 이미 등록된 사용자 대여정보가 있습니다.'
        }
    }

    public void returnPeriodExtend () {
        this.expiredAt.plusWeeks(1);
    }

}