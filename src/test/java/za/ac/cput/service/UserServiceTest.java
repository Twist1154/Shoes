package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import za.ac.cput.Application;
import za.ac.cput.domain.User;
import za.ac.cput.enums.Role;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = AFTER_CLASS)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userService;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
        role = Role.USER;


        // Create user only if it doesn't exist
        Optional<User> existingUser = userService.findByEmail("rethabile1154@gmail.com");
        if (existingUser.isEmpty()) {
            user = new User.Builder()
                    .setFirstName("Rethabile")
                    .setLastName("Ntsekhe")
                    .setEmail("rethabile1154@gmail.com")
                    .setPassword(passwordEncoder.encode("password"))
                    .setRole(role)
                    .setBirthDate(LocalDate.of(1990, 1, 1))
                    .setPhoneNumber("1234567890")
                    .build();

            userService.create(user); // Create user in the repository
        } else {
            user = existingUser.get(); // Use existing user
        }
    }

    @Test
    @Order(1)
    void testCreateUser() {
        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals("Rethabile", createdUser.getFirstName());
        assertNotNull(userRepository.findByEmail("rethabile1154@gmail.com"));
    }

    @Test
    @Order(2)
    void testReadUser() {
        User foundUser = userService.read(user.getId());
        assertNotNull(foundUser);
        assertEquals("Rethabile", foundUser.getFirstName());
    }

    @Test
    @Order(3)
    void testUpdateUser() {
        user = new User.Builder()
                .copy(user)
                .setLastName("UpdatedLastName") // Changed for clarity
                .build();
        User updatedUser = userService.update(user);
        assertNotNull(updatedUser);
        assertEquals("UpdatedLastName", updatedUser.getLastName());
    }

    @Test
    void testDeleteUser() {
        userService.delete(user.getId());
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    @Order(4)
    void testFindAllUsers() {
        List<User> users = userService.findAll();
        System.out.println("All Users :" + '\n' + users + '\n');
        assertTrue(!users.isEmpty()); // Changed to check that users exist
    }

    @Test
    @Order(5)
    void testLoadUserByUsername() {
        UserDetails userDetails = userService.loadUserByUsername("rethabile1154@gmail.com");
        System.out.println("Found By Username: " + userDetails + '\n');
        assertNotNull(userDetails);
        assertEquals("rethabile1154@gmail.com", userDetails.getUsername());
    }

    @Test
    @Order(6)
    void testLoadUserByUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("unknown@example.com"));
    }

    @Test
    @Order(7)
    void testFindByEmail() {
        Optional<User> foundUser = userService.findByEmail("rethabile1154@gmail.com");
        System.out.println("Found By Email: " + '\n' + foundUser);
        assertTrue(foundUser.isPresent());
        assertEquals("rethabile1154@gmail.com", foundUser.get().getEmail());
    }

    @Test
    @Order(8)
    void testFindByFirstName() {
        List<User> users = userService.findByFirstName("Rethabile");
        System.out.println("Found By First Name: " + users);
        assertFalse(users.isEmpty());
        assertEquals("Rethabile", users.get(0).getFirstName()); // Adjusted index to 0
    }

    @Test
    @Order(9)
    void testFindByLastName() {
        List<User> users = userService.findByLastName("Ntsekhe");
        System.out.println("Found By Last Name: " + users);
        assertFalse(users.isEmpty());
    }

    @Test
    @Order(10)
    void testFindByBirthDate() {
        List<User> users = userService.findByBirthDate(LocalDate.of(1990, 1, 1));
        System.out.println("Found By Birthday: " + users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    @Order(11)
    void testFindByPhoneNumber() {
        List<User> users = userService.findByPhoneNumber("1234567890");
        System.out.println("Found By Phone Number: " + users);
        assertFalse(users.isEmpty());
        assertEquals("1234567890", users.get(0).getPhoneNumber()); // Adjusted index to 0
    }

    @Test
    @Order(12)
    void testFindByRole() {
        List<User> users = userService.findByRole("USER");
        System.out.println("Found By Roles: " + users);
        assertFalse(users.isEmpty());
     }
}
