package org.infosys.controller;

import org.infosys.exception.InvalidEntityException;
import org.infosys.model.Admin;
import org.infosys.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Admin admin) {
        try {
            adminService.signUp(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully!");
        } catch (InvalidEntityException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            adminService.login(email, password);
            return ResponseEntity.ok("Admin login successful!");
        } catch (InvalidEntityException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
