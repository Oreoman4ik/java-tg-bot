package ru.konin.botik.java_t_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.konin.botik.java_t_bot.exceptions.AnecdotsNotFoundExceptions;
import ru.konin.botik.java_t_bot.model.Anecdots;

import ru.konin.botik.java_t_bot.repository.AnecdotsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//@RequiredArgsConstructor
@Service
public class AnecdotsServiceImpl implements AnecdotsService {

    private final AnecdotsRepository anecdotsRepository;

    @Autowired
    public AnecdotsServiceImpl(AnecdotsRepository anecdotsRepository) {
        this.anecdotsRepository = anecdotsRepository;
    }

    public Anecdots addAnecdots(Anecdots anecdot) {
        return anecdotsRepository.save(anecdot);
    }

    public List<Anecdots> getAllAnecdots(String title) {
        if (title != null) {
            return StreamSupport.stream(anecdotsRepository.findAll().spliterator(), false)
                    .filter(anecdot -> title.equals(anecdot.getTitle()))
                    .collect(Collectors.toList());
        } else {
            return (List<Anecdots>) anecdotsRepository.findAll();
        }
    }

    public Anecdots getAnecdotsById(Long id) {
        Optional<Anecdots> anecdots = anecdotsRepository.findById(id);
        if (anecdots.isPresent()){
            return anecdots.get();
        }
        else {
            throw new AnecdotsNotFoundExceptions(id) ;
        }
    }

    public Void editAnecdots(Long id, Anecdots anecdot) {
        if (!anecdotsRepository.existsById(id)) {
            throw new AnecdotsNotFoundExceptions(id);
        }
        anecdot.setId(id);
        anecdotsRepository.save(anecdot);
        return null;
    }

    public Void deleteAnecdots(Long id) {
        if (!anecdotsRepository.existsById(id)) {
            throw new AnecdotsNotFoundExceptions(id);
        }
        anecdotsRepository.deleteById(id);
        return null;
    }
}
