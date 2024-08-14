package com.bughouse.thinkode.domain.question.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IoEntity {

    /*@NotBlank*/
    private String input;

    /*@NotBlank*/
    private String output;

}
