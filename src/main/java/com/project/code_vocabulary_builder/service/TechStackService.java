package com.project.code_vocabulary_builder.service;

import com.project.code_vocabulary_builder.model.TechStack;
import com.project.code_vocabulary_builder.repository.TechStackRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechStackService {

    @Autowired
    private TechStackRepo techStackRepo;

    public TechStack addTechStack(TechStack techStack) {
        return techStackRepo.save(techStack);
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<TechStack> getAllTechStacks() {
        return techStackRepo.findAll();
    }

    public Optional<TechStack> getTechStackById(Long id) {
        return techStackRepo.findById(id);
    }

    public TechStack updateTechStack(Long id, TechStack techStack) {
        if (techStackRepo.existsById(id)) {
            techStack.setId(id);
            return techStackRepo.save(techStack);
        }
        return null;
    }

    public boolean deleteTechStack(Long id) {
        if (techStackRepo.existsById(id)) {
            techStackRepo.deleteById(id);

            long count = techStackRepo.count();
            if (count == 0) {
                entityManager.createNativeQuery("ALTER TABLE tech_stack AUTO_INCREMENT = 1").executeUpdate();
            }
            return true;
        }
        return false;
    }
}