package org.exp.incaskbot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackQueryHandler implements Consumer<CallbackQuery> {

    @Override
    public void accept(CallbackQuery callbackQuery) {

    }
}
