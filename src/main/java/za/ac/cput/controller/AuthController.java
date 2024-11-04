package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import za.ac.cput.domain.User;
import za.ac.cput.dto.Auth;
import za.ac.cput.dto.UserAuth;
import za.ac.cput.util.JwtUtil;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Auth> loginUser(@RequestBody UserAuth userAuth) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword()));

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(userAuth.getUsername());

            // Generate JWT Token
            final String jwt = jwtUtil.generateToken((User) userDetails);

            // Return token and status
            Auth  response = Auth.builder()
                    .token(jwt)
                    .status("Login successful")
                    .build();

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Auth.builder().status("Invalid email or password").build());
        }
    }
}