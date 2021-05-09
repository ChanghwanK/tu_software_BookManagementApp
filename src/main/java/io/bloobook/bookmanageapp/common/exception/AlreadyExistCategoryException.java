package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */

public class AlreadyExistCategoryException extends RuntimeException {
    private static final String MESSAGE = "이미 존재하는 카테고리 이름 입니다.";
    public AlreadyExistCategoryException ( String categoryName ) {
        super ( MESSAGE + " 카테고리 명: " + categoryName );
    }
}
