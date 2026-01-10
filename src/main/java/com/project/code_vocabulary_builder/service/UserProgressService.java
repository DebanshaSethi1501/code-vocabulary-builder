package com.project.code_vocabulary_builder.service;

import com.project.code_vocabulary_builder.model.User;
import com.project.code_vocabulary_builder.model.UserProgress;
import com.project.code_vocabulary_builder.repository.UserProgressRepo;
import com.project.code_vocabulary_builder.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserProgressService {

    @Autowired
    private UserProgressRepo userProgressRepo;

    @Autowired
    private UserRepo userRepo;

    // Get or create progress for user
    public UserProgress getOrCreateProgress(Long userId) {
        Optional<UserProgress> existing = userProgressRepo.findByUserId(userId);
        if (existing.isPresent()) {
            return existing.get();
        }

        // Create new progress
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        UserProgress progress = new UserProgress();
        progress.setUser(user);
        progress.setTotalAttempts(0L);
        progress.setCorrectAnswers(0L);
        progress.setWrongAnswers(0L);
        progress.setAccuracy(0.0);
        progress.setCurrentLevel("Beginner");
        progress.setLastPlayed(LocalDateTime.now());
        progress.setStreakDays(0L);

        return userProgressRepo.save(progress);
    }

    // Update progress after answering
    public UserProgress updateProgress(Long userId, boolean isCorrect) {
        UserProgress progress = getOrCreateProgress(userId);
        if (progress == null) {
            return null;
        }

        progress.setTotalAttempts(progress.getTotalAttempts() + 1);

        if (isCorrect) {
            progress.setCorrectAnswers(progress.getCorrectAnswers() + 1);
        } else {
            progress.setWrongAnswers(progress.getWrongAnswers() + 1);
        }

        // Calculate accuracy
        if (progress.getTotalAttempts() > 0) {
            double accuracy = (progress.getCorrectAnswers() * 100.0) / progress.getTotalAttempts();
            progress.setAccuracy(Math.round(accuracy * 100.0) / 100.0);
        }

        // Update level based on correct answers
        progress.setCurrentLevel(calculateLevel(progress.getCorrectAnswers()));

        progress.setLastPlayed(LocalDateTime.now());

        return userProgressRepo.save(progress);
    }

    private String calculateLevel(Long correctAnswers) {
        if (correctAnswers < 10) {
            return "Beginner";
        } else if (correctAnswers < 30) {
            return "Intermediate";
        } else {
            return "Advanced";
        }
    }

    // Reset progress
    public UserProgress resetProgress(Long userId) {
        Optional<UserProgress> existing = userProgressRepo.findByUserId(userId);
        if (existing.isPresent()) {
            UserProgress progress = existing.get();
            progress.setTotalAttempts(0L);
            progress.setCorrectAnswers(0L);
            progress.setWrongAnswers(0L);
            progress.setAccuracy(0.0);
            progress.setCurrentLevel("Beginner");
            return userProgressRepo.save(progress);
        }
        return null;
    }
}