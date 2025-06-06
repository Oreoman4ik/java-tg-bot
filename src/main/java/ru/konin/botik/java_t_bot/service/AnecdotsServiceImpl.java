package ru.konin.botik.java_t_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.konin.botik.java_t_bot.model.AnecdotCall;
import ru.konin.botik.java_t_bot.model.Anecdots;
import ru.konin.botik.java_t_bot.model.AnecdotSaveDTO;
import ru.konin.botik.java_t_bot.repository.AnecdotCallRepository;
import ru.konin.botik.java_t_bot.repository.AnecdotsRepository;
import ru.konin.botik.java_t_bot.service.AnecdotsService;
import ru.konin.botik.java_t_bot.exceptions.AnecdotsNotFoundExceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class AnecdotsServiceImpl implements AnecdotsService {

    private final AnecdotsRepository anecdotsRepository;

    private final AnecdotCallRepository anecdotCallRepository;

    public void logAnecdotCall(Long userId, Anecdots anecdot) {
        AnecdotCall call = new AnecdotCall(userId, LocalDateTime.now(), anecdot);
        anecdotCallRepository.save(call);
    }

    public Page<Anecdots> getAnecdotsPage(String title, Pageable pageable) {
        if (title != null && !title.isEmpty()) {
            return anecdotsRepository.findByTitleContainingIgnoreCase(title, pageable);
        }
        return anecdotsRepository.findAll(pageable);
    }

    public List<Anecdots> getTopAnecdots(int limit) {
        List<AnecdotCallRepository.AnecdotStats> stats = anecdotCallRepository.findTopAnecdot();
        List<Long> anecdotIds = stats.stream()
                .map(AnecdotCallRepository.AnecdotStats::getAnecdotId)
                .limit(limit)
                .collect(Collectors.toList());
        return StreamSupport.stream(anecdotsRepository.findAllById(anecdotIds).spliterator(), false)
                .collect(Collectors.toList());

    }

    public Anecdots addAnecdots(AnecdotSaveDTO anecdot) {
        Anecdots anecdot1 = new Anecdots(anecdot.getTitle(), anecdot.getContent());
        return anecdotsRepository.save(anecdot1);
    }

    public List<Anecdots> getAllAnecdots(String title) {
        if (title != null) {
            return anecdotsRepository.findByTitle(title);
        }
        return (List<Anecdots>) anecdotsRepository.findAll();
    }

    public Anecdots getAnecdotsById(Long id) {
        return getAnecdotsOrThrowExcep(id);
    }

    private Anecdots getAnecdotsOrThrowExcep(Long id) {
        return anecdotsRepository.findById(id).orElseThrow(() -> new AnecdotsNotFoundExceptions(id));
    }

    public Anecdots editAnecdots(Long id, AnecdotSaveDTO anecdot) {
        Anecdots anecdot1 = getAnecdotsOrThrowExcep(id);
        anecdot1.setTitle(anecdot.getTitle());
        anecdot1.setContent(anecdot.getContent());

        return anecdotsRepository.save(anecdot1);
    }

    public void deleteAnecdots(Long id) {
        if (!anecdotsRepository.existsById(id)) {
            throw new AnecdotsNotFoundExceptions(id);
        }
        anecdotsRepository.deleteById(id);
    }
}
