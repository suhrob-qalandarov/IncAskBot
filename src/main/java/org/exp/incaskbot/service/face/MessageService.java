package org.exp.incaskbot.service.face;

import com.pengrad.telegrambot.model.*;
import org.exp.incaskbot.model.entity.Session;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
    void sendMenuMessage(Long chatId, String url);
    void sendParamMenuMessage(Long chatId);
    void deleteMessage(Long chatId, Integer lastMessageId);
    void sendIncognitoTextMessage(Session session, String text);
    void sendIncognitoAudioMessage(Session session, Audio audio);
    void sendIncognitoVideoMessage(Session session, Video video);
    void sendIncognitoVoiceMessage(Session session, Voice voice);
    void sendIncognitoPhotoMessage(Session session, PhotoSize[] photo);
    void sendIncognitoAnimationMessage(Session session, Animation animation);
    void sendIncognitoVideoNoteMessage(Session session, VideoNote videoNote);
    void sendIncognitoStickerMessage(Session session, Sticker sticker);
}
