package ru.konin.botik.java_t_bot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "anecdots")
@Table(name = "anecdots")
public class Anecdots {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "anecdots_seq_gen")
    @SequenceGenerator(name = "anecdots_seq_gen", sequenceName = "anecdots_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public Anecdots(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
