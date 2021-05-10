package io.bloobook.bookmanageapp.entity.rental;

import io.bloobook.bookmanageapp.common.enumclass.status.RentalStatus;
import io.bloobook.bookmanageapp.entity.BaseTimeEntity;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.user.User;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
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

    @OneToMany (
        mappedBy = "rental",
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private final Map<Long, Book> books = new HashMap<>();


    @NonNull
    @Column (nullable = false)
    private LocalDate expiredAt;

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

    public void startBookRental ( Book book ) {
        book.addRentalInfo(this);
        books.put(book.getId(), book);
    }

    public void endBookRental ( Book book ) {
        book.removeBookRental();
        books.remove(book.getId(), book);
    }
}