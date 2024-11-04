package za.ac.cput.dto;

public class UserAuth {
    private String username;
    private String password;

    public UserAuth() {
    }

    public UserAuth(String username, String password) {
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
        return "UserAuth{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
