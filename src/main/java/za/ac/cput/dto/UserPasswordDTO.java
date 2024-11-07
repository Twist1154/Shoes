package za.ac.cput.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserPasswordDTO {
    private String email;
    private String password;


}
