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

    @Query (value =
        "select distinct b "
        + "from Book b "
        + "join fetch b.category "
        + "join fetch b.publisher "
        + "join fetch b.bookLocation "
        + "where b.id =:id" )
    Optional<Book> findByIdJoinFetch( Long id );

    List<Book> findByTitleContaining (String title);

    Optional<Book> findByBookCode ( String bookCode );
}
