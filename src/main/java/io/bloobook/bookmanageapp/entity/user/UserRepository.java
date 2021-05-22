package io.bloobook.bookmanageapp.entity.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail ( String email );
}
