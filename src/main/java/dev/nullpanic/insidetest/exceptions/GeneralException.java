package dev.nullpanic.insidetest.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GeneralException extends Exception {
    private final String message;
    private final HttpStatus httpStatus;

    public GeneralException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.httpStatus = status;
    }

}
