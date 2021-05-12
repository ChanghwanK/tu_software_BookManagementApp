package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/12
 */
public class BookNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당 ID의 도서를 조회할 수 없습니다.";

    public BookNotFoundException ( Long id ) {

    }
}
