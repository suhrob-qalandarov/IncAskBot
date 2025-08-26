package org.exp.incaskbot.service.impl;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.exp.incaskbot.service.face.ButtonService;
import org.springframework.stereotype.Service;

@Service
public class ButtonServiceImpl implements ButtonService {
    @Override
    public InlineKeyboardMarkup menuButtons(String payload) {
        String message = "Using this link you can send me an\n**anonymous message**:\n" +
                "👉 t.me/incaskbot?start=" + payload;

        return new InlineKeyboardMarkup(
                new InlineKeyboardButton(" Share link").url("tg://msg_url?url=" + message)
        );
    }

    @Override
    public InlineKeyboardMarkup sendMessageButton() {
        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("✖️Cancel").callbackData("delete_message_wait")
        );
    }

}
