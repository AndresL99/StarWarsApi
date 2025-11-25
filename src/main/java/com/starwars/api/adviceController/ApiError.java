package com.starwars.api.adviceController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private List<String>errors;

    public ApiError(HttpStatus httpStatus, String localizedMessage, List<String> errors) {
        this.httpStatus = httpStatus;
        this.message = localizedMessage;
        this.errors = errors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
