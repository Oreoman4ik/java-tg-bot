package ru.konin.botik.java_t_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.konin.botik.java_t_bot.model.Anecdots;

import java.util.List;

@Component
public class TelegramUpdateListener implements UpdatesListener {

    private final AnecdotsService anecdotsService;
    private final TelegramBot telegramBot;

    public TelegramUpdateListener(AnecdotsService anecdotsService, TelegramBot telegramBot) {
        this.anecdotsService = anecdotsService;
        this.telegramBot = telegramBot;
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                Long chatId = update.message().chat().id();

                if (messageText.equals("/start")) {
                    telegramBot.execute(new SendMessage(chatId,
                            "Доступные команды:\n/random_anecdot - случайный анекдот\n/all_anecdots - все анекдоты"));
                }

                if (update.message().text().equals("/random_anecdot")) {
                    List<Anecdots> anecdots = anecdotsService.getAllAnecdots(null);

                    if (!anecdots.isEmpty()) {
                        Anecdots anecdot = anecdots.get((int) (Math.random() * anecdots.size()));
                        String text = '"' + anecdot.getTitle() + '"' +  "\n" + anecdot.getContent();
                        telegramBot.execute(new SendMessage(update.message().chat().id(), text));
                    }else {telegramBot.execute(new SendMessage(update.message().chat().id(), "Мемов нет"));}
                }
                if (update.message().text().equals("/all_anecdots")) {
                    List<Anecdots> anecdots = anecdotsService.getAllAnecdots(null);
                    String text = "";
                    if (!anecdots.isEmpty()) {
                        for (int i = 0; i < anecdots.size(); i++) {
                            text = '"' + anecdots.get(i).getTitle() + '"'  + "\n" + anecdots.get(i).getContent()+ "\n\n";
                            telegramBot.execute(new SendMessage(update.message().chat().id(), text));
                        }
                    }
                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        telegramBot.setUpdatesListener(this);
    }
}