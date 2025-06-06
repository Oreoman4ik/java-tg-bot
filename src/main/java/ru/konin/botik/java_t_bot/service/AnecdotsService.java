package ru.konin.botik.java_t_bot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.konin.botik.java_t_bot.model.Anecdots;
import ru.konin.botik.java_t_bot.model.AnecdotSaveDTO;
import java.util.List;

public interface AnecdotsService {
    Anecdots addAnecdots(AnecdotSaveDTO anecdotSaveDTO);

    List<Anecdots> getAllAnecdots(String title);

    List<Anecdots> getTopAnecdots(int limit);

    void logAnecdotCall(Long userId, Anecdots anecdot);

    Page<Anecdots> getAnecdotsPage(String title, Pageable pageable);

    Anecdots getAnecdotsById(Long id);

    Anecdots editAnecdots(Long id, AnecdotSaveDTO anecdot);

    void deleteAnecdots(Long id);
}
