package dev.nullpanic.insidetest.services;

import dev.nullpanic.insidetest.exceptions.GeneralException;
import dev.nullpanic.insidetest.persist.models.Message;
import dev.nullpanic.insidetest.persist.models.User;
import dev.nullpanic.insidetest.persist.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    public static final String LOG_MESSAGE_CREATED = "Message created - id: {}";
    private final UserRepository userRepository;

    public MessageServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Message createMessage(User user, Message message) throws GeneralException {

        message.setUser_id(user.getId());
        message.setName(user.getName());

        user.getMessages().add(message);

        userRepository.save(user);

        log.info(LOG_MESSAGE_CREATED, message.getId());

        return user.getMessages().stream().reduce((first, second) -> second).get();
    }
}
