package dev.nullpanic.insidetest.controllers;

import dev.nullpanic.insidetest.dto.UserDTO;
import dev.nullpanic.insidetest.enums.Errors;
import dev.nullpanic.insidetest.exceptions.GeneralException;
import dev.nullpanic.insidetest.mapper.MessageMapper;
import dev.nullpanic.insidetest.mapper.UserMapper;
import dev.nullpanic.insidetest.persist.models.Message;
import dev.nullpanic.insidetest.persist.models.User;
import dev.nullpanic.insidetest.requests.MessageRequest;
import dev.nullpanic.insidetest.services.JwtTokenService;
import dev.nullpanic.insidetest.services.MessageService;
import dev.nullpanic.insidetest.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;


    public MessageController(MessageService messageService, JwtTokenService jwtTokenService, UserService userService, MessageMapper messageMapper, UserMapper userMapper) {
        this.messageService = messageService;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> createOrGetMessage(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody MessageRequest messageRequest) throws GeneralException {

        Message message = messageMapper.mapToEntity(messageRequest);
        User user = userService.findByName(message.getName())
                .orElseThrow(
                        () -> new GeneralException(
                                Errors.USER_NOT_FOUND.getMessage(),
                                HttpStatus.NOT_FOUND)
                );

        jwtTokenService.tokenIsValid(user, token);

        if (!"history".equals(message.getMessage())) {
            messageService.createMessage(user, message);
        }

        return new ResponseEntity<>(userMapper.mapToDto(user), HttpStatus.OK);
    }

}
