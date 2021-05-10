package io.bloobook.bookmanageapp.common.dto.request;

import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PRIVATE)
@Getter
public class PublisherSaveRequest {

    @NotBlank (message = "비즈니스 번호는 필수 입력값입니다.")
    private String businessNumber;
    @NotBlank (message = "출판사 대표 전화 번호는 필수 입력값입니다.")
    private String telNumber;                                   // 대표 전화번호
    @NotBlank (message = "출판사 명은 필수 입력값입니다.")
    private String name;
    @NotBlank (message = "주소명은 필수 입력값입니다.")
    private String address;


    public Publisher toEntity () {
        return Publisher.builder()
            .businessNumber(businessNumber)
            .telNumber(telNumber)
            .name(name)
            .address(address)
            .publisherStatus(PublisherStatus.REGISTER)
            .build();
    }
}
