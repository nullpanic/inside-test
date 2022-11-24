package dev.nullpanic.insidetest.controllers;

import dev.nullpanic.insidetest.dto.UserDTO;
import dev.nullpanic.insidetest.exceptions.GeneralException;
import dev.nullpanic.insidetest.mapper.MessageMapper;
import dev.nullpanic.insidetest.mapper.UserMapper;
import dev.nullpanic.insidetest.persist.models.Message;
import dev.nullpanic.insidetest.persist.models.User;
import dev.nullpanic.insidetest.requests.MessageRequest;
import dev.nullpanic.insidetest.services.JwtTokenService;
import dev.nullpanic.insidetest.services.MessageService;
import dev.nullpanic.insidetest.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

class MessageControllerTest {
    private final MessageService messageService = Mockito.mock(MessageService.class);
    private final JwtTokenService jwtTokenService = Mockito.mock(JwtTokenService.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final MessageMapper messageMapper = Mockito.mock(MessageMapper.class);
    private final UserMapper userMapper = Mockito.mock(UserMapper.class);
    private final MessageRequest messageRequest= Mockito.mock(MessageRequest.class);
    private Message message = Mockito.mock(Message.class);

    @BeforeEach
    public void init() {
        Mockito.when(messageMapper.mapToEntity(messageRequest))
                .thenReturn(message);
    }

    @Test
    public void testCreateOrGetMessage_WhenUserNotFound_ShouldThrowGeneralException() throws GeneralException {
        MessageController messageController = new MessageController(
                messageService,
                jwtTokenService,
                userService,
                messageMapper,
                userMapper
        );

        String token = "testToken";

        Mockito.when(message.getName())
                .thenReturn("Johnny");
        Mockito.when(userService.findByName("Johnny"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                GeneralException.class,
                () -> messageController.createOrGetMessage(token, messageRequest));
    }

    @Test
    public void testCreateOrGetMessage_WhenUserFound_ShouldInvokeJwtTokenServiceTokenIsValid() throws GeneralException {
        MessageController messageController = new MessageController(
                messageService,
                jwtTokenService,
                userService,
                messageMapper,
                userMapper
        );

        User user = User.builder()
                .name("Johnny")
                .secret("test")
                .id(1L)
                .build();

        String token = "testToken";

        Mockito.when(message.getName())
                .thenReturn(user.getName());
        Mockito.when(userService.findByName("Johnny"))
                .thenReturn(Optional.of(user));

        messageController.createOrGetMessage(token, messageRequest);

        Mockito.verify(jwtTokenService).tokenIsValid(user, token);
    }

    @Test
    public void testCreateOrGetMessage_WhenNotExistMessageHistory_ShouldInvokeMessageServiceCreateMessage() throws GeneralException {
        MessageController messageController = new MessageController(
                messageService,
                jwtTokenService,
                userService,
                messageMapper,
                userMapper
        );

        User user = User.builder()
                .name("Johnny")
                .secret("test")
                .id(1L)
                .build();

        String token = "testToken";

        Mockito.when(message.getName())
                .thenReturn(user.getName());
        Mockito.when(userService.findByName("Johnny"))
                .thenReturn(Optional.of(user));
        Mockito.when(message.getMessage())
                .thenReturn("testMessage");

        messageController.createOrGetMessage(token, messageRequest);

        Mockito.verify(messageService).createMessage(user, message);
    }

    @Test
    public void testCreateOrGetMessage_WhenExistMessageHistory_ShouldReturnResponseEntity() throws GeneralException {
        MessageController messageController = new MessageController(
                messageService,
                jwtTokenService,
                userService,
                messageMapper,
                userMapper
        );

        User user = User.builder()
                .name("Johnny")
                .secret("test")
                .token("testToken")
                .id(1L)
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .name("Johnny")
                .secret("test")
                .build();

        String token = "testToken";

        Mockito.when(message.getName())
                .thenReturn(user.getName());
        Mockito.when(userService.findByName("Johnny"))
                .thenReturn(Optional.of(user));
        Mockito.when(message.getMessage())
                .thenReturn("history");
        Mockito.when(userMapper.mapToDto(user))
                        .thenReturn(userDTO);

        Assertions.assertEquals(
                new ResponseEntity<>(userDTO, HttpStatus.OK),
                messageController.createOrGetMessage(token, messageRequest));
    }
}