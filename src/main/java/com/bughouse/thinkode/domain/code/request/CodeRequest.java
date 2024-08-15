package com.bughouse.thinkode.domain.code.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CodeRequest {

    private String code;

    private String[] input;

    private String[] output;

}
