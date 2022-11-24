package dev.nullpanic.insidetest.services;

import dev.nullpanic.insidetest.exceptions.GeneralException;
import dev.nullpanic.insidetest.persist.models.Message;
import dev.nullpanic.insidetest.persist.models.User;

public interface MessageService {
    Message createMessage(User user, Message message) throws GeneralException;
}
