package za.ac.cput.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.User;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling user-related operations.
 * Implements {@link UserDetailsService} for user authentication.
 * Implements {@link IUser} for user CRUD operations.
 * <p>
 * Author: Rethabile Ntsekhe
 * Date: 24-Aug-24
 * </p>
 */
@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService, IUser {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a UserService with the specified UserRepository and PasswordEncoder.
     *
     * @param userRepository  the UserRepository for interacting with the database
     * @param passwordEncoder the PasswordEncoder for encoding passwords
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isEmpty()) {
            User registeredUser = new User.Builder()
                    .copy(user) // Initialize using new user data
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setBirthDate(user.getBirthDate())
                    .setPhoneNumber(passwordEncoder.encode(user.getPhoneNumber()))
                    .setEmail(user.getEmail())
                    .setPassword(passwordEncoder.encode(user.getPassword()))
                    .setRole(user.getRole())
                    .build();
            return userRepository.save(registeredUser);
        }
        log.info("User with ID {} already exists", user.getId());
        return existingUser.get();
    }

    @Override
    public User read(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            User updatedUser = new User.Builder()
                    .copy(existingUser)
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setBirthDate(user.getBirthDate())
                    .setPhoneNumber(passwordEncoder.encode(user.getPhoneNumber()))
                    .setEmail(user.getEmail())
                    .setPassword(passwordEncoder.encode(user.getPassword()))
                    .setRole(user.getRole())
                    .build();
            return userRepository.save(updatedUser);
        } else {
            return null;
        }
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Loads user-specific data by username.
     *
     * @param username the username of the user
     * @return the UserDetails object containing user data
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        List<SimpleGrantedAuthority> authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());

        // Return user details for authentication
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Override
    public List<User> findByBirthDate(LocalDate birthDate) {
        return userRepository.findByBirthDate(birthDate);
    }

    @Override
    public List<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
