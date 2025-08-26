package org.exp.incaskbot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exp.incaskbot.model.Session;
import org.exp.incaskbot.service.face.ButtonService;
import org.exp.incaskbot.service.face.SessionService;
import org.springframework.stereotype.Service;
import org.exp.incaskbot.service.face.MessageService;

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
    public void sendIncognitoTextMessage(Session session, String text) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                telegramBot.execute(
                        new SendMessage(
                                targetChatId,
                                text
                        )
                                .parseMode(ParseMode.HTML)
                );
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoAudioMessage(Session session, Audio audio) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                telegramBot.execute(
                        new SendAudio(
                                targetChatId,
                                audio.fileId()
                        )
                                .caption("Anonim audio xabar")
                                .parseMode(ParseMode.HTML)
                );
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoVideoMessage(Session session, Video video) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                telegramBot.execute(
                        new SendVideo(
                                targetChatId,
                                video.fileId()
                        )
                );
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoAnimationMessage(Session session, Animation animation) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                telegramBot.execute(
                        new SendAnimation(
                                targetChatId,
                                animation.fileId()
                        )
                );
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoVoiceMessage(Session session, Voice voice) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                telegramBot.execute(
                        new SendVoice(
                                targetChatId,
                                voice.fileId()
                        )
                );
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoPhotoMessage(Session session, PhotoSize[] photo) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                telegramBot.execute(
                        new SendPhoto(
                                targetChatId,
                                photo[photo.length - 1].fileId() // Eng yuqori sifatli rasm
                        )
                                .caption("Anonim rasm xabar")
                                .parseMode(ParseMode.HTML)
                );
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoVideoNoteMessage(Session session, VideoNote videoNote) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                telegramBot.execute(
                        new SendVideoNote(
                                targetChatId,
                                videoNote.fileId()
                        )
                );
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }

    @Override
    public void sendIncognitoStickerMessage(Session session, Sticker sticker) {
        String targetUrl = session.getToMessageUrl();
        if (targetUrl != null) {
            Long targetChatId = sessionService.getChatIdByUrl(targetUrl);
            if (targetChatId != null) {
                telegramBot.execute(
                        new SendSticker(
                                targetChatId,
                                sticker.fileId()
                        )
                );
            } else {
                log.warn("Target chat ID not found for URL: {}", targetUrl);
            }
        } else {
            log.warn("toMessageUrl is null for session: {}", session.getChatId());
        }
    }
}
