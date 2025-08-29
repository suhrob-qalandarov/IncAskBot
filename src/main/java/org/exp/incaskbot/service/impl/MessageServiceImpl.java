package org.exp.incaskbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.exp.incaskbot.model.entity.Message;
import org.exp.incaskbot.model.entity.Session;
import org.exp.incaskbot.repository.MessageRepository;
import org.exp.incaskbot.service.face.MessageService;
import org.exp.incaskbot.service.face.SessionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SessionService sessionService;

    @Override
    public void saveMessage(Long to, Integer messageId, Long from, Integer linkedMessageId, String data) {
        Session fromSession = sessionService.getById(from);
        Session toSession = sessionService.getById(to);
        messageRepository.save(
                Message.builder()
                        .from(fromSession)
                        .to(toSession)
                        .messageId(messageId)
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
        return messageRepository.findByToChatIdAndMessageId(id, integer);
    }
}
