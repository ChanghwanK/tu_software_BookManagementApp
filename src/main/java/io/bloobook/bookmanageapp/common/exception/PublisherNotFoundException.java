package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/26
 */
public class PublisherNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당 ID 의 출판사가 존재하지 않습니다.";

    public PublisherNotFoundException () {
        super(MESSAGE);
    }
}
