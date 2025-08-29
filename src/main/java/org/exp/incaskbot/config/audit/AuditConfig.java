package org.exp.incaskbot.config.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    private final TelegramContext telegramContext;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            try {
                String userId = telegramContext.getCurrentUserId();
                if (userId != null && !userId.isEmpty()) {
                    return Optional.of(userId);
                }
            } catch (Exception e) {
                log.error("Auditorni aniqlashda xatolik: {}", e.getMessage());
            }
            return Optional.of("SYSTEM");
        };
    }
}