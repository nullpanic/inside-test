package dev.nullpanic.insidetest.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {
    private String login;
    private String secret;
}
