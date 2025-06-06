package ru.konin.botik.java_t_bot.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.konin.botik.java_t_bot.model.Anecdots;

import java.util.List;

public interface AnecdotsRepository extends CrudRepository<Anecdots, Long> {
    List<Anecdots> findByTitle(String title);

    Page<Anecdots> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Anecdots> findAll(Pageable pageable);
}
