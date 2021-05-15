package io.bloobook.bookmanageapp.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.bloobook.bookmanageapp.common.dto.request.CategorySaveRequest;
import io.bloobook.bookmanageapp.common.exception.AlreadyExistCategoryException;
import io.bloobook.bookmanageapp.entity.category.Category;
import io.bloobook.bookmanageapp.entity.category.CategoryRepository;
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
 * @Date: 2021/05/10
 */

@ExtendWith (MockitoExtension.class)
class ApiCategoryServiceTest {

    @InjectMocks
    private ApiCategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category newCategory;
    private CategorySaveRequest saveRequest;

    @BeforeEach
    void setUp () {
        saveRequest = CategorySaveRequest.builder()
            .categoryCode("IT")
            .categoryName("IT/컴퓨터")
            .build();

        newCategory = saveRequest.toEntity();
    }


    @DisplayName ("카테고리 저장을 테스트")
    @Test
    void categorySave () {
        // given
        // when
        categoryService.saveNewCategory(saveRequest);

        // then
        verify(categoryRepository, times(1)).save(any());
    }

    @DisplayName ("이미 존재하는 카테고리에 대한 예외 테스트")
    @Test
    void ifAlreadyExistCategory () {
        // given
        // when
        when(categoryRepository.findByCategoryName("IT/컴퓨터"))
            .thenReturn(Optional.of(newCategory));

        // then
        assertThrows(
            AlreadyExistCategoryException.class, () -> categoryService.saveNewCategory(saveRequest)
        );

        verify(categoryRepository, never()).save(any());
    }
}