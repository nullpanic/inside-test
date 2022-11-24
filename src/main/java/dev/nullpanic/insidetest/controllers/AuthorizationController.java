package dev.nullpanic.insidetest.controllers;

import dev.nullpanic.insidetest.dto.JwtResponseDTO;
import dev.nullpanic.insidetest.enums.Errors;
import dev.nullpanic.insidetest.exceptions.GeneralException;
import dev.nullpanic.insidetest.persist.models.User;
import dev.nullpanic.insidetest.requests.JwtRequest;
import dev.nullpanic.insidetest.services.JwtTokenService;
import dev.nullpanic.insidetest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    JwtTokenService jwtTokenService;
    UserService userService;

    @Autowired
    public AuthorizationController(JwtTokenService jwtTokenService, UserService userService) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<JwtResponseDTO> createJwtToken(@RequestBody JwtRequest jwtRequest) throws GeneralException {
        String username = jwtRequest.getLogin();
        String secret = jwtRequest.getSecret();

        if (username == null || secret == null) {
            throwAuthenticationFailedGeneralException();
        }

        User user = userService.findByName(username)
                .orElseThrow(() -> new GeneralException(Errors.AUTHENTICATION_FAILED.getMessage(), HttpStatus.UNAUTHORIZED));

        if (!secret.equals(user.getSecret())) {
            throwAuthenticationFailedGeneralException();
        }

        String token = jwtTokenService.getToken(user, secret);

        userService.saveUserToken(user, token);

        JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                .token(token)
                .build();

        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
    }

    public void throwAuthenticationFailedGeneralException() throws GeneralException {
        throw new GeneralException(Errors.AUTHENTICATION_FAILED.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
