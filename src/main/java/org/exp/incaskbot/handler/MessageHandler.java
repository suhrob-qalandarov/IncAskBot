package org.exp.incaskbot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.exp.incaskbot.config.audit.TelegramContext;
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

    @Override
    public void accept(Message message) {
        try {
            String text = message.text();
            Message replied = message.replyToMessage();
            Session session = sessionService.getOrCreateSession(message.from());

            if (replied != null) {
                org.exp.incaskbot.model.entity.Message senderMessage = messageService.findByToIdAndMessageId(
                        replied.chat().id(),
                        replied.messageId()
                );

                if (senderMessage == null) {
                    log.error("Message mapping not found for reply messageId={}", replied.messageId());
                    botMessageService.sendMenuMessage(session.getChatId(), session.getUrl());
                    return;
                }

                System.out.println(senderMessage);
                System.out.println(senderMessage.getMessageId());

                telegramBot.execute(
                        new SendMessage(
                                senderMessage.getFrom().getChatId(),
                                message.text()
                        )
                                .replyToMessageId(senderMessage.getLinkedMessageId())
                                .replyMarkup(new InlineKeyboardMarkup(
                                        new InlineKeyboardButton(
                                                "✍️Write more"
                                        ).callbackData("write_again")
                                ))
                );
                SendResponse response = telegramBot.execute(new SendMessage(
                        session.getChatId(),
                        "🕊Your answer has been sent successfully!"
                ));
                log.error("Response error: {}", response);
                return;

            } else if (session.getState().equals(State.MESSAGE)) {
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

            if (text != null && session.getState().equals(State.MENU)) {
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
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
        } finally {
            TelegramContext.clear();
        }
    }
}
