package com.example.FlowerShop.controllers;

import com.example.FlowerShop.models.Role;
import com.example.FlowerShop.models.User;
import com.example.FlowerShop.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage(Model model){
        return "login";
    }

    @GetMapping("/register")
    public String regPage(Model model){
        return "register";
    }

    @PostMapping("/register")
    public String regPost(Model model, @RequestParam String username, @RequestParam String password){

        boolean testUser = userRepository.findByUsername(username).isPresent();
        if (!testUser) {
            User user = new User();
            user.setPassword(passwordEncoder.encode(password));
            user.setUsername(username);
            user.setRoles(Set.of(Role.ROLE_ADMIN));
            userRepository.save(user);
            return "redirect:/login";
        } else {
            return "redirect:/register";
        }
    }



}
