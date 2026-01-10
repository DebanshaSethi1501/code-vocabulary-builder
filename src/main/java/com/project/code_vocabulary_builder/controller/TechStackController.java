package com.project.code_vocabulary_builder.controller;

import com.project.code_vocabulary_builder.model.TechStack;
import com.project.code_vocabulary_builder.service.TechStackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/techstack")
@CrossOrigin(origins = "*")
public class TechStackController {

    @Autowired
    private TechStackService techStackService;

    @PostMapping
    public ResponseEntity<TechStack> addTechStack(@RequestBody TechStack techStack) {
        TechStack savedTechStack = techStackService.addTechStack(techStack);
        return new ResponseEntity<>(savedTechStack, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TechStack>> getAllTechStacks() {
        List<TechStack> techStacks = techStackService.getAllTechStacks();
        return ResponseEntity.ok(techStacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechStack> getTechStackById(@PathVariable Long id) {
        Optional<TechStack> techStack = techStackService.getTechStackById(id);
        return techStack.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TechStack> updateTechStack(@PathVariable Long id, @RequestBody TechStack techStack) {
        TechStack updatedTechStack = techStackService.updateTechStack(id, techStack);
        if (updatedTechStack != null) {
            return ResponseEntity.ok(updatedTechStack);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechStack(@PathVariable Long id) {
        boolean deleted = techStackService.deleteTechStack(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
