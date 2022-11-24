package dev.nullpanic.insidetest.controllers;

import dev.nullpanic.insidetest.dto.JwtResponseDTO;
import dev.nullpanic.insidetest.exceptions.GeneralException;
import dev.nullpanic.insidetest.persist.models.User;
import dev.nullpanic.insidetest.requests.JwtRequest;
import dev.nullpanic.insidetest.services.JwtTokenService;
import dev.nullpanic.insidetest.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


class AuthorizationControllerTest {

    private final JwtTokenService jwtTokenService = Mockito.mock(JwtTokenService.class);
    private final UserService userService = Mockito.mock(UserService.class);


    private final JwtRequest jwtRequest = Mockito.mock(JwtRequest.class);
    private final User user = Mockito.mock(User.class);


    @BeforeEach
    public void init() {
        Mockito.when(jwtRequest.getLogin()).thenReturn("Johnny");
        Mockito.when(jwtRequest.getSecret()).thenReturn("test");
    }

    @Test
    public void testCreateJwtToken_WhenUsernameIsNull_ShouldThrowGeneralException() {
        Mockito.when(jwtRequest.getLogin()).thenReturn(null);

        AuthorizationController authorizationController = new AuthorizationController(jwtTokenService, userService);

        Assertions.assertThrows(GeneralException.class, () -> authorizationController.createJwtToken(jwtRequest));
    }

    @Test
    public void testCreateJwtToken_WhenSecretIsNull_ShouldThrowGeneralException() throws GeneralException {
        Mockito.when(jwtRequest.getSecret()).thenReturn(null);

        AuthorizationController authorizationController = new AuthorizationController(jwtTokenService, userService);

        Assertions.assertThrows(GeneralException.class, () -> authorizationController.createJwtToken(jwtRequest));
    }

    @Test void testCreateJwtToken_WhenUserNotFound_ShouldThrowGeneralException() {
        AuthorizationController authorizationController = new AuthorizationController(jwtTokenService, userService);
        Assertions.assertThrows(GeneralException.class, () -> authorizationController.createJwtToken(jwtRequest));
    }

    @Test void testCreateJwtToken_WhenInvalidSecret_ShouldThrowGeneralException() {
        AuthorizationController authorizationController = new AuthorizationController(jwtTokenService, userService);

        Mockito.when(user.getSecret()).thenReturn("Invalid secret");

        Assertions.assertThrows(GeneralException.class, () -> authorizationController.createJwtToken(jwtRequest));
    }

    @Test void testCreateJwtToken_WhenUserFound_ShouldInvokeSaveUserToken() throws GeneralException {
        User user = User.builder()
                .name("Johnny")
                .secret("test")
                .id(1L)
                .build();

        Mockito.when(userService.findByName("Johnny"))
                .thenReturn(Optional.of(user));
        Mockito.when(jwtTokenService.getToken(user, jwtRequest.getSecret()))
                .thenReturn("testToken");

        AuthorizationController authorizationController = new AuthorizationController(jwtTokenService, userService);
        authorizationController.createJwtToken(jwtRequest);

        Mockito.verify(userService).saveUserToken(user, "testToken");
    }

    @Test void testCreateJwtToken_WhenUserFound_ShouldReturnResponseEntity() throws GeneralException {
        User user = User.builder()
                .name("Johnny")
                .secret("test")
                .id(1L)
                .build();

        JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                .token("testToken")
                .build();

        Mockito.when(userService.findByName("Johnny"))
                .thenReturn(Optional.of(user));
        Mockito.when(jwtTokenService.getToken(user, jwtRequest.getSecret()))
                .thenReturn("testToken");

        AuthorizationController authorizationController = new AuthorizationController(jwtTokenService, userService);

        Assertions.assertEquals(
                new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK),
                authorizationController.createJwtToken(jwtRequest)
        );
    }
}