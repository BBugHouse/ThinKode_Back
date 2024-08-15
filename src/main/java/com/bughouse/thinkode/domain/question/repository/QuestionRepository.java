package com.bughouse.thinkode.domain.question.repository;

import com.bughouse.thinkode.domain.question.entity.QuestionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<QuestionEntity, String> {

}
