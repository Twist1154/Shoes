
package za.ac.cput.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.User;
import za.ac.cput.enums.Role;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    private User user;
    private Set<Role> roles;

    @BeforeEach
    void setUp() {
        roles = new HashSet<>(Set.of(Role.USER, Role.ADMIN));

        user = UserFactory.createUser(
                null,
                "imageURl ",
                "Rethabible",
                "Ntsekhe",
                "email@repoTests.com",
                LocalDate.now(),
                roles,
                "0766818425",
                "ThisPassword"
        );
    }

    @Test
    void testSaveAndFindUser() {
        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findByEmail("email@repoTests.com");

        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getEmail(), foundUser.get().getEmail());
        assertNotNull(foundUser.get().getId());
    }

    @Test
    void testUpdateUserPassword() {
        User savedUser = userRepository.save(user);
        User updateduser = new User.Builder()
                .copy(savedUser)
                .setId(savedUser.getId())
                .setPassword("newPassword123")
                .build();

        userRepository.save(updateduser);
        Optional<User> foundUser = userRepository.findByEmail("email@repoTests.com");
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getPassword(), foundUser.get().getPassword());
    }

    @Test
    void testFindAllUsers() {
        userRepository.save(user);
        assertFalse(userRepository.findAll().isEmpty());
    }

    @Test
    void testDeleteUser() {
        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());

        Optional<User> foundUser = userRepository.findByEmail("email@repoTests.com");
        assertFalse(foundUser.isPresent());
    }
}

