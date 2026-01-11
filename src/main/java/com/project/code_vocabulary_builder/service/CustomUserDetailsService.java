package com.project.code_vocabulary_builder.service;

import com.project.code_vocabulary_builder.UserDetails.CustomUserDetails;
import com.project.code_vocabulary_builder.model.Admin;
import com.project.code_vocabulary_builder.repository.AdminRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private AdminRepo adminRepo;

    public CustomUserDetailsService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByName(username);
        return new CustomUserDetails (admin);
    }
}
