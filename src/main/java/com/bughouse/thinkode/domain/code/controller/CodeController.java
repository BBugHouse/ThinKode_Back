package com.bughouse.thinkode.domain.code.controller;

import com.bughouse.thinkode.domain.code.request.CodeByIDRequest;
import com.bughouse.thinkode.domain.code.request.CodeRequest;
import com.bughouse.thinkode.domain.code.response.CodeResponse;
import com.bughouse.thinkode.domain.code.service.CodeService;
import com.bughouse.thinkode.domain.code.status.CodeStatus;
import com.bughouse.thinkode.domain.question.entity.IoEntity;
import com.bughouse.thinkode.domain.question.entity.QuestionEntity;
import com.bughouse.thinkode.domain.question.service.QuestionService;
import com.bughouse.thinkode.exception.CodeExeption;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
public class CodeController {

    private static final Logger log = LoggerFactory.getLogger(CodeController.class);
    private final CodeService codeService;
    private final QuestionService questionService;

    @PostMapping("/check")
    public ResponseEntity<CodeResponse> checkCode(@RequestBody @Valid CodeRequest codeRequest) {
        try {
            CodeResponse result = codeService.checkCode(codeRequest);
            return ResponseEntity.ok(result);
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

    @PostMapping("check/{questionId}")
    public ResponseEntity<CodeResponse> checkCodeByID(@RequestBody @Valid CodeByIDRequest request, @PathVariable String questionId){
        QuestionEntity question = questionService.find(questionId);
        if(question == null){
            CodeResponse codeResponse = new CodeResponse();
            codeResponse.setStatus(CodeStatus.NOT_FOUND);
            codeResponse.setMessage("문제를 찾을 수 없습니다.");
            return ResponseEntity.badRequest().body(codeResponse);
        }
        try {
            CodeRequest codeRequest = new CodeRequest();
            codeRequest.setCode(request.getCode());
            List<String> inputs = new ArrayList<>();
            List<String> outputs = new ArrayList<>();
            for (IoEntity ios: question.getIos()) {
                inputs.add(ios.getInput());
                outputs.add(ios.getOutput());
            }
            codeRequest.setInput(inputs.toArray(new String[0]));
            codeRequest.setOutput(outputs.toArray(new String[0]));
            CodeResponse result = codeService.checkCode(codeRequest);
            return ResponseEntity.ok(result);
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
