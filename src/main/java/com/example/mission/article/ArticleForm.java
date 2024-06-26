package com.example.mission.article;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {

    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
}
