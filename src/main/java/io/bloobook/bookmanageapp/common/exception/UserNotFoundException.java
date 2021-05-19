package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/18
 */
public class UserNotFoundException extends RuntimeException{
    private static final String MESSAGE = "해당 Id의 사용자를 찾을 수 없습니다.";
    public UserNotFoundException (Long id) {
        super(MESSAGE + " ID: " + id );
    }
}
