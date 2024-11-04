package za.ac.cput.dto;

import lombok.Getter;
import za.ac.cput.domain.User;

public class AuthenticationResponse {
    @Getter
    private User user;

    @Getter
    private String token;

    /**
     * Constructs a new {@code AuthenticationResponse} with the provided token.
     *
     * @param token the JWT token issued after successful authentication
     */
    public AuthenticationResponse(String token) {
        this.token = token;
    }

}
