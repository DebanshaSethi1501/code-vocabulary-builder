package com.project.code_vocabulary_builder.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_progress")
@Data
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long totalAttempts = 0L;

    @Column(nullable = false)
    private Long correctAnswers = 0L;

    @Column(nullable = false)
    private Long wrongAnswers = 0L;

    @Column(nullable = false)
    private Double accuracy = 0.0; // percentage

    @Column
    private String currentLevel = "Beginner"; // Beginner, Intermediate, Advanced

    @Column
    private LocalDateTime lastPlayed;

    @Column
    private Long streakDays = 0L;
}