package io.bloobook.bookmanageapp.service;

import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.common.dto.request.BookUpdateRequest;
import io.bloobook.bookmanageapp.common.dto.response.BookDetailResponse;
import io.bloobook.bookmanageapp.common.dto.response.BookSimpleResponse;
import io.bloobook.bookmanageapp.common.exception.AlreadyExistBookException;
import io.bloobook.bookmanageapp.common.exception.BookNotFoundException;
import io.bloobook.bookmanageapp.common.exception.CategoryNotFoundException;
import io.bloobook.bookmanageapp.common.exception.PublisherNotFoundException;
import io.bloobook.bookmanageapp.entity.book.Book;
import io.bloobook.bookmanageapp.entity.book.BookRepository;
import io.bloobook.bookmanageapp.entity.bookLocation.BookLocation;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.category.CategoryRepository;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.publisher.PublisherRepository;
import java.util.Collections;
import java.util.List;
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
    public Book saveNewBook ( BookSaveRequest saveRequest ) {

        isDuplicatedBook(saveRequest.getBookCode());
        Category category = findCategoryById(saveRequest.getCategoryId());
        Publisher publisher = findPublisherByBusinessNumber(saveRequest.getPublisherBusinessNumber());
        BookLocation location = createLocation(category, saveRequest.getLocationCode());

        return saveNewBook ( saveRequest, category, publisher, location);
    }

    @Transactional (readOnly = true)
    public BookDetailResponse findBookDetailById ( Long id ) {
        Book findBook = findBookById(id);
        return BookDetailResponse.of(findBook);
    }

    @Transactional (readOnly = true)
    public List<BookSimpleResponse> findBooksByTitle ( String title ) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        return Collections.unmodifiableList(BookSimpleResponse.listOf(books));
    }

    @Transactional (readOnly = true)
    public List<BookSimpleResponse> findAllByCategoryId ( Long categoryId ) {
        List<Book> books = bookRepository.findAllByCategoryId(categoryId);
        return BookSimpleResponse.listOf(books);
    }

    @Transactional
    public Book updateBookInfo ( Long id, BookUpdateRequest updateRequest ) {
        Book savedBook = findBookById(id);
        return savedBook.updateBook(updateRequest);
    }


    private void isDuplicatedBook ( String bookCode ) {
        if ( bookRepository.findByBookCode(bookCode).isPresent() ) {
            throw new AlreadyExistBookException(bookCode);
        }
    }

    private Book saveNewBook ( BookSaveRequest request, Category category, Publisher publisher,
        BookLocation bookLocation ) {
        Book newBook = request.createNewBook(category, publisher, bookLocation);
        return bookRepository.save(newBook);
    }

    private BookLocation createLocation ( Category category, String location ) {
        return BookLocation.builder()
            .categoryName(category.getCategoryName())
            .locationCode(location)
            .build();
    }

    private Book findBookById ( Long bookId ) {
        return bookRepository.findById(bookId)
            .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private Category findCategoryById ( Long categoryId ) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    private Publisher findPublisherByBusinessNumber ( String businessNumber ) {
        return publisherRepository.findByBusinessNumber(businessNumber)
            .orElseThrow(() -> new PublisherNotFoundException(businessNumber));
    }
}
