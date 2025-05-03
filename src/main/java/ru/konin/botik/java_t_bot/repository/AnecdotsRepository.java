package ru.konin.botik.java_t_bot.repository;
import org.springframework.data.repository.CrudRepository;
import ru.konin.botik.java_t_bot.model.Anecdots;

public interface AnecdotsRepository extends CrudRepository<Anecdots, Long> {
}
