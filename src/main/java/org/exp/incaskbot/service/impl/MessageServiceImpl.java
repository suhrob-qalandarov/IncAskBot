package org.exp.incaskbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.exp.incaskbot.model.User;
import org.exp.incaskbot.service.face.MessageService;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Override
    public void sendMenuMessage(User dbUser) {

    }
}
