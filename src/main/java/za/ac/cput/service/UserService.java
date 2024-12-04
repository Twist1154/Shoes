package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.User;
import za.ac.cput.enums.Role;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.util.Helper;
//import za.ac.cput.util.JwtUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling user-related operations.
// * Implements {@link //UserDetailsService} for user authentication.
 * Implements {@link IUser} for user CRUD operations.
 * <p>
 * Author: Rethabile Ntsekhe
 * Date: 24-Aug-24
 * </p>
 */
@Service
@Transactional
public class UserService implements /*UserDetailsService,*/ IUser {

    private final UserRepository userRepository;
   /* private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
*/
    /**
     * Constructs a UserService with the specified UserRepository, PasswordEncoder, and UserMapper.
     *
     * @param userRepository   the UserRepository for interacting with the database
     * @param //passwordEncoder  the PasswordEncoder for encoding passwords
     */
    @Autowired
    public UserService(UserRepository userRepository/*, PasswordEncoder passwordEncoder, JwtUtil jwtUtil*/) {
        this.userRepository = userRepository;
        /*this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;*/
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
                    .setPhoneNumber(user.getPhoneNumber())
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword())
                    .setRole(user.getRole())
                    .build();
            return userRepository.save(updatedUser);
        } else {
            return null;
        }
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);


        boolean exists = userRepository.existsById(id);

        return !exists;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Loads user-specific data by username.
     *
     * @param //username the username of the user
     * @return the UserDetails object containing user data
     * @throws //UsernameNotFoundException if no user is found with the given username
     */
   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));


        List<SimpleGrantedAuthority> authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority( role.name()))
                .collect(Collectors.toList());

        // Return user details for authentication
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }*/


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
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    /*public String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }

    public boolean validateToken(String token, User user) {
        return jwtUtil.isValid(token, user);
    }

    public User updatePassword(Long id,User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            User updatedUser = new User.Builder()
                    .copy(existingUser)
                    .setPassword(passwordEncoder.encode(user.getPassword()))
                    .build();
            return userRepository.save(updatedUser);
        } else {
            return null;
        }
    }*/
}