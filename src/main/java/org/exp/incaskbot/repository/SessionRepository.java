package org.exp.incaskbot.repository;

import jakarta.transaction.Transactional;
import org.exp.incaskbot.model.Session;
import org.exp.incaskbot.model.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByChatId(Long userId);

    @Query("SELECT s.chatId FROM Session s WHERE s.url = :url")
    Long findChatIdByUrl(@Param("url") String url);

    @Modifying
    @Query("UPDATE Session s SET s.state = :state, s.url = :url WHERE s.chatId = :chatId")
    int updateSessionStateAndUrl(@Param("chatId") Long chatId, @Param("state") State state, @Param("url") String url);

    @Modifying
    @Query("UPDATE Session s SET s.state = :state, s.toMessageUrl = :toUrl WHERE s.chatId = :chatId")
    int updateSessionStateAndToMessageUrl(@Param("chatId") Long chatId,
                                          @Param("state") State state,
                                          @Param("toUrl") String toUrl);

    @Modifying
    @Transactional
    @Query("UPDATE Session s SET s.state = :state WHERE s.chatId = :chatId")
    void updateStateByChatId(@Param("chatId") Long chatId, @Param("state") State state);

    @Modifying
    @Transactional
    @Query("UPDATE Session s SET s.lastMessageId = :lastMessageId WHERE s.chatId = :chatId")
    void updateLastMessageIdByChatId(@Param("chatId") Long chatId,
                                     @Param("lastMessageId") Integer lastMessageId);
}