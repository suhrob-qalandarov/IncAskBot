package org.exp.incaskbot.service.face;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import org.springframework.stereotype.Service;

@Service
public interface ButtonService {
    InlineKeyboardMarkup menuButtons(String payload);
    InlineKeyboardMarkup sendMessageButton();

    Keyboard blockIncUserBtn(Long chatId);
}
