package org.exp.incaskbot.service.face;

import org.exp.incaskbot.model.entity.Message;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    void saveMessage(Long toId, Integer messageId, Long fromId, Integer linkedMessageId, String data);

    Long getSenderUserUsingMessageId(Integer messageId);

    Message getMessageWithSenderMessageId(Integer senderMessageId);

    Message findByToIdAndMessageId(Long id, Integer integer);
}
