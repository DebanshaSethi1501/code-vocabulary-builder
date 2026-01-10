package com.project.code_vocabulary_builder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tech_stack")
@Data
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tech_name;

    @Column(nullable = false)
    private String tech_description;

    @JsonIgnore
    @OneToMany(mappedBy = "techStack" ,cascade = CascadeType.ALL)
    private List<Questions> questions;

}
