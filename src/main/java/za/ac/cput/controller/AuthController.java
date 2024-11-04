import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import za.ac.cput.dto.AuthenticationResponse;
import za.ac.cput.domain.User;
import za.ac.cput.service.AuthenticationService;
import za.ac.cput.service.UserService;
import za.ac.cput.util.JwtUtil;

@RestController
public class AuthController {


    private final AuthenticationService authService;
    private final UserService userService;
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationService authService, UserService userService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}