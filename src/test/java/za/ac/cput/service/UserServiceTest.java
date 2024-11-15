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

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        Optional<User> existingUser = userService.findByEmail("rethabile1154@gmail.co.za");
        if (existingUser.isEmpty()) {
            user = new User.Builder()
                    .setId(null)
                    .setAvatar("avatar.jpg")
                    .setFirstName("Ntsekhe")
                    .setLastName("Rethabile")
                    .setEmail("rethabile1154@gmail.co.za")
                    .setUsername("USER12")
                    .setPassword(passwordEncoder.encode("password"))
                    .setRole(Set.of(Role.USER, Role.ADMIN))
                    .setBirthDate(LocalDate.of(1991, 1, 1))
                    .setPhoneNumber("123456789")
                    .build();
            userService.create(user);
        } else {
            user = existingUser.get();
        }
    }

    @AfterEach
    void tearDown() {

        if (userRepository.findById(user.getId()).isPresent() && user.getId() >2) {
            userService.delete(user.getId());
        }
    }

    @Test
    @Order(1)
    void testCreateUser() {
        User createdUser = userService.create(user);
        System.out.println("Created User: " + createdUser);
        assertNotNull(createdUser);
        assertEquals("Ntsekhe", createdUser.getFirstName());
        assertTrue(userRepository.findByEmail("rethabile1154@gmail.co.za").isPresent());
    }

    @Test
    @Order(2)
    void testReadUser() {
        User foundUser = userService.read(2L);
        System.out.println("Found User: " + foundUser);
        assertNotNull(foundUser);
        assertEquals("Ntsekhe", foundUser.getFirstName());
    }

    @Test
    @Order(3)
    void testUpdateUser() {
        user = new User.Builder()
                .copy(user)
                .setLastName("UpdatedLastName")
                .build();
        User updatedUser = userService.update(user);
        System.out.println("Updated User: " + updatedUser);
        assertNotNull(updatedUser);
        assertEquals("UpdatedLastName", updatedUser.getLastName());
    }

    @Test
    @Order(4)
    void testDeleteUser() {
        User deletedUser = userService.create(user);
        boolean delete = userService.delete(deletedUser.getId());
        assertFalse(userRepository.findById(deletedUser.getId()).isPresent());
        assertTrue(delete);
    }

    @Test
    @Order(5)
    void testFindAllUsers() {
        List<User> users = userService.findAll();
        System.out.println("All Users:\n" + users);
        assertFalse(users.isEmpty());
    }

    @Test
    @Order(6)
    void testLoadUserByUsername() {
        UserDetails userDetails = userService.loadUserByUsername("rethabile1154@gmail.co.za");
        System.out.println("Found By Username: " + userDetails);
        assertNotNull(userDetails);
        assertEquals("rethabile1154@gmail.co.za", userDetails.getUsername());
    }

    @Test
    @Order(7)
    void testLoadUserByUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("unknown@example.com"));
    }

    @Test
    @Order(8)
    void testFindByEmail() {
        Optional<User> foundUser = userService.findByEmail("rethabile1154@gmail.co.za");
        System.out.println("Found By Email:\n" + foundUser);
        assertTrue(foundUser.isPresent());
        assertEquals("rethabile1154@gmail.co.za", foundUser.get().getEmail());
    }

    @Test
    @Order(9)
    void testFindByFirstName() {
        List<User> users = userService.findByFirstName("Ntsekhe");
        System.out.println("Found By First Name: " + users);
        assertFalse(users.isEmpty());
        assertEquals("Ntsekhe", users.get(0).getFirstName());
    }

    @Test
    @Order(10)
    void testFindByLastName() {
        List<User> users = userService.findByLastName("Rethabile");
        System.out.println("Found By Last Name: " + users);
        assertFalse(users.isEmpty());
        assertEquals("Rethabile", users.get(0).getLastName());
    }

    @Test
    @Order(11)
    void testFindByBirthDate() {
        List<User> users = userService.findByBirthDate(LocalDate.of(1991, 1, 1));
        System.out.println("Found By Birthday: " + users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    @Order(12)
    void testFindByPhoneNumber() {
        List<User> users = userService.findByPhoneNumber("123456789");
        System.out.println("Found By Phone Number: " + users);
        assertFalse(users.isEmpty());
        assertEquals("123456789", users.get(0).getPhoneNumber());
    }

    @Test
    @Order(13)
    void testFindByUsername() {
        User foundUser = userService.findByUsername("USER12");
        System.out.println("Found By Username: " + foundUser);
        assertNotNull(foundUser);
        assertEquals("USER12", foundUser.getUsername());
    }
}
