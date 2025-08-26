package org.exp.incaskbot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.exp.incaskbot.model.Session;
import org.exp.incaskbot.model.enums.State;
import org.exp.incaskbot.service.face.MessageService;
import org.exp.incaskbot.service.face.SessionService;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackQueryHandler implements Consumer<CallbackQuery> {

    private final SessionService sessionService;
    private final MessageService messageService;

    @Override
    public void accept(CallbackQuery callbackQuery) {
        String data = callbackQuery.data();
        Session session = sessionService.getOrCreateSession(callbackQuery.from());

        if (data.equals("delete_message_wait") && session.getState().equals(State.MESSAGE)) {
            messageService.deleteMessage(session.getChatId(), session.getLastMessageId());
            sessionService.updateSessionState(session.getChatId(), State.MENU);
            messageService.sendMenuMessage(session.getChatId(), session.getUrl());
            return;

        } else {
            log.info("Unknown callback query data={}", data);
            return;
        }
    }
}
