package org.infosys.controller;


import java.util.Map;

import org.infosys.exception.InvalidEntityException;
import org.infosys.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.infosys.service.UserService; 


@RestController
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        try {
            userService.signUp(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(Map.of("status", "success", "message", "User registered successfully!"));
        } catch (InvalidEntityException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("status", "error", "message", ex.getMessage()));
        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            userService.login(email, password);
            return ResponseEntity.ok("User login successful!");
        } catch (InvalidEntityException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
