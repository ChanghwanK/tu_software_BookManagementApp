package io.bloobook.bookmanageapp.service;

import io.bloobook.bookmanageapp.common.dto.request.CategorySaveRequest;
import io.bloobook.bookmanageapp.common.exception.AlreadyExistCategoryException;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/09
 */

@RequiredArgsConstructor
@Service
public class ApiCategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category saveNewCategory ( CategorySaveRequest request ) {
        String categoryName = request.getCategoryName();
        isDuplicatedName(categoryName);
        return categoryRepository.save(request.toEntity());
    }

    private void isDuplicatedName ( String categoryName ) {
        if ( categoryRepository.findByCategoryName(categoryName).isPresent() ) {
            throw new AlreadyExistCategoryException(categoryName);
        }
    }
}
