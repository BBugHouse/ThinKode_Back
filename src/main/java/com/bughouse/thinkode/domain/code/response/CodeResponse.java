package com.bughouse.thinkode.domain.code.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CodeResponse {

    private String status;

    private String message;

    private long time;

    private long memory;

}
