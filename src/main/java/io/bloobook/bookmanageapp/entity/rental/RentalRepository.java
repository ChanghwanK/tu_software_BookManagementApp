package io.bloobook.bookmanageapp.entity.rental;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByStartAtBetween( LocalDate startDate, LocalDate expiredAt );
}
