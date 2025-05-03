package ru.konin.botik.java_t_bot.service;

import ru.konin.botik.java_t_bot.model.Anecdots;
import java.util.List;

public interface AnecdotsService {
    public Anecdots addAnecdots(Anecdots anecdot);
    public List<Anecdots> getAllAnecdots(String title);


    public Anecdots getAnecdotsById(Long id);

    public Void editAnecdots(Long id, Anecdots anecdot);

    public Void deleteAnecdots(Long id);
}
