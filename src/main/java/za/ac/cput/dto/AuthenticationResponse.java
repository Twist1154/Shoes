package za.ac.cput.dto;

import za.ac.cput.domain.User;

public class AuthenticationResponse {

    private User user;
    private String token;

    /**
     * Constructs a new {@code AuthenticationResponse} with the provided user and token.
     *
     * @param user the authenticated user
     * @param token the JWT token issued after successful authentication
     */
    public AuthenticationResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    /**
     * Retrieves the authenticated user.
     *
     * @return the user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Retrieves the JWT token.
     *
     * @return the JWT token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the user object.
     *
     * @param user the authenticated user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the JWT token.
     *
     * @param token the JWT token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
