package dev.nullpanic.insidetest.controllers;

import dev.nullpanic.insidetest.dto.UserDTO;
import dev.nullpanic.insidetest.requests.UserRequest;
import dev.nullpanic.insidetest.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private final UserService userService = Mockito.mock(UserService.class);
    private final UserRequest userRequest = Mockito.mock(UserRequest.class);
    private final UserDTO userDTO = Mockito.mock(UserDTO.class);

    @Test
    public void testCreateNewUser_WhenUserRequestExist_ShouldReturnResponseEntity() {
        UserController userController = new UserController(userService);

        Mockito.when(userService.createUser(userRequest))
                        .thenReturn(userDTO);

        assertEquals(new ResponseEntity<>(userDTO, HttpStatus.CREATED), userController.createNewUser(userRequest));
    }
}