package com.project.code_vocabulary_builder.service;

import com.project.code_vocabulary_builder.model.User;
import com.project.code_vocabulary_builder.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User createUser(User user) {
        if (user.getScore() == null) {
            user.setScore(0L);
        }
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public User updateScore(Long userId, Long points) {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setScore(user.getScore() + points);
            return userRepo.save(user);
        }
        return null;
    }

    public User resetScore(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setScore(0L);
            return userRepo.save(user);
        }
        return null;
    }

    public List<User> getLeaderboard() {
        return userRepo.findAll().stream()
                .sorted((u1, u2) -> Long.compare(u2.getScore(), u1.getScore()))
                .toList();
    }
}