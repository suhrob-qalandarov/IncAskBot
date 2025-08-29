package org.exp.incaskbot.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.exp.incaskbot.config.audit.TelegramContext;
import org.exp.incaskbot.model.entity.Session;
import org.exp.incaskbot.model.entity.User;
import org.exp.incaskbot.model.enums.State;
import org.exp.incaskbot.repository.SessionRepository;
import org.exp.incaskbot.repository.UserRepository;
import org.exp.incaskbot.service.face.SessionService;
import org.exp.incaskbot.service.face.UserService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserService userService;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Override
    public Session getOrCreateSession(com.pengrad.telegrambot.model.User from) {

        String userId = from != null ? from.id().toString() : "UNKNOWN";
        TelegramContext.setCurrentUserId(userId);

        assert from != null;
        Optional<Session> optionalSession = sessionRepository.findByChatId(from.id());
        if (optionalSession.isPresent()) {
            return optionalSession.get();
        }

        User dbUser = userService.getOrCreateUser(from);
        return sessionRepository.save(
                Session.builder()
                        .chatId(dbUser.getId())
                        .url(createUniqueUrl(from.id()))
                        .isAdmin(from.id().equals(6513286717L))
                        .state(State.MENU)
                        .user(dbUser)
                        .build()
        );
    }

    @Override
    @Transactional
    public void updateUserSessionParam(Long chatId, String startParam) {
        sessionRepository.updateSessionStateAndToMessageUrl(chatId, State.MESSAGE, startParam);
        if (!userRepository.existsChat(chatId, startParam)) {
            userRepository.addChatToUser(chatId, startParam);
        }
    }

    @Override
    public void updateLastMessageId(Long chatId, Integer messageId) {
        sessionRepository.updateLastMessageIdByChatId(chatId, messageId);
    }

    @Override
    public void updateSessionState(Long chatId, State state) {
        sessionRepository.updateStateByChatId(chatId, state);
    }

    @Override
    public Long getChatIdByUrl(String targetUrl) {
        return sessionRepository.findChatIdByUrl(targetUrl);
    }

    @Override
    public Session getById(Long id) {
        return sessionRepository.findById(id).orElseThrow();
    }

    private String createUniqueUrl(Long userId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(String.valueOf(userId).getBytes());
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(Arrays.copyOf(hash, 6));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
