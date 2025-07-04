package com.ms.Auth_Service.controller;

import com.ms.Auth_Service.dto.AuthResponseDTO;
import com.ms.Auth_Service.dto.JwtResponseDTO;
import com.ms.Auth_Service.dto.UserSignInDTO;
import com.ms.Auth_Service.dto.UserSignUpDTO;
import com.ms.Auth_Service.model.User;
import com.ms.Auth_Service.repo.UserRepository;
import com.ms.Auth_Service.service.CustomUserDetailsService;
import com.ms.Auth_Service.service.UserService;
import com.ms.Auth_Service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/auth")
public class AuthController {

      @Autowired
      private JwtUtil jwtUtil;

      @Autowired
      private UserService userService;


      @Autowired
      private AuthenticationManager authenticationManager;


      @Autowired
      private UserRepository userRepository;


    @GetMapping("/validate")
    public ResponseEntity<AuthResponseDTO> validateToken(@RequestHeader("Authorization") String token) {
        String pureToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        String email = jwtUtil.extractEmail(pureToken); // extract email from token

        if (!jwtUtil.validateToken(pureToken, email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<String> roles = jwtUtil.extractRoles(pureToken); // extracting role from token

        Long userId = jwtUtil.extractUserId(pureToken); // extracting userId.

        return ResponseEntity.ok(new AuthResponseDTO(userId ,email, roles)); // sending response to (API-Gateway)
    }




    // sing up user
     @PostMapping("/register")
     public ResponseEntity<?> registerUser(@RequestBody UserSignUpDTO userSignUpDTO)
     {
        return userService.registerUser(userSignUpDTO);
     }

     //login User
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserSignInDTO userSignInDTO)
    {

      try{
          // Validating User here
          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(userSignInDTO.getEmail(), userSignInDTO.getPassword()));



          // 2. Fetch user from DB — this will include roles
          User user = userRepository.findUserByEmail(userSignInDTO.getEmail())
                  .orElseThrow(() -> new RuntimeException("User not found"));


//          // 2. Fetch user from DB — this will include roles
//          User user = userService.loginUser(userSignInDTO.getEmail())
//                  .orElseThrow(() -> new RuntimeException("User not found"));

          // 3. Generate JWT with roles
          String token = jwtUtil.generateToken(user);
          JwtResponseDTO jwtResponse = new JwtResponseDTO(token); // jwtResponse
          return ResponseEntity.ok(jwtResponse);
      } catch (Exception e) {
          e.printStackTrace(); //  log exception
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
      }
    }

}
