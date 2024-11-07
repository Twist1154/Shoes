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
import za.ac.cput.dto.UserPasswordDTO;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collections;
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
        return userRepository.save(user);
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


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Attempting to load user: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("User not found: " + email);
                    return new UsernameNotFoundException("User not Found");
                });
        System.out.println("User found: " + user.getEmail());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
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
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updatePassword(UserPasswordDTO userPasswordDTO) {
        User user = userRepository.findByEmail(userPasswordDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userPasswordDTO.getEmail()));

        user.setPassword(passwordEncoder.encode(userPasswordDTO.getPassword()));
        return userRepository.save(user);
    }

}
