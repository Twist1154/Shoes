package za.ac.cput.dto.Exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AccessDeniedExceptionResponse extends ExceptionResponse {
    private String action;

    public AccessDeniedExceptionResponse(LocalDateTime timestamp, String message, String details, String action) {
        super(timestamp, message, details);
        this.action = action;
    }

}
