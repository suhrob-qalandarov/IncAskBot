package org.exp.incaskbot.config.audit;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramContext {
    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    public static void setCurrentUserId(String userId) {
        currentUser.set(userId);
    }

    public static String getCurrentUserId() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}