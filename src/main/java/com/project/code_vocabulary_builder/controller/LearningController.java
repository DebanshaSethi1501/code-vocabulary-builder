package com.project.code_vocabulary_builder.controller;

import com.project.code_vocabulary_builder.model.Questions;
import com.project.code_vocabulary_builder.model.TechStack;
import com.project.code_vocabulary_builder.service.QuestionService;
import com.project.code_vocabulary_builder.service.TechStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth/learn")
@CrossOrigin(origins = "*")
public class LearningController {

    @Autowired
    private TechStackService techStackService;

    @Autowired
    private QuestionService questionService;

    // Get all tech stacks (for learning)
    @GetMapping("/topics")
    public ResponseEntity<List<TechStack>> getAllTopics() {
        List<TechStack> topics = techStackService.getAllTechStacks();
        return ResponseEntity.ok(topics);
    }

    // Get topic details with questions
    @GetMapping("/topics/{id}")
    public ResponseEntity<Map<String, Object>> getTopicDetails(@PathVariable Long id) {
        TechStack techStack = techStackService.getTechStackById(id).orElse(null);
        if (techStack == null) {
            return ResponseEntity.notFound().build();
        }

        List<Questions> questions = questionService.getAllQuestions().stream()
                .filter(q -> q.getTechStack().getId().equals(id))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("topic", techStack);
        response.put("questions", questions);
        response.put("total_questions", questions.size());

        return ResponseEntity.ok(response);
    }

    // Get all terms (questions) for learning
    @GetMapping("/terms")
    public ResponseEntity<List<Questions>> getAllTerms() {
        List<Questions> terms = questionService.getAllQuestions();
        return ResponseEntity.ok(terms);
    }

    // Get term by ID with full details
    @GetMapping("/terms/{id}")
    public ResponseEntity<Questions> getTermById(@PathVariable Long id) {
        Questions term = questionService.getQuestionById(id).orElse(null);
        if (term == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(term);
    }
}