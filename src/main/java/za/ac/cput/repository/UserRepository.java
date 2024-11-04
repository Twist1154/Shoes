package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * UserRepository.java
 *
 * Repository interface for User entity.
 * Provides methods for accessing and modifying User data in the database.
 *
 * @author Rethabile Ntsekhe
 * @date 24-Aug-24
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the User if found, otherwise empty
     */
    Optional<User> findByEmail(String email);



    /**
     * Finds all Users by their first name.
     *
     * @param firstName the first name to search for
     * @return a List of Users with the specified first name
     */
    List<User> findByFirstName(String firstName);

    /**
     * Finds all Users by their last name.
     *
     * @param lastName the last name to search for
     * @return a List of Users with the specified last name
     */
    List<User> findByLastName(String lastName);

    /**
     * Finds all Users by their birth date.
     *
     * @param birthDate the birth date to search for
     * @return a List of Users with the specified birth date
     */
    List<User> findByBirthDate(LocalDate birthDate);

    /**
     * Finds all Users by their phone number.
     *
     * @param phoneNumber the phone number to search for
     * @return a List of Users with the specified phone number
     */
    List<User> findByPhoneNumber(String phoneNumber);

    /**
     * Finds all Users by their role.
     *
     * @param username the role to search for
     * @return a List of Users with the specified username
     */
    Optional<User> findByUsername(String username);
}