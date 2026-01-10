package com.project.code_vocabulary_builder.service;

import com.project.code_vocabulary_builder.model.Admin;
import com.project.code_vocabulary_builder.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    public Admin registerAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

//    public boolean loginAdmin(String name, String password) {
//        Optional<Admin> admin = adminRepo.findByName(name);
//        return admin.isPresent() && admin.get().getPassword().equals(password);
//    }

//    public Optional<Admin> getAdminByName(String name) {
//        return adminRepo.findByName(name);
//    }
}