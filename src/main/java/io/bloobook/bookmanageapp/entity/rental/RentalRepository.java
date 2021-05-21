package io.bloobook.bookmanageapp.entity.rental;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */

public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query (value = "select r from Rental r where r.startAt >=:startedAt and r.expiredAt <=:expiredAt")
    List<Rental> findAllByStartAtAndExpiredAt( LocalDate startedAt, LocalDate expiredAt );
}
