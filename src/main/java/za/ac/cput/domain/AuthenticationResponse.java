package za.ac.cput.domain;

public class AuthenticationResponse {

    private String token;

    /**
     * Constructs a new {@code AuthenticationResponse} with the provided token.
     *
     * @param token the JWT token issued after successful authentication
     */
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    /**
     * Retrieves the JWT token.
     *
     * @return the JWT token
     */
    public String getToken() {
        return token;
    }

}
