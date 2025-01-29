package org.infosys.service;

import org.infosys.exception.InvalidEntityException;
import org.infosys.model.Admin;
import org.infosys.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Admin signUp(Admin admin) throws InvalidEntityException {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new InvalidEntityException("Email is already registered.");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public Admin login(String email, String password) throws InvalidEntityException {
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isEmpty() || 
            !passwordEncoder.matches(password, adminOpt.get().getPassword())) {
            throw new InvalidEntityException("Invalid email or password.");
        }
        return adminOpt.get();
    }
}
