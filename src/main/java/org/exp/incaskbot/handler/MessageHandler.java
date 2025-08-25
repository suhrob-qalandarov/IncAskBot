package org.exp.incaskbot.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.passport.PassportData;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.exp.incaskbot.model.User;
import org.exp.incaskbot.service.face.MessageService;
import org.exp.incaskbot.service.face.UserService;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandler implements Consumer<Message> {

    private final MessageService messageService;
    private final UserService userService;

    @Override
    public void accept(Message message) {
        String text = message.text();
        User dbUser = userService.getOrCreateUser(message.from());

        System.out.println(message);

        if (text!=null) {
            if (text.startsWith("/start")) {
                String[] parts = message.text().split(" ", 2);
                String payload = parts.length > 1 ? parts[1] : null;

                if (payload != null) {
                    System.out.println("Start param: " + payload);
                } else {
                    messageService.sendMenuMessage(dbUser);
                }

            } else if (text.equals("/info")) {
                System.out.println("info");
            }
        }
    }
}
