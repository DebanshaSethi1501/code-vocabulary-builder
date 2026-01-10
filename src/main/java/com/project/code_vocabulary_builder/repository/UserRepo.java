package com.project.code_vocabulary_builder.repository;

import com.project.code_vocabulary_builder.model.TechStack;
import com.project.code_vocabulary_builder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
