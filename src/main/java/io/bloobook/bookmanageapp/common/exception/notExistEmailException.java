package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/21
 */
public class notExistEmailException extends RuntimeException {
    private static final String MESSAGE = "존재 하지 않는 이메일 입니다.";
    public notExistEmailException ( String email ) {
        super(MESSAGE);
    }
}
