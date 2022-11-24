package dev.nullpanic.insidetest.services;

import dev.nullpanic.insidetest.dto.UserDTO;
import dev.nullpanic.insidetest.persist.models.User;
import dev.nullpanic.insidetest.requests.UserRequest;

import java.util.Optional;

public interface UserService {

    Optional<User> findByName(String name);

    UserDTO createUser(UserRequest userRequest);

    void saveUserToken(User user, String token);
}
