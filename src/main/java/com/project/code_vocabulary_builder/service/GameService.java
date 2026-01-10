package com.project.code_vocabulary_builder.service;

import com.project.code_vocabulary_builder.model.Questions;
import com.project.code_vocabulary_builder.model.User;
import com.project.code_vocabulary_builder.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class GameService {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private UserService userService;

    // Get random question
    public Questions getRandomQuestion() {
        List<Questions> allQuestions = questionRepo.findAll();
        if (allQuestions.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return allQuestions.get(random.nextInt(allQuestions.size()));
    }

    // Get random question by difficulty
    public Questions getRandomQuestionByDifficulty(String difficulty) {
        List<Questions> allQuestions = questionRepo.findAll();
        List<Questions> filteredQuestions = allQuestions.stream()
                .filter(q -> q.getDifficulty_level().equalsIgnoreCase(difficulty))
                .toList();

        if (filteredQuestions.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return filteredQuestions.get(random.nextInt(filteredQuestions.size()));
    }

    // Check answer and update score
    public Map<String, Object> checkAnswerAndUpdateScore(Long questionId, String userAnswer, Long userId) {
        Map<String, Object> result = new HashMap<>();

        Questions question = questionRepo.findById(questionId).orElse(null);
        if (question == null) {
            result.put("error", "Question not found");
            return result;
        }

        boolean isCorrect = question.getAnswer().equalsIgnoreCase(userAnswer);
        result.put("correct", isCorrect);

        if (isCorrect) {
            // Award points based on difficulty
            Long points = getPointsByDifficulty(question.getDifficulty_level());
            User updatedUser = userService.updateScore(userId, points);

            result.put("message", "Correct answer! +" + points + " points");
            result.put("points_earned", points);
            result.put("new_score", updatedUser != null ? updatedUser.getScore() : 0);
        } else {
            result.put("message", "Wrong answer!");
            result.put("correct_answer", question.getAnswer());
            result.put("points_earned", 0);
        }

        return result;
    }

    // Points based on difficulty
    private Long getPointsByDifficulty(String difficulty) {
        return switch (difficulty.toLowerCase()) {
            case "easy" -> 5L;
            case "medium" -> 10L;
            case "hard" -> 15L;
            default -> 5L;
        };
    }
}