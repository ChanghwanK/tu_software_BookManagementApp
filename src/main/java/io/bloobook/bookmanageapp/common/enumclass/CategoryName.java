package io.bloobook.bookmanageapp.common.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @CreateBy: Bloo
 * @Date: 2021/05/06
 */
@AllArgsConstructor
@Getter
public enum CategoryName {
    NOVEL (0L , "소설"),
    HUMANITIES (1L, "인문"),
    LITERATURE (2L, "문학"),
    POLITICS (3L, "정치"),
    ART (4L, "예술"),
    SCIENCE (5L, "과학"),
    IT (6L, "컴퓨터 IT"),
    ERROR (999L, "예외")
    ;

    private Long id;
    private String description;

}