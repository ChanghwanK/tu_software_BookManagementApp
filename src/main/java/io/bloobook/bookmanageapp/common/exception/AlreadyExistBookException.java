package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/11
 */
public class AlreadyExistBookException extends RuntimeException {

    private static final String MESSAGE = "이미 존재하는 도서 코드 입니다.";

    public AlreadyExistBookException ( String bookCode ) {
        super(MESSAGE + " 도서 코드: " + bookCode);
    }

}
