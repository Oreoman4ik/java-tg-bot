package ru.konin.botik.java_t_bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.konin.botik.java_t_bot.exceptions.ExceptionRespone;
import ru.konin.botik.java_t_bot.exceptions.AnecdotsNotFoundExceptions;

@ControllerAdvice
public class AnecdotsExceptionsHandler {

    @ExceptionHandler(AnecdotsNotFoundExceptions.class)
    public ResponseEntity<ExceptionRespone> handleAnecdotsNotFound (AnecdotsNotFoundExceptions exception) {

        System.out.println("Anecdot not found with ID: " + exception.getId());
        return ResponseEntity.notFound().build();
    }
}
