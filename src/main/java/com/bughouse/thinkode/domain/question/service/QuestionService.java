package com.bughouse.thinkode.domain.question.service;

import com.bughouse.thinkode.domain.question.entity.QuestionEntity;
import com.bughouse.thinkode.domain.question.repository.QuestionRepository;
import com.bughouse.thinkode.domain.question.request.QuestionRequest;
import com.bughouse.thinkode.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionEntity> findAll() {
        return questionRepository.findAll();
    }

    public QuestionEntity find(String questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    public void create(QuestionRequest question) {
        QuestionEntity newQuestion = new QuestionEntity();
        newQuestion.setName(question.getName());
        newQuestion.setContent(question.getContent());
        newQuestion.setIos(question.getIos());
        newQuestion.setTest_ios(question.getTest_ios());
        questionRepository.save(newQuestion);
    }

    public void update(String questionId, QuestionRequest question) {
        QuestionEntity updateQuestion = questionRepository.findById(questionId).orElse(null);
        if (updateQuestion == null) {
            throw new NotFoundException("해당하는 ID의 문제를 찾지 못했습니다.");
        }
        updateQuestion.setName(question.getName());
        updateQuestion.setContent(question.getContent());
        updateQuestion.setIos(question.getIos());
        updateQuestion.setTest_ios(question.getTest_ios());
        questionRepository.save(updateQuestion);
    }

    public void delete(String questionId) {
        QuestionEntity deleteQuestion = questionRepository.findById(questionId).orElse(null);
        if (deleteQuestion == null) {
            throw new NotFoundException("해당하는 ID의 문제를 찾지 못했습니다.");
        }
        questionRepository.delete(deleteQuestion);
    }

}
