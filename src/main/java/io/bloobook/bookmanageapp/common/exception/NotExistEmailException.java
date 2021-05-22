package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/21
 */
public class NotExistEmailException extends RuntimeException {
    private static final String MESSAGE = "존재 하지 않는 이메일 입니다.";
    public NotExistEmailException ( String email ) {
        super(MESSAGE);
    }
}
