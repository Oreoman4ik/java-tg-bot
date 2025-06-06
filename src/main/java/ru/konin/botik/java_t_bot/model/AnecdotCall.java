package ru.konin.botik.java_t_bot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "anecdot_calls")
@Table(name = "anecdot_calls")
public class AnecdotCall {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "anecdot_calls_seq_gen")
    @SequenceGenerator(name = "anecdot_calls_seq_gen", sequenceName = "anecdot_calls_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "call_time", nullable = false)
    private LocalDateTime callTime;

    @ManyToOne
    @JoinColumn(name = "anecdot_id", nullable = false)
    private Anecdots anecdot;

    public AnecdotCall(Long userId, LocalDateTime callTime, Anecdots anecdot) {
        this.userId = userId;
        this.callTime = callTime;
        this.anecdot = anecdot;
    }
}
