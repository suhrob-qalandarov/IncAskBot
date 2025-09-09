package org.exp.incaskbot.repository;

import org.exp.incaskbot.model.entity.IncognitoChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncChatRepository extends JpaRepository<IncognitoChat, Long> {
}
