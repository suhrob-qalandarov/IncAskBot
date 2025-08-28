package org.exp.incaskbot.handler;

import com.pengrad.telegrambot.model.Message;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.exp.incaskbot.model.entity.Session;
import org.exp.incaskbot.model.enums.State;
import org.exp.incaskbot.service.face.BotMessageService;
import org.exp.incaskbot.service.face.MessageService;
import org.exp.incaskbot.service.face.SessionService;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandler implements Consumer<Message> {

    private final BotMessageService botMessageService;
    private final SessionService sessionService;
    private final TelegramBot telegramBot;
    private final MessageService messageService;
    private final SessionService sessionService;

    @Override
    public void accept(Message message) {
        String text = message.text();
        Session session = sessionService.getOrCreateSession(message.from());

        if (session.getState().equals(State.MESSAGE)) {
            messageService.deleteMessage(session.getChatId(), session.getLastMessageId());
            botMessageService.deleteMessage(session.getChatId(), session.getLastMessageId());
            if (text != null && !text.isBlank()) {
                botMessageService.sendIncognitoTextMessage(session, message);

            } else if (message.audio() != null) {
                botMessageService.sendIncognitoAudioMessage(session, message);

            } else if (message.video() != null) {
                botMessageService.sendIncognitoVideoMessage(session, message);

            } else if (message.animation() != null) {
                botMessageService.sendIncognitoAnimationMessage(session, message);

            } else if (message.voice() != null) {
                botMessageService.sendIncognitoVoiceMessage(session, message);

            } else if (message.photo() != null) {
                botMessageService.sendIncognitoPhotoMessage(session, message);

            } else if (message.videoNote() != null) {
                botMessageService.sendIncognitoVideoNoteMessage(session, message);

            } else if (message.sticker() != null) {
                botMessageService.sendIncognitoStickerMessage(session, message);

            } else if (message.document() != null) {
                botMessageService.sendIncognitoDocumentMessage(session, message);
            } else {
                log.info("Unknown message={}", message);
            }
        }

        if (text!=null && session.getState().equals(State.MENU)) {
            if (text.startsWith("/start")) {
                String[] parts = message.text().split(" ", 2);
                String startParam = parts.length > 1 ? parts[1] : null;

                if (startParam != null) {
                    botMessageService.sendParamMenuMessage(session.getChatId());
                    sessionService.updateUserSessionParam(session.getChatId(), startParam);
                    return;
                } else {
                    botMessageService.sendMenuMessage(session.getChatId(), session.getUrl());
                    return;
                }

            } else if (text.equals("/info")) {
                System.out.println("info");
                return;
            } else {
                botMessageService.sendMenuMessage(session.getChatId(), session.getUrl());
                return;
            }
        } /*else {
            messageService.sendMenuMessage(session.getChatId(), session.getUrl());
            return;
        }*/
    }
}
