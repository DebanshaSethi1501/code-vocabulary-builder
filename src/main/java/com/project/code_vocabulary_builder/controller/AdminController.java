package com.project.code_vocabulary_builder.controller;

import com.project.code_vocabulary_builder.model.Admin;
import com.project.code_vocabulary_builder.repository.AdminRepo;
import com.project.code_vocabulary_builder.service.AdminService;
import com.project.code_vocabulary_builder.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private AdminRepo adminRepo;
    private AdminService adminService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public AdminController(AdminRepo adminRepo,
                           AdminService adminService,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.adminService = adminService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerAdmin(@RequestBody Admin admin) {
        Map<String, String> h = new HashMap<>();
        Admin exist_admin =adminRepo.findByName(admin.getName());
        if (exist_admin!=null){
            h.put("Error" , "User ALredy exists");
            return ResponseEntity.status(400).body(h);
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin savedAdmin = adminService.registerAdmin(admin);
        h.put("Status", "Created");
        return ResponseEntity.status(200).body(h);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginAdmin(@RequestBody Admin admin) {
        Map<String, String> h = new HashMap<>();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getName() , admin.getPassword()));
        String token = jwtService.generateToken(admin.getName());
        h.put("token",token);
        h.put("status","connected");
        return ResponseEntity.status(200).body(h);
    }
}