package dev.nullpanic.insidetest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JwtRequestDTO {
    private String login;
    private String secret;
}
