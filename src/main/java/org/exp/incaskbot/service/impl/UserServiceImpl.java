package org.exp.incaskbot.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.incaskbot.model.entity.IncognitoUri;
import org.exp.incaskbot.model.entity.User;
import org.exp.incaskbot.model.enums.UriType;
import org.exp.incaskbot.repository.UserRepository;
import org.exp.incaskbot.service.face.UserService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User getOrCreateUser(com.pengrad.telegrambot.model.User telegramUser) {
        Optional<User> optionalUser = userRepository.findById(telegramUser.id());
        if (optionalUser.isPresent()) return optionalUser.get();

        User newUser = mapToUser(telegramUser);
        User savedUser = userRepository.save(newUser);
        savedUser.getUrlList().add(
                IncognitoUri.builder()
                        .user(newUser)
                        .uri(createUniqueUrl(telegramUser.id()))
                        .type(UriType.MAIN)
                        .build());
        return userRepository.save(savedUser);
    }

    @Override
    public boolean existsChat(Long chatId, String param) {
        return userRepository.existsChat(chatId, param);
    }

    @Override
    public void addChatToUser(Long chatId, String param) {
        userRepository.addChatToUser(chatId, param);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void updateUserUrl(String url) {

    }

    public User updateDbUser(com.pengrad.telegrambot.model.User tgUser) {
        User updatedUser = mapToUser(tgUser);
        return userRepository.updateAndReturnUserInfo(
                updatedUser.getId(),
                updatedUser.getFullName(),
                updatedUser.getUsername()
        );
    }

    private void updateUserFields(User dbUser, com.pengrad.telegrambot.model.User tgUser) {
        dbUser.setFullName(
                tgUser.lastName() != null && !tgUser.lastName().isBlank()
                        ? tgUser.firstName() + " " + tgUser.lastName()
                        : tgUser.firstName()
        );
        dbUser.setUsername(tgUser.username());
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

    private User mapToUser(com.pengrad.telegrambot.model.User user) {
        return User.builder()
                .id(user.id())
                .fullName(
                        user.lastName() != null && !user.lastName().isBlank()
                                ? user.firstName() + " " + user.lastName()
                                : user.firstName()
                )
                .username(user.username())
                .build();
    }
}
