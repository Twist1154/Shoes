package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import za.ac.cput.enums.Role;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a user in the system.
 * <p>
 * This entity class is mapped to the "users" table in the database.
 *
 * @author Rethabile
 * @date 25-Aug-24
 */

@Entity
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username", unique = true, nullable = false) // Added username column
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    // ElementCollection with a separate table "user_roles" to store roles
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @JsonIgnore
    private Set<Role> role = new HashSet<>();

    public User() {
    }

    // Private constructor to be used by the builder
    private User(Builder builder) {
        this.id = builder.id;
        this.avatar = builder.avatar;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.username = builder.username; // Added username assignment
        this.email = builder.email;
        this.birthDate = builder.birthDate;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.role.addAll(builder.role);
    }

    @Override
    public String toString() {
        return "\n User{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +  // Updated to include username
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                "}\n ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(avatar, user.avatar) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(username, user.username) &&  // Updated to include username in equality check
                Objects.equals(email, user.email) &&
                Objects.equals(birthDate, user.birthDate) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, avatar, firstName, lastName, username, email, birthDate, password, phoneNumber, role);  // Updated hashCode with username
    }

    public static class Builder {
        private Long id;
        private String avatar;
        private String firstName;
        private String lastName;
        private String username;  // Added username to Builder
        private String email;
        private LocalDate birthDate;
        private String password;
        private String phoneNumber;
        private Set<Role> role = new HashSet<>();

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setUsername(String username) {  // New setter for username
            this.username = username;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setRole(Set<Role> role) {
            this.role = role;
            return this;
        }

        public Builder copy(User user) {
            this.id = user.getId();
            this.avatar = user.getAvatar();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.username = user.getUsername();  // Added username in copy method
            this.email = user.getEmail();
            this.birthDate = user.getBirthDate();
            this.password = user.getPassword();
            this.phoneNumber = user.getPhoneNumber();
            this.role = new HashSet<>(user.getRole());
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
