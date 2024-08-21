package com.bughouse.thinkode.domain.question.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "question")
public class QuestionEntity {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String content;

    @NotNull
    @Valid
    @JsonSubTypes.Type(name = "io", value = IoEntity.class)
    private List<IoEntity> ios;

    @NotNull
    @Valid
    private List<IoEntity> test_ios;

}
