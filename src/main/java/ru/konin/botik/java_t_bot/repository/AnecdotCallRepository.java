package ru.konin.botik.java_t_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.konin.botik.java_t_bot.model.AnecdotCall;

import java.util.List;

public interface AnecdotCallRepository extends JpaRepository<AnecdotCall, Long> {
    public interface AnecdotStats {
        Long getAnecdotId();
        Long getCount();

    }

    @Query("SELECT jc.anecdot.id as anecdotId, COUNT(jc) as count FROM anecdot_calls jc GROUP BY jc.anecdot.id ORDER BY count DESC")
    List<AnecdotStats> findTopAnecdot();
}
