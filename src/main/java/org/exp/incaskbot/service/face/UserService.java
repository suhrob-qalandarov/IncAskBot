package org.exp.incaskbot.service.face;

import org.exp.incaskbot.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getOrCreateUser(com.pengrad.telegrambot.model.User telegramUser);

}
