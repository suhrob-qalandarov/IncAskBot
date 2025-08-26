package org.exp.incaskbot.service.face;

import org.exp.incaskbot.model.entity.Session;
import org.exp.incaskbot.model.enums.State;
import org.springframework.stereotype.Service;

@Service
public interface SessionService {
    Session getOrCreateSession(com.pengrad.telegrambot.model.User from);
    void updateUserSessionParam(Long chatId, String startParam);
    void updateLastMessageId(Long chatId, Integer integer);
    void updateSessionState(Long chatId, State state);
    Long getChatIdByUrl(String targetUrl);
}
