package ru.konin.botik.java_t_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.konin.botik.java_t_bot.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
