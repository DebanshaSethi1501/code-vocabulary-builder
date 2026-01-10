package com.project.code_vocabulary_builder.repository;

import com.project.code_vocabulary_builder.model.Admin;
import com.project.code_vocabulary_builder.model.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    Admin findByName(String name);
}
