package io.bloobook.bookmanageapp.common.exception;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/22
 */
public class RentalNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당 Id 의 대여 정보가 존재하지 않습니다.";
    public RentalNotFoundException (Long id) {
        super(MESSAGE + " 대여 Id: " + id);
    }
}
