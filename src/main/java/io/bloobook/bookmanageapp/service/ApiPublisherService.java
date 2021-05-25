package io.bloobook.bookmanageapp.service;

import io.bloobook.bookmanageapp.common.dto.request.PublisherSaveRequest;
import io.bloobook.bookmanageapp.common.exception.AlreadyExistPublisherException;
import io.bloobook.bookmanageapp.common.exception.PublisherNotFoundException;
import io.bloobook.bookmanageapp.entity.publisher.Publisher;
import io.bloobook.bookmanageapp.entity.publisher.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ApiPublisherService {

    private final PublisherRepository publisherRepository;

    @Transactional
    public Publisher saveNewPublisher ( PublisherSaveRequest request ) {
        String businessNumber = request.getBusinessNumber();
        isDuplicatedBusinessNumber(businessNumber);
        return publisherRepository.save(request.toEntity());
    }

    @Transactional
    public void terminatePublisher ( Long id ) {
        findPublisherById(id).terminate();
    }

    private void isDuplicatedBusinessNumber ( String businessNumber ) {
        if ( publisherRepository.findByBusinessNumber(businessNumber).isPresent() ) {
            throw new AlreadyExistPublisherException(businessNumber);
        }
    }

    private Publisher findPublisherById ( Long id ) {
        return publisherRepository.findById(id)
            .orElseThrow(PublisherNotFoundException::new);
    }
}
