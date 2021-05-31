package io.bloobook.bookmanageapp.entity.rental;

import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.RentalStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */

public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query (
        "select r "
        + "from Rental r "
        + "where r.startAt >=:startedAt and r.expiredAt <=:expiredAt and r.onRental=true")
    List<Rental> findAllByStartAtAndExpiredAtAndOnRental( LocalDate startedAt, LocalDate expiredAt );

    List<Rental> findRentalByUserEmail ( String email );

    @Query ("select r from Rental r where r.id =:id and r.onRental = true")
    Optional<Rental> findById ( Long id );

    @Query ("select r from Rental r where r.rentalStatus =:rentalStatus ")
    List<Rental> findAllNonRentals ( RentalStatus rentalStatus );
}
