package dev.nullpanic.insidetest.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Roles {

    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

}
