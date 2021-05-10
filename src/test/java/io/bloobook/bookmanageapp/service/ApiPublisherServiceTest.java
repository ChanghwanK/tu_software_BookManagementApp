package io.bloobook.bookmanageapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.bloobook.bookmanageapp.common.dto.request.PublisherSaveRequest;
import io.bloobook.bookmanageapp.entity.publisher.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/11
 */

@ExtendWith(MockitoExtension.class)
class ApiPublisherServiceTest {

    @InjectMocks
    private ApiPublisherService publisherService;

    @Mock
    private PublisherRepository publisherRepository;

    @BeforeEach
    void setUp () {

    }

    @DisplayName ("새로운 출판사 등록을 테스트")
    @Test
    void saveNewPublisher () {
        // given
        PublisherSaveRequest saveRequest = PublisherSaveRequest.builder()
            .businessNumber("A-2938-293")
            .telNumber("02-3948-3932")
            .name("책 사랑")
            .address("서울특별시 강북")
            .build();


        // when
        publisherService.saveNewPublisher(saveRequest);

        // then
        verify(publisherRepository, times(1)).save(any());
    }

    // TODO: 2021.05.11 -Blue 이미 존재하는 사업자 번호에 대한 예외처리 구현하기


}