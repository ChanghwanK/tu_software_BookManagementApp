package io.bloobook.bookmanageapp.entity.publisher;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/07
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByBusinessNumber ( String businessNumber );
}
