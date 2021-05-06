package io.bloobook.bookmanageapp.entity.book;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */
public interface BookRepository extends JpaRepository<Book, Long> {

}
