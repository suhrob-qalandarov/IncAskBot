package org.exp.incaskbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.incaskbot.model.entity.Session;
import org.exp.incaskbot.model.enums.State;
import org.exp.incaskbot.service.face.ButtonService;
import org.exp.incaskbot.service.face.SessionService;
import org.springframework.stereotype.Service;
import org.exp.incaskbot.service.face.MessageService;

import static org.exp.incaskbot.constant.Message.getRandomEmoji;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final TelegramBot telegramBot;
    private final ButtonService buttonService;
    private final SessionService sessionService;

    @Override
    public void sendMenuMessage(Long chatId, String payload) {
        telegramBot.execute(
                new SendMessage(
                        chatId,
                        """
                        Start receiving anonymous messages right now 🚀
                        
                        Your link 👇
                        <blockquote>t.me/incaskbot?start=%s</blockquote>
                        
                        Put this link ☝️ in your Telegram/TikTok/Instagram profile description to start receiving anonymous messages 💬"""
                                .formatted(payload)
                )
                        .replyMarkup(buttonService.menuButtons(payload))
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
        );
    }

    @Override
    public void sendParamMenuMessage(Long chatId) {
        String message = """
                🚀 Here you can send an anonymous message to the person who posted this link.
                
                ✍️ Write here everything you want to convey to him, and in a few seconds he will receive your message, but he will not know from whom.
                
                You can send photos, videos, 💬 text, 🔊 voice, 📷 video messages (circles), and also ✨ stickers""";

        SendResponse response = telegramBot.execute(
                new SendMessage(
                        chatId,
                        message
                ).replyMarkup(buttonService.sendMessageButton())
                        .parseMode(ParseMode.HTML)
        );

        sessionService.updateLastMessageId(chatId, response.message().messageId());
    }

    @Override
    public void deleteMessage(Long chatId, Integer lastMessageId) {
        telegramBot.execute(new DeleteMessage(chatId, lastMessageId));
    }

    @Override
    public void sendIncognitoTextMessage(Session session, Message message) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                messageSendAnswerMenu(session.getChatId(), message.messageId(), session.getToMessageUrl());
                telegramBot.execute(
                        new SendMessage(
                                targetChatId,
                                getRandomEmoji() + "You have a new anonymous message!\n\n" +
                                        (message.text() != null && !message.text().isEmpty() ? message.text() + "\n\n" : "") +
                                        "↩️Swipe to reply."
                        ).parseMode(ParseMode.HTML)
                );
                sessionService.updateSessionState(session.getChatId(), State.MENU);
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoAudioMessage(Session session, Message message) {
        if (message.audio() == null) return;
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                messageSendAnswerMenu(session.getChatId(), message.messageId(), session.getToMessageUrl());
                telegramBot.execute(
                        new SendAudio(
                                targetChatId,
                                message.audio().fileId()
                        )
                                .caption(
                                        getRandomEmoji() + "️You have a new anonymous message!\n\n" +
                                                (message.caption() != null && !message.caption().isEmpty() ? message.caption() + "\n\n" : "") +
                                                "↩️Swipe to reply."
                                )
                                .parseMode(ParseMode.HTML)
                );
                sessionService.updateSessionState(session.getChatId(), State.MENU);
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoVideoMessage(Session session, Message message) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                messageSendAnswerMenu(session.getChatId(), message.messageId(), session.getToMessageUrl());
                telegramBot.execute(
                        new SendVideo(
                                targetChatId,
                                message.video().fileId()
                        )
                                .caption(
                                        getRandomEmoji() + "You have a new anonymous message!\n\n" +
                                                (message.caption() != null && !message.caption().isEmpty() ? message.caption() + "\n\n" : "") +
                                                "↩️Swipe to reply."
                                )
                                .parseMode(ParseMode.HTML)
                );
                sessionService.updateSessionState(session.getChatId(), State.MENU);
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoAnimationMessage(Session session, Message message) {
        Animation animation = message.animation();
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                messageSendAnswerMenu(session.getChatId(), message.messageId(), session.getToMessageUrl());
                telegramBot.execute(
                        new SendAnimation(
                                targetChatId,
                                animation.fileId()
                        )
                ); // Faqat animatsiya yuboriladi, qo'shimcha matn yo'q
                sessionService.updateSessionState(session.getChatId(), State.MENU);
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoVoiceMessage(Session session, Message message) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                messageSendAnswerMenu(session.getChatId(), message.messageId(), session.getToMessageUrl());
                telegramBot.execute(
                        new SendVoice(
                                targetChatId,
                                message.voice().fileId()
                        )
                                .caption(
                                        getRandomEmoji() + "️You have a new anonymous message!\n\n" +
                                                (message.caption() != null && !message.caption().isEmpty() ? message.caption() + "\n\n" : "") +
                                                "↩️Swipe to reply."
                                )
                                .parseMode(ParseMode.HTML)
                );
                sessionService.updateSessionState(session.getChatId(), State.MENU);
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoPhotoMessage(Session session, Message message) {
        PhotoSize[] photo = message.photo();
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                messageSendAnswerMenu(session.getChatId(), message.messageId(), session.getToMessageUrl());
                telegramBot.execute(
                        new SendPhoto(
                                targetChatId,
                                photo[photo.length - 1].fileId() // Eng yuqori sifatli rasm
                        )
                                .caption(
                                        getRandomEmoji() + "You have a new anonymous message!\n\n" +
                                                (message.caption().isEmpty() ? message.caption() + "\n\n" : "") +
                                                "↩️Swipe to reply."
                                )
                                .parseMode(ParseMode.HTML)
                );
                sessionService.updateSessionState(session.getChatId(), State.MENU);
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoVideoNoteMessage(Session session, Message message) {
        VideoNote videoNote = message.videoNote();
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                messageSendAnswerMenu(session.getChatId(), message.messageId(), session.getToMessageUrl());
                telegramBot.execute(
                        new SendVideoNote(
                                targetChatId,
                                videoNote.fileId()
                        )
                ); // Faqat video note yuboriladi, qo'shimcha matn yo'q
                sessionService.updateSessionState(session.getChatId(), State.MENU);
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoStickerMessage(Session session, Message message) {
        Sticker sticker = message.sticker();
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                messageSendAnswerMenu(session.getChatId(), message.messageId(), session.getToMessageUrl());
                telegramBot.execute(
                        new SendSticker(
                                targetChatId,
                                sticker.fileId()
                        )
                );
                /*telegramBot.execute(new SendMessage(
                        targetChatId,
                        """
                                You have a new anonymous message!
                                ↩️Swipe to reply."""
                ));*/
                sessionService.updateSessionState(session.getChatId(), State.MENU);
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoDocumentMessage(Session session, Message message) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                messageSendAnswerMenu(session.getChatId(), message.messageId(), session.getToMessageUrl());
                telegramBot.execute(
                        new SendDocument(
                                targetChatId,
                                message.document().fileId()
                        )
                                .caption(
                                        getRandomEmoji() + "You have a new anonymous message!\n\n" +
                                                (message.caption() != null && !message.caption().isEmpty() ? message.caption() + "\n\n" : "") +
                                                "↩️Swipe to reply."
                                )
                                .parseMode(ParseMode.HTML)
                );
                sessionService.updateSessionState(session.getChatId(), State.MENU);
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    private void messageSendAnswerMenu(Long chatId, Integer messageId, String param) {
        telegramBot.execute(
                new SendMessage(
                        chatId,
                        getRandomEmoji() + "Message sent, wait for a response!"
                ).replyToMessageId(messageId)
                        .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton("✍️Send more").callbackData("send_more_" + param)))
        );
    }
}