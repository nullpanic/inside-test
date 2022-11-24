package dev.nullpanic.insidetest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Errors {
    USER_NOT_FOUND("User not found"),
    AUTHENTICATION_FAILED("Authentication failed");

    private final String message;
}
