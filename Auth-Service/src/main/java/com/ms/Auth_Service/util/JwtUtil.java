package com.ms.Auth_Service.util;


import com.ms.Auth_Service.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import com.ms.Auth_Service.model.Role;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final String SECRET = "murari-secure-key-for-jwt-12345678murari";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // generating token
    public  String generateToken(User user)
    {

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());


        return Jwts.builder()
                .setId(String.valueOf(user.getId())) // set id
                .setSubject(user.getEmail()) // setting user email
                .claim("roles", roles)  // setting the role
                .setIssuedAt(new Date()) //starting time of token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // expiry date
                .signWith(SECRET_KEY) // encrypt it using secret key
                .compact();
    }

    // extract email from token
    public String extractEmail(String token)
    {
      return Jwts.parserBuilder()
              .setSigningKey(SECRET_KEY) // use same secret key
              .build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject(); // return email
    }


    // Get roles from token
    public List<String> extractRoles(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                //Casts directly to List.class, which works for lists of strings.
                .get("roles", List.class);
    }

    // Check if token has specific role
    public boolean hasRole(String token, String roleToCheck) {
        List<String> roles = extractRoles(token);
        return roles.contains(roleToCheck);
    }

   public boolean validateToken(String token , String email)
   {
       return extractEmail(token).equals(email) && !isTokenExpired(token);
   }

   // extract the userId

    public Long extractUserId (String token)
    {
      return Long.parseLong(extractAllClaims(token).getId()); // extract id from jti
    }

    // extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    // function for token expiration time
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

}
