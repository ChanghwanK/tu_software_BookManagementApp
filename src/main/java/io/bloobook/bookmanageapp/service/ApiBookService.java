package io.bloobook.bookmanageapp.service;

import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.common.exception.CategoryNotFoundException;
import io.bloobook.bookmanageapp.common.exception.PublisherNotFoundException;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.book.BookRepository;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.category.CategoryRepository;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.publisher.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/08
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ApiBookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    @Transactional
    public Book saveNeBook ( BookSaveRequest bookSaveRequest ) {
        Book baseBook = bookSaveRequest.toEntity ();
        Category category = findCategoryById ( bookSaveRequest.getCategoryId () );
        BookLocation bookLocation = addBookLocation ( category );
        Publisher publisher = findPublisherByBusinessNumber (
            bookSaveRequest.getPublisherBusinessNumber () );

        return bookRepository
            .save ( createNewBook ( baseBook, category, bookLocation, publisher ) );
    }

    private Book createNewBook ( Book baseBook, Category category, BookLocation bookLocation,
        Publisher publisher ) {
        baseBook.setRelationWithPublisher ( publisher );
        baseBook.setCategoryInfo ( category );
        baseBook.setBookLocation ( bookLocation );
        return baseBook;
    }

    private BookLocation addBookLocation ( Category category ) {
        return BookLocation.builder ()
            .categoryName ( "ART" )
            .locationCode ( "1층 A - 2 선반" )
            .build ();
    }

    private Category findCategoryById ( Long categoryId ) {
        return categoryRepository.findById ( categoryId )
            .orElseThrow ( () -> new CategoryNotFoundException ( categoryId ) );
    }

    private Publisher findPublisherByBusinessNumber ( String businessNumber ) {
        return publisherRepository.findByBusinessNumber ( businessNumber )
            .orElseThrow ( () -> new PublisherNotFoundException ( businessNumber ) );
    }
}
