package com.bughouse.thinkode.domain.code.controller;

import com.bughouse.thinkode.domain.code.request.CodeRequest;
import com.bughouse.thinkode.domain.code.response.CodeResponse;
import com.bughouse.thinkode.domain.code.service.CodeService;
import com.bughouse.thinkode.domain.code.status.CodeStatus;
import com.bughouse.thinkode.exception.CodeExeption;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
public class CodeController {

    private static final Logger log = LoggerFactory.getLogger(CodeController.class);
    private final CodeService codeService;

    @PostMapping("/check")
    public ResponseEntity<CodeResponse> checkCode(@RequestBody @Valid CodeRequest codeRequest) {
        try {
            codeService.checkCode(codeRequest);
            log.info("정답입니다.");
            CodeResponse codeResponse = new CodeResponse();
            codeResponse.setStatus(CodeStatus.SUCCESS);
            codeResponse.setMessage("정답입니다.");
            return ResponseEntity.ok(codeResponse);
        } catch (Exception e) {
            if (e instanceof CodeExeption) {
                CodeResponse codeResponse = new CodeResponse();
                codeResponse.setStatus(((CodeExeption) e).getStatus());
                codeResponse.setMessage(e.getMessage());
                return ResponseEntity.badRequest().body(codeResponse);
            }
            throw e;
        }
    }

}
