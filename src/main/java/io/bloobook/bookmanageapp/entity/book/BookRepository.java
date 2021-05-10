package io.bloobook.bookmanageapp.entity.book;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query ("select b "
        + "from Book b "
        + "join fetch b.category "
        + "join fetch b.bookLocation "
        + "join fetch b.category")
    List<Book> findByIdJoinFetch( Long id );

    Optional<Book> findByBookCode ( String bookCode );
}
