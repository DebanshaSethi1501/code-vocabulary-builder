package com.project.code_vocabulary_builder.repository;

import com.project.code_vocabulary_builder.model.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechStackRepo extends JpaRepository<TechStack , Long> {
}
