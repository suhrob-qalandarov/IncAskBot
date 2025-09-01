package org.exp.incaskbot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.exp.incaskbot.config.audit.TelegramContext;
import org.exp.incaskbot.model.entity.Session;
import org.exp.incaskbot.model.enums.State;
import org.exp.incaskbot.service.face.BotMessageService;
import org.exp.incaskbot.service.face.SessionService;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackQueryHandler implements Consumer<CallbackQuery> {

    private final SessionService sessionService;
    private final BotMessageService botMessageService;
    private final TelegramBot telegramBot;

    @Override
    public void accept(CallbackQuery callbackQuery) {
        try {
            String data = callbackQuery.data();
            Session session = sessionService.getOrCreateSession(callbackQuery.from());

            if (data.equals("delete_message_wait") && session.getState().equals(State.MESSAGE)) {
                botMessageService.deleteMessage(session.getChatId(), session.getLastMessageId());
                sessionService.updateSessionState(session.getChatId(), State.MENU);
                botMessageService.sendMenuMessage(session.getChatId(), session.getContactUri());
                return;

            } else if (data.startsWith("send_more_")) {
            /*String[] split = data.split("_");
            String param = split[2];
            String param2 = split[3];
            if (param2 != null) param = param + "_" + param2;*/
                String param = data.substring(10);
                if (param.isEmpty()) {
                    log.error("Param empty: {}", data);
                    return;
                }
                botMessageService.sendParamMenuMessage(session.getChatId());
                sessionService.updateUserSessionParam(session.getChatId(), param);
                return;

            } else if (data.startsWith("block_inc_")) {
                telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id())
                        .text("⚠️ Warning: This action is not available!")
                        .showAlert(true));
                return;

            } else {
                botMessageService.sendMenuMessage(session.getChatId(), session.getContactUri());
                log.info("Unknown callback query data={}", data);
                return;
            }
        } catch (Exception e) {
            log.error("Error processing callback query: {}", e.getMessage());
        } finally {
            TelegramContext.clear();
        }
    }
}
