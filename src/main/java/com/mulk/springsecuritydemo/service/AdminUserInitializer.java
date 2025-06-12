package com.mulk.springsecuritydemo.service;

import com.mulk.springsecuritydemo.model.User;
import com.mulk.springsecuritydemo.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdminUser(UserDetailsRepository repo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("Admin1")); //Securely store password
                admin.setRole("ROLE_ADMIN");

                repo.save(admin);
                System.out.println("Default Admin Created");
            }
        };
    }
}
