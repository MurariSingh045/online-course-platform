package com.ms.Auth_Service.service;

import com.ms.Auth_Service.dto.UserResponseDTO;
import com.ms.Auth_Service.dto.UserSignInDTO;
import com.ms.Auth_Service.dto.UserSignUpDTO;
import com.ms.Auth_Service.model.Role;
import com.ms.Auth_Service.model.User;
import com.ms.Auth_Service.repo.RoleRepository;
import com.ms.Auth_Service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    public ResponseEntity<?> registerUser( UserSignUpDTO userSignUpDTO) {

        try {
            //if the user is already exist don't add it and return bad request
            if (userRepository.findUserByEmail(userSignUpDTO.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already registered");
            }


            // if the email is not already exists
            User newUser = new User(); // create object of User class
            newUser.setUsername(userSignUpDTO.getUsername());
            newUser.setEmail(userSignUpDTO.getEmail());

            newUser.setPassword(passwordEncoder.encode(userSignUpDTO.getPassword())); // encoding password

            // getting role from db
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            newUser.setRoles(Set.of(userRole)); // setting role

          User saved =  userRepository.save(newUser); // saving user to db

          UserResponseDTO response = new UserResponseDTO();
                response.setId(saved.getId());
                response.setName(saved.getUsername());
                response.setEmail(saved.getEmail());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }

    }
        public ResponseEntity<?>  loginUser(UserSignInDTO userSignInDTO)
        {
            // getting user from db
            Optional<User> optionalUser = userRepository.findUserByEmail(userSignInDTO.getEmail());

            // if user is present
            if (optionalUser.isPresent()) {
                User user = optionalUser.get(); // extracting user from optional user which is coming from db.

                // first we are encoding the password which is coming from request and matching with the password which is coming from db
                // coz  we have already store password in encrypted form.
                if (passwordEncoder.matches(userSignInDTO.getPassword(), user.getPassword())) {
                    // make object of UserResponse
                    UserResponseDTO response = new UserResponseDTO();
                    response.setId(user.getId());
                    response.setName(user.getUsername());
                    response.setEmail(user.getEmail());
                    Set<String> roles = user.getRoles()
                            .stream()
                            .map(Role::getName)
                            .collect(Collectors.toSet());
                    response.setRoles(roles);

                    return ResponseEntity.ok(response);
                }
            }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credential");
            }
}



