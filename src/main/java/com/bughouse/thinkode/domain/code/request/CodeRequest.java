package com.bughouse.thinkode.domain.code.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CodeRequest {

    private String code;

    private String[] input;

    private String[] output;

}
