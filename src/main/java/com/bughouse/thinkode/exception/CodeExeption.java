package com.bughouse.thinkode.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CodeExeption extends RuntimeException {

    private final String status;

    public CodeExeption(String status, String message) {
        super(message);
        this.status = status;
    }
}
