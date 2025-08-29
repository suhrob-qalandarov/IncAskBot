package org.exp.incaskbot.repository;

import org.exp.incaskbot.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT from_chat_id FROM messages WHERE message_id = :messageId", nativeQuery = true)
    Long getSenderUserId(@Param("messageId") Integer messageId);

    Message findByMessageId(Integer messageId);

    Message findByToChatIdAndMessageId(Long toId, Integer messageId);
}
