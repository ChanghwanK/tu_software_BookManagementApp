package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */
public class AlreadyExistPublisherException extends RuntimeException {

    private static final String MESSAGE = "이미 존재하는 출판사 입니다.";

    public AlreadyExistPublisherException ( String businessNumber ) {
        super(MESSAGE + " 사업자 번호: " + businessNumber);
    }
}
