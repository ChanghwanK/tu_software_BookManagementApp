package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/24
 */
public class BookStockCountZeroException extends RuntimeException {
    private static final String MESSAGE = "도서 재고개수가 0개 입니다.";
    public BookStockCountZeroException () {
        super(MESSAGE);
    }
}
