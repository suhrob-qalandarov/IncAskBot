package org.exp.incaskbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.exp.incaskbot.model.entity.Message;
import org.exp.incaskbot.repository.MessageRepository;
import org.exp.incaskbot.service.face.MessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public void saveMessage(Long toId, Integer messageId, Long fromId, Integer linkedMessageId, String data) {
        messageRepository.save(
                Message.builder()
                        .toId(toId)
                        .messageId(messageId)
                        .fromId(fromId)
                        .linkedMessageId(linkedMessageId)

                        .build()
        );
    }

    @Override
    public Long getSenderUserUsingMessageId(Integer messageId) {
        return messageRepository.getSenderUserId(messageId);
    }

    @Override
    public Message getMessageWithSenderMessageId(Integer senderMessageId) {
        return messageRepository.findByMessageId(senderMessageId);
    }

    @Override
    public Message findByToIdAndMessageId(Long id, Integer integer) {
        return messageRepository.findByToIdAndMessageId(id, integer);
    }
}
