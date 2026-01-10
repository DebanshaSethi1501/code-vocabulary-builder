package com.project.code_vocabulary_builder.controller;

import com.project.code_vocabulary_builder.model.Questions;
import com.project.code_vocabulary_builder.model.UserProgress;
import com.project.code_vocabulary_builder.service.GameService;
import com.project.code_vocabulary_builder.service.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/game")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserProgressService userProgressService;

    // Get random question
    @GetMapping("/question")
    public ResponseEntity<Questions> getRandomQuestion() {
        Questions question = gameService.getRandomQuestion();
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(question);
    }

    // Get question by difficulty
    @GetMapping("/question/{difficulty}")
    public ResponseEntity<Questions> getQuestionByDifficulty(@PathVariable String difficulty) {
        Questions question = gameService.getRandomQuestionByDifficulty(difficulty);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(question);
    }

    // Submit answer and get result with score update
    @PostMapping("/answer")
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<String, Object> request) {
        Long questionId = Long.parseLong(request.get("questionId").toString());
        String userAnswer = request.get("answer").toString();
        Long userId = Long.parseLong(request.get("userId").toString());

        Map<String, Object> result = gameService.checkAnswerAndUpdateScore(questionId, userAnswer, userId);

        // Update user progress
        boolean isCorrect = (boolean) result.get("correct");
        UserProgress progress = userProgressService.updateProgress(userId, isCorrect);

        if (progress != null) {
            result.put("total_attempts", progress.getTotalAttempts());
            result.put("correct_answers", progress.getCorrectAnswers());
            result.put("accuracy", progress.getAccuracy());
            result.put("level", progress.getCurrentLevel());
        }

        return ResponseEntity.ok(result);
    }

    // Get user progress
    @GetMapping("/progress/{userId}")
    public ResponseEntity<UserProgress> getUserProgress(@PathVariable Long userId) {
        UserProgress progress = userProgressService.getOrCreateProgress(userId);
        if (progress == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(progress);
    }

    // Reset progress
    @PutMapping("/progress/{userId}/reset")
    public ResponseEntity<UserProgress> resetProgress(@PathVariable Long userId) {
        UserProgress progress = userProgressService.resetProgress(userId);
        if (progress == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(progress);
    }
}
