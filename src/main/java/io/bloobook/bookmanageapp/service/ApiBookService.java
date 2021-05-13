package io.bloobook.bookmanageapp.service;

import io.bloobook.bookmanageapp.common.dto.request.BookSaveRequest;
import io.bloobook.bookmanageapp.common.dto.response.BookDetailResponse;
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
        isDuplicatedBook(bookSaveRequest.getBookCode());

        Book baseBook = bookSaveRequest.toBaseBookEntity();

        Category category = findCategoryById(bookSaveRequest.getCategoryId());

        Publisher publisher = findPublisherByBusinessNumber(
            bookSaveRequest.getPublisherBusinessNumber());

        BookLocation bookLocation = createLocation(category,
            bookSaveRequest.getLocationCode());

        return bookRepository
            .save(createBookRelationOf(baseBook, category, bookLocation, publisher));
    }

    @Transactional (readOnly = true)
    public BookDetailResponse findBookById ( Long id ) {
        Book findBook = bookRepository.findByIdJoinFetch(id)
            .orElseThrow(() -> new BookNotFoundException(id));

        log.info("페치 조인을 통해 조회한 도서 >>> {}", findBook);

        return BookDetailResponse.of(findBook);
    }

    /**
     * 해당 인자들과 baseBook 과의 연관관계를 설정한다.
     */
    private Book createBookRelationOf ( Book baseBook, Category category, BookLocation bookLocation,
        Publisher publisher ) {
        baseBook.setRelationWithPublisher(publisher);
        baseBook.setRelationWithCategory(category);
        baseBook.setBookLocation(bookLocation);
        baseBook.increaseStockCount(5);
        return baseBook;
    }

    private void isDuplicatedBook ( String bookCode ) {
        if ( bookRepository.findByBookCode(bookCode).isPresent() ) {
            throw new AlreadyExistBookException(bookCode);
        }
    }

    private BookLocation createLocation ( Category category, String location ) {
        return BookLocation.builder()
            .categoryName(category.getCategoryName())
            .locationCode(location)
            .build();
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
