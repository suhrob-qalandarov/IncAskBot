package org.exp.incaskbot.service.face;

import org.exp.incaskbot.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getOrCreateUser(com.pengrad.telegrambot.model.User telegramUser);

    boolean existsChat(Long chatId, String param);

    void addChatToUser(Long chatId, String param);

    User updateUser(User user);
}
