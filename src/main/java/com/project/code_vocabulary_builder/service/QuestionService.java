package com.project.code_vocabulary_builder.service;

import com.project.code_vocabulary_builder.model.Questions;
import com.project.code_vocabulary_builder.repository.QuestionRepo;
import com.project.code_vocabulary_builder.repository.TechStackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private TechStackRepo techStackRepo;

    public Questions addQuestion(Questions question) {
        return questionRepo.save(question);
    }

    public List<Questions> getAllQuestions() {
        return questionRepo.findAll();
    }

    public Optional<Questions> getQuestionById(Long id) {
        return questionRepo.findById(id);
    }

    public Questions updateQuestion(Long id, Questions question) {
        if (questionRepo.existsById(id)) {
            question.setId(id);
            return questionRepo.save(question);
        }
        return null;
    }

    public boolean deleteQuestion(Long id) {
        if (questionRepo.existsById(id)) {
            questionRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean checkAnswer(Long questionId, String userAnswer) {
        Optional<Questions> question = questionRepo.findById(questionId);
        return question.isPresent() && question.get().getAnswer().equalsIgnoreCase(userAnswer);
    }
}
