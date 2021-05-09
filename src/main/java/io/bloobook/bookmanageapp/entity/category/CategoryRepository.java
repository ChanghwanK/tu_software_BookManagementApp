package io.bloobook.bookmanageapp.entity.category;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName ( String categoryName );
}
