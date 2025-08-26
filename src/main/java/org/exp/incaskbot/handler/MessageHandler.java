package org.exp.incaskbot.handler;

import com.pengrad.telegrambot.model.Message;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.exp.incaskbot.model.entity.Session;
import org.exp.incaskbot.model.enums.State;
import org.exp.incaskbot.service.face.MessageService;
import org.exp.incaskbot.service.face.SessionService;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandler implements Consumer<Message> {

    private final MessageService messageService;
    private final SessionService sessionService;

    @Override
    public void accept(Message message) {
        String text = message.text();
        Session session = sessionService.getOrCreateSession(message.from());

        if (session.getState().equals(State.MESSAGE)) {
            messageService.deleteMessage(session.getChatId(), session.getLastMessageId());
            if (text != null && !text.isBlank()) {
                messageService.sendIncognitoTextMessage(session, message);

            } else if (message.audio() != null) {
                messageService.sendIncognitoAudioMessage(session, message);

            } else if (message.video() != null) {
                messageService.sendIncognitoVideoMessage(session, message);

            } else if (message.animation() != null) {
                messageService.sendIncognitoAnimationMessage(session, message);

            } else if (message.voice() != null) {
                messageService.sendIncognitoVoiceMessage(session, message);

            }  else if (message.photo() != null) {
                messageService.sendIncognitoPhotoMessage(session, message);

            } else if (message.videoNote() != null) {
                messageService.sendIncognitoVideoNoteMessage(session, message);

            } else if (message.sticker() != null) {
                messageService.sendIncognitoStickerMessage(session, message);

            } else if (message.document() != null) {
                messageService.sendIncognitoDocumentMessage(session, message);
            } else {
                log.info("Unknown message={}", message);
            }
        }

        if (text!=null && session.getState().equals(State.MENU)) {
            if (text.startsWith("/start")) {
                String[] parts = message.text().split(" ", 2);
                String startParam = parts.length > 1 ? parts[1] : null;

                if (startParam != null) {
                    messageService.sendParamMenuMessage(session.getChatId());
                    sessionService.updateUserSessionParam(session.getChatId(), startParam);
                    return;
                } else {
                    messageService.sendMenuMessage(session.getChatId(), session.getUrl());
                    return;
                }

            } else if (text.equals("/info")) {
                System.out.println("info");
                return;
            } else {
                messageService.sendMenuMessage(session.getChatId(), session.getUrl());
                return;
            }
        } /*else {
            messageService.sendMenuMessage(session.getChatId(), session.getUrl());
            return;
        }*/
    }
}
