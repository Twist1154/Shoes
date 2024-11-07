package za.ac.cput.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.cput.dto.AuthenticationResponse;
import za.ac.cput.domain.User;
import za.ac.cput.dto.UserAuth;
import za.ac.cput.repository.UserRepository;


@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs a new {@code AuthenticationService} with the provided dependencies.
     *
     * @param repository             the repository to manage user data
     * @param passwordEncoder        the password encoder to securely encode passwords
     * @param jwtService             the service to handle JWT token operations
     * @param authenticationManager  the manager responsible for authenticating user credentials
     */
    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user by encoding the password, saving the user in the repository,
     * and generating a JWT token for the user.
     *
     * @param request the user data provided during registration
     * @return an {@link AuthenticationResponse} containing the JWT token
     */
    public AuthenticationResponse register(User request) {
        User user = new User.Builder().copy(request)
                .setId(request.getId())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setUsername(request.getUsername())
                .setEmail(request.getEmail())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRole(request.getRole())
                .setBirthDate(request.getBirthDate())
                .setPhoneNumber(request.getPhoneNumber())
                .build();
        System.out.println("user created");

        // Save the user to the repository
        user = repository.save(user);

        // Generate JWT token for the registered user
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    /**
     * Authenticates a user by verifying their username and password, and generates a JWT token if valid.
     *
     * @param request the user credentials provided for authentication
     * @return an {@link AuthenticationResponse} containing the JWT token
     */
    public AuthenticationResponse authenticate(UserAuth request) {
        // private static final String logger = Logger.getLogger(AuthenticationService.class.getName());

        try {
            System.out.println("Attempting to authenticate user: " + request.getEmail());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtService.generateToken(user);
            System.out.println("Authentication successful for user: " + request.getEmail());

            return new AuthenticationResponse(token);
        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed for user: " + request.getEmail() + ". Reason: Bad credentials");
            throw new BadCredentialsException("Incorrect username or password");
        } catch (UsernameNotFoundException e) {
            System.out.println("Authentication failed for user: " + request.getEmail() + ". Reason: User not found");
            throw new UsernameNotFoundException("User not found");
        } catch (Exception e) {
            System.out.println("Unexpected error during authentication for user: " + request.getEmail() + ". Error: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during authentication");
        }
    }

}
