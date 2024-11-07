package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import za.ac.cput.dto.AuthenticationResponse;
import za.ac.cput.domain.User;
import za.ac.cput.dto.UserAuth;
import za.ac.cput.service.AuthenticationService;
import za.ac.cput.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthenticationService authService;



    public AuthController(AuthenticationService authService, UserService userService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserAuth request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}