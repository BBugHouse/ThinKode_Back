package com.bughouse.thinkode.domain.question;

import com.bughouse.thinkode.domain.question.entity.QuestionEntity;
import com.bughouse.thinkode.domain.question.request.QuestionRequest;
import com.bughouse.thinkode.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("")
    public ResponseEntity<List<QuestionEntity>> getQuestions() {
        List<QuestionEntity> questions = questionService.findAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionEntity> getQuestion(@PathVariable String questionId) {
        QuestionEntity question = questionService.find(questionId);
        if (question == null) {
            throw new NotFoundException("해당하는 ID의 문제를 찾지 못했습니다.");
        }
        return ResponseEntity.ok(question);
    }

    @PostMapping("")
    public ResponseEntity<String> createQuestion(@Valid @RequestBody QuestionRequest question) {
        try {
            questionService.create(question);
            return ResponseEntity.ok("문제가 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<String> updateQuestion(@PathVariable String questionId, @Valid @RequestBody QuestionRequest question) {
        try {
            questionService.update(questionId, question);
            return ResponseEntity.ok("문제가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable String questionId) {
        try {
            questionService.delete(questionId);
            return ResponseEntity.ok("문제가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
