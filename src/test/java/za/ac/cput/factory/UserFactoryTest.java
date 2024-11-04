package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.User;
import za.ac.cput.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserFactoryTest {

    private User user;
    private Set<Role> roles;

    @BeforeEach
    void setup() {
         roles = new HashSet<>(Set.of(Role.USER, Role.ADMIN));



        // Set up a sample User object using the factory method
        user = UserFactory.createUser(
                null,
                "avatar.jpg",
                "John",
                "Doe",
                "user1",
                "johndoe@example.com",
                LocalDate.parse("1990-01-01"),
                roles,
                "0123456789",
                "password123");
    }

    @Test
    void testCreateUser() {
        // Verify that the User object is not null
        assertNotNull(user);

        // Print the created User object to the terminal
        System.out.println("Created User: " + user);
    }

    @Test
    void testCreateUser_WithNullFirstName_ThrowsIllegalArgumentException() {
        // Try to create a User object with null first name
        assertThrows(IllegalArgumentException.class,
                () -> UserFactory.createUser(
                        null,
                        "avatar.jpg",
                        null,
                        "Doe",
                        "user2",
                        "johndoe@example.com",
                        LocalDate.parse("1990-01-01"),
                        roles,
                        "0123456789",
                        "password123"));

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating User with null first name");
    }

    @Test
    void testCreateUser_WithNullLastName_ThrowsIllegalArgumentException() {
        // Try to create a User object with null last name
        assertThrows(IllegalArgumentException.class,
                () -> UserFactory.createUser(
                        null,
                        "avatar.jpg",
                        "John",
                        null,
                        "user3",
                        "johndoe@example.com",
                        LocalDate.parse("1990-01-01"),
                        roles,
                        "0123456789",
                        "password123"));

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating User with null last name");
    }

    @Test
    void testCreateUser_WithNullEmail_ThrowsIllegalArgumentException() {
        // Try to create a User object with null email
        assertThrows(IllegalArgumentException.class,
                () -> UserFactory.createUser(
                        null,
                        "avatar.jpg",
                        "John",
                        "Doe",
                        null,
                        "user5",
                        LocalDate.parse("1990-01-01"),
                        roles,
                        "0123456789",
                        "password123"));

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating User with null email");
    }

    @Test
    void testCreateUser_WithNullPassword_ThrowsIllegalArgumentException() {
        // Try to create a User object with null password
        assertThrows(IllegalArgumentException.class,
                () -> UserFactory.createUser(
                        null,
                        "avatar.jpg",
                        "John",
                        "Doe",
                        "user6",
                        "johndoe@example.com",
                        LocalDate.parse("1990-01-01"),
                        roles,
                        "0123456789",
                        null));

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating User with null password");
    }

    @Test
    void testCreateUserForSignIn() {
        // Create a User object for sign-in using the factory method
        User signInUser = UserFactory.createUserForSignIn("johndoe@example.com", "password123");

        // Verify that the User object is not null
        assertNotNull(signInUser);

        // Print the created User object to the terminal
        System.out.println("Created User for sign-in: " + signInUser);
    }

    @Test
    void testCreateUserForSignIn_WithNullEmail_ThrowsIllegalArgumentException() {
        // Try to create a User object for sign-in with null email
        assertThrows(IllegalArgumentException.class,
                () -> UserFactory.createUserForSignIn(null, "password123"));

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating User for sign-in with null email");
    }

    @Test
    void testCreateUserForSignIn_WithNullPassword_ThrowsIllegalArgumentException() {
        // Try to create a User object for sign-in with null password
        assertThrows(IllegalArgumentException.class,
                () -> UserFactory.createUserForSignIn("johndoe@example.com", null));

        // Print a message to the terminal indicating that an exception was thrown
        System.out.println("Expected IllegalArgumentException thrown when creating User for sign-in with null password");
    }

}