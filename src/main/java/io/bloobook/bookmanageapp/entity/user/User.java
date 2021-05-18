package io.bloobook.bookmanageapp.entity.user;

import io.bloobook.bookmanageapp.common.enumclass.status.UserStatus;
import io.bloobook.bookmanageapp.entity.BaseTimeEntity;
import io.bloobook.bookmanageapp.entity.rental.Rental;
import java.util.ArrayList;
import java.util.List;
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
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column (nullable = false)
    private String email;

    @NonNull
    @Column (nullable = false)
    private String password;

    @NonNull
    @Column (nullable = false)
    private String phoneNumber;

    @Column (nullable = false)
    private int rentalBookCount;

    @Enumerated (value = EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany (
        mappedBy = "user",
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    private final List<Rental> rentals = new ArrayList<>();

    @Builder
    public User ( @NonNull String email, @NonNull String password,
        @NonNull String phoneNumber, int rentalBookCount,
        UserStatus userStatus ) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.rentalBookCount = rentalBookCount;
        this.userStatus = userStatus;
    }

    public void registerRentalInfo ( Rental rental ) {
        rental.setRelationWithUserForRental(this);
        rentals.add(rental);
    }

    /**
     * 도서 등록 시 대여 카운트를 증가 시킨다.
     */
    public void increaseBookRentalCount () {
        this.rentalBookCount += 1;
    }

    /**
     * 도서 반납 시 대여 카운트를 감소 시킨다.
     */
    public void decreaseBookRentalCount () {
        if ( rentalBookCount > 0 ) {
            rentalBookCount--;
        } else {
            rentalBookCount = 0;
        }
    }
}
