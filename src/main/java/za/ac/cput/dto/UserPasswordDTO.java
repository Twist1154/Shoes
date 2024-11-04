package za.ac.cput.dto;

public class UserPasswordDTO {
    private String username;
    private String password;

    public UserPasswordDTO() {
    }

    public UserPasswordDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserPasswordDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
