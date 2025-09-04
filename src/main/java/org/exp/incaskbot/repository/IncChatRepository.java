package org.exp.incaskbot.repository;

import org.exp.incaskbot.model.entity.IncChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncChatRepository extends JpaRepository<IncChat, Long> {
}
