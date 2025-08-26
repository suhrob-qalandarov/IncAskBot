package org.exp.incaskbot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.incaskbot.model.entity.User;
import org.exp.incaskbot.repository.UserRepository;
import org.exp.incaskbot.service.face.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getOrCreateUser(com.pengrad.telegrambot.model.User telegramUser) {
        Optional<User> optionalUser = userRepository.findById(telegramUser.id());
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return userRepository.save(mapToUser(telegramUser));
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
