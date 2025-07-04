package com.ms.Auth_Service.config;

import com.ms.Auth_Service.model.Role;
import com.ms.Auth_Service.repo.RoleRepository;
import com.ms.Auth_Service.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@Configuration
public class DataInitializer {

    //@Bean public CommandLineRunner: This defines a special bean that runs after the application context is fully initialized, but before the app starts serving requests.
    //CommandLineRunner bean to insert default roles (e.g., ROLE_USER, ROLE_ADMIN) into the database when the application starts — if they don’t already exist.

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role("ROLE_USER"));
            }
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
            System.out.println("Default roles inserted"); // this message ensure the role is inserted successfully.
        };
    }
}
// we can also anyone as ADMIN or USER

//@Configuration
//public class DataInitializer {
//
//    @Bean
//    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//
//            // Insert default roles if not present
//            Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
//            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
//
//            // Insert default admin user if not present
//            if (userRepository.findByEmail("admin@genie.com").isEmpty()) {
//                User admin = new User();
//                admin.setName("Default Admin");
//                admin.setEmail("admin@genie.com");
//                admin.setPassword(passwordEncoder.encode("admin123")); // Encrypt the password
//                admin.setRoles(Set.of(adminRole)); // Assign ROLE_ADMIN only
//
//                userRepository.save(admin);
//                System.out.println("✅ Default admin user inserted");
//            }
//
//            System.out.println("✅ Default roles ensured"); // this is role is inserted succesfully
//        };
//    }
//}
