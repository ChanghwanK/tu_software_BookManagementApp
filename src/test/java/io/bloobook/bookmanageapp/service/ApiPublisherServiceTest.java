package io.bloobook.bookmanageapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.bloobook.bookmanageapp.common.dto.request.PublisherSaveRequest;
import io.bloobook.bookmanageapp.common.enumclass.status.PublisherStatus;
import io.bloobook.bookmanageapp.common.exception.AlreadyExistPublisherException;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.publisher.PublisherRepository;
import java.util.Optional;
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

@ExtendWith (MockitoExtension.class)
class ApiPublisherServiceTest {

    @InjectMocks
    private ApiPublisherService publisherService;

    @Mock
    private PublisherRepository publisherRepository;

    private PublisherSaveRequest saveRequest;

    @BeforeEach
    void setUp () {
        saveRequest = PublisherSaveRequest.builder()
            .businessNumber("A-2938-293")
            .telNumber("02-3948-3932")
            .name("책 사랑")
            .address("서울특별시 강북")
            .build();
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

    @DisplayName ("계약 해지를 테스트")
    @Test
    void terminatePublisher () {
        // given
        Publisher publisher = Publisher.builder()
            .name("책 사랑")
            .address("서울시 강남")
            .telNumber("02-1944-9392")
            .businessNumber("B-239-9381-33")
            .publisherStatus(PublisherStatus.REGISTER)
            .build();

        // when
        when(publisherRepository.findById(anyLong()))
            .thenReturn(Optional.of(publisher));

        publisherService.terminatePublisher(anyLong());
        // then

        assertThat(publisher.getPublisherStatus()).isEqualTo(PublisherStatus.UNREGISTER);

    }

    @DisplayName ("이미 등록된 비즈니스 번호에 대한 예외 테스트")
    @Test
    void ifAlreadyExistBusinessNumber () {
        // given

        // when
        Publisher mockPublisher = saveRequest.toEntity();

        when(publisherRepository.findByBusinessNumber(anyString()))
            .thenReturn(Optional.of(mockPublisher));

        // then
        assertThrows(
            AlreadyExistPublisherException.class,
            () -> publisherService.saveNewPublisher(saveRequest)
        );
    }
}