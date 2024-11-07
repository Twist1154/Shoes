package za.ac.cput.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * UserAuth.java
 *
 * @author Rethabile Ntsekhe
 * Student Num: 220455430
 * @date 24-Oct-24
 */
@Setter
@Getter
@ToString
public class UserAuth {
    private String email;
    private String password;
}
