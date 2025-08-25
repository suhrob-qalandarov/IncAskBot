package org.exp.incaskbot.service.face;

import org.exp.incaskbot.model.User;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    void sendMenuMessage(User dbUser);
}
