package ru.konin.botik.java_t_bot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.konin.botik.java_t_bot.model.Anecdots;
import ru.konin.botik.java_t_bot.service.AnecdotsService;
import ru.konin.botik.java_t_bot.service.AnecdotsServiceImpl;

import java.util.List;

@RequestMapping("/api/anecdots")
@RestController
public class AnecdotsController {

    private final AnecdotsService anecdotsService;

    @Autowired
    public AnecdotsController(AnecdotsServiceImpl anecdotsService) {
        this.anecdotsService = anecdotsService;
    }

    @PostMapping
    public ResponseEntity<Anecdots> addAnecdot(@RequestBody  Anecdots anecdot){
        Anecdots saved = anecdotsService.addAnecdots(anecdot);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);}

    @GetMapping
    public ResponseEntity<List<Anecdots>> getAllAnecdots(@RequestParam(value = "title", required = false) String title) {
        List<Anecdots> anecdots = anecdotsService.getAllAnecdots(title);
        return ResponseEntity.ok(anecdots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anecdots> getAnecdotsById(@PathVariable("id") Long id) {
        Anecdots anecdot = anecdotsService.getAnecdotsById(id);
        return ResponseEntity.ok(anecdot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editAnecdots(@PathVariable("id") Long id, @RequestBody Anecdots anecdot) {
        anecdotsService.editAnecdots(id, anecdot);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnecdots(@PathVariable("id") Long id) {
        anecdotsService.deleteAnecdots(id);
        return ResponseEntity.ok().build();
    }

}
