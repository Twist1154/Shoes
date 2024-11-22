package za.ac.cput.dto.Exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationExceptionResponse extends ExceptionResponse {
    private Map<String, String> fieldErrors;

    public ValidationExceptionResponse(LocalDateTime timestamp, String message, String details, Map<String, String> fieldErrors) {
        super(timestamp, message, details);
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
