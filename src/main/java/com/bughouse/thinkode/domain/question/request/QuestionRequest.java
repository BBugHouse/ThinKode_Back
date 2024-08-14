package com.bughouse.thinkode.domain.question.request;

import com.bughouse.thinkode.domain.question.entity.IoEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String content;

    @NotNull
    @Valid
    private List<IoEntity> ios;

}
