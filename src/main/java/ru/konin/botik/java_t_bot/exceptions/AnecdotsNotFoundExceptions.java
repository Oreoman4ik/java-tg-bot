package ru.konin.botik.java_t_bot.exceptions;

import lombok.Getter;

@Getter
public class AnecdotsNotFoundExceptions extends RuntimeException {

    private final Long id;

    public AnecdotsNotFoundExceptions(Long id) {
        super("Anecdots not found: " + id);
        this.id=id;
    }

    public Long getId() {
        return id;
    }
}
