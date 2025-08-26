package org.exp.incaskbot.service.face;

import com.pengrad.telegrambot.model.*;
import org.exp.incaskbot.model.entity.Session;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
    void sendMenuMessage(Long chatId, String url);
    void sendParamMenuMessage(Long chatId);
    void deleteMessage(Long chatId, Integer lastMessageId);
    void sendIncognitoTextMessage(Session session, Message text);
    void sendIncognitoAudioMessage(Session session, Message audio);
    void sendIncognitoVideoMessage(Session session, Message video);
    void sendIncognitoVoiceMessage(Session session, Message massage);
    void sendIncognitoPhotoMessage(Session session, Message message);
    void sendIncognitoAnimationMessage(Session session, Message animation);
    void sendIncognitoVideoNoteMessage(Session session, Message videoNote);
    void sendIncognitoStickerMessage(Session session, Message message);
    void sendIncognitoDocumentMessage(Session session, Message message);
}
