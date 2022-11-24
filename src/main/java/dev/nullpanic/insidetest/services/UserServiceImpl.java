package dev.nullpanic.insidetest.services;

import dev.nullpanic.insidetest.dto.UserDTO;
import dev.nullpanic.insidetest.exceptions.GeneralException;
import dev.nullpanic.insidetest.mapper.UserMapper;
import dev.nullpanic.insidetest.persist.models.User;
import dev.nullpanic.insidetest.persist.repositories.UserRepository;
import dev.nullpanic.insidetest.requests.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    public static final String LOG_USER_CREATED = "User created - id: {}, name: {}";
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public UserDTO createUser(UserRequest userRequest) {
        User user = userMapper.mapToEntity(userRequest);
        userRepository.save(user);
        log.info(LOG_USER_CREATED, user.getId(), user.getName());
        return userMapper.mapToDto(user);
    }

    @Override
    @Transactional
    public void saveUserToken(User user, String token) {
        user.setToken(token);
        userRepository.save(user);
    }
}
