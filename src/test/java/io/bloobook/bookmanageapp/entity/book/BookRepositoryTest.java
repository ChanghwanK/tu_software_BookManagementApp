package io.bloobook.bookmanageapp.entity.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.bloobook.bookmanageapp.common.enumclass.status.CategoryStatus;
import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocationRepository;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.category.CategoryRepository;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.publisher.PublisherRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/07
 */

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookLocationRepository bookLocationRepository;

    private BookLocation bookLocation;
    private Publisher publisher;
    private Category category;
    private Book kingOfLear;


    @BeforeEach
    void setUp () {
         bookLocation = BookLocation.builder ()
            .categoryName ( "ART" )
            .locationCode ( "B-2열 2층 선반" )
            .build ();

         publisher = Publisher.builder ()
            .businessNumber ( "A-29384-293818" )
            .telNumber ( "02-1553-8444" )
            .name ( "책사랑 출판사" )
            .address ( "서울광역시 성북구" )
            .publisherStatus ( PublisherStatus.REGISTER )
            .build ();

         category = Category.builder ()
            .categoryName ( "ART" )
            .categoryStatus ( CategoryStatus.REGISTER )
            .build ();

         kingOfLear = Book.builder ()
            .title ( "King Of Lear" )
            .author ( "셰익스피어" )
            .bookCode ( "A03-2938-9382" )
            .bookIntroduction ( "리어왕 이야기 소개글 입니다." )
            .thumbnail ( "www.ei2opeie.ewoewii" )
            .publicationAt ( LocalDate.of ( 1685,8,12 ) )
            .bookLocation ( bookLocation)
            .publisher ( publisher )
            .category ( category )
            .build ();
    }

   @DisplayName ("도서 저장 테스트")
   @Transactional
   @Test
   void saveBook () {
       // given

       // when
       publisherRepository.save ( publisher );
       categoryRepository.save ( category );
       bookRepository.save ( kingOfLear );

       // then
       Book savedBook = bookRepository.findById ( 1L ).get ();
       assertAll (
           () -> assertThat ( savedBook.getCategory ().getCategoryName () ).isEqualTo ( "ART" ),
           () -> assertThat ( savedBook.getBookLocation ().getLocationCode () ).isEqualTo ( "B-2열 2층 선반" ),
           () -> assertThat ( savedBook.getPublisher ().getBusinessNumber () ).isEqualTo ( "A-29384-293818" ),
           () -> assertThat ( savedBook.getBookCode () ).isEqualTo ( "A03-2938-9382" )
       );
   }
}