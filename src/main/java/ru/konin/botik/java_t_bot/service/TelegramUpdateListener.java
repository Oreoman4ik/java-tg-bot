package ru.konin.botik.java_t_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.konin.botik.java_t_bot.model.Anecdots;
import ru.konin.botik.java_t_bot.model.AnecdotSaveDTO;

import java.util.List;

@Log4j2
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
            log.info("Получен апдейт: {}", update);
            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                Long chatId = update.message().chat().id();

                if (messageText.equals("/start")) {
                    telegramBot.execute(new SendMessage(chatId,
                            "Доступные команды:\n/random_anecdot - случайный анекдот\n/all_anecdots - все анекдоты\n /anecdotpage - страница анекдотов"));
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
                if (messageText.startsWith("/anecdotpage")) {
                    String[] parts = messageText.split(" ");
                    int pageNumber = 0;
                    int pageSize = 5;

                    if (parts.length >= 2) {
                        try {
                            pageNumber = Integer.parseInt(parts[1]) - 1;
                            if (pageNumber < 0) pageNumber = 0;
                        } catch (NumberFormatException e) {
                            telegramBot.execute(new SendMessage(chatId, "Неверный номер страницы. Используйте: /anecdotpage 1"));
                            return UpdatesListener.CONFIRMED_UPDATES_ALL;
                        }
                    }

                    Pageable pageable = PageRequest.of(pageNumber, pageSize);
                    Page<Anecdots> anecdotPage = anecdotsService.getAnecdotsPage(null, pageable);

                    if (anecdotPage.hasContent()) {
                        StringBuilder builder = new StringBuilder("Анекдоты (страница " + (pageNumber + 1) + " из " + anecdotPage.getTotalPages() + "):\n\n");
                        for (Anecdots anecdot : anecdotPage.getContent()) {
                            builder.append("• ").append(anecdot.getTitle()).append("\n")
                                    .append(anecdot.getContent()).append("\n\n\n\n\n\n");
                        }
                        telegramBot.execute(new SendMessage(chatId, builder.toString()));
                    } else {
                        telegramBot.execute(new SendMessage(chatId, "Анекдотов не найдено на этой странице"));
                    }
                    if (messageText.equals("/top_anecdots")) {
                        List<Anecdots> topAnecdots = anecdotsService.getTopAnecdots(5);
                        StringBuilder builder = new StringBuilder("Топ-5 самых популярных анекдотов:\n");
                        for (int i = 0; i < topAnecdots.size(); i++) {
                            Anecdots anecdot = topAnecdots.get(i);
                            builder.append(i + 1)
                                    .append(". ")
                                    .append(anecdot.getTitle())
                                    .append("\n");
                        }
                        telegramBot.execute(new SendMessage(chatId, builder.toString()));
                    }

                    if (update.message().text().startsWith("/add")) {
                        String[] parts1 = update.message().text().split(" ", 3);

                        if (parts1.length < 3) {
                            telegramBot.execute(new SendMessage(update.message().chat().id(), "Ошибка: недостаточно аргументов."));
                            return UpdatesListener.CONFIRMED_UPDATES_ALL;
                        }

                        String command = parts1[0];
                        String title = parts1[1];
                        String content = parts1[2];

                        AnecdotSaveDTO anecdots = new AnecdotSaveDTO();
                        anecdots.setTitle(title);
                        anecdots.setContent(content);
                        anecdotsService.addAnecdots(anecdots);
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Шутка добавлена!"));


                        System.out.println("Команда: " + command);
                        System.out.println("Тайтл: " + title);
                        System.out.println("Контент: " + content);
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