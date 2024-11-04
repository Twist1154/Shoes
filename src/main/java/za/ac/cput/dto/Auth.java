package za.ac.cput.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import za.ac.cput.domain.User;

@Getter
@Setter
@ToString
@Builder
public class Auth {
private User user;
private String token;
private String status;

}
