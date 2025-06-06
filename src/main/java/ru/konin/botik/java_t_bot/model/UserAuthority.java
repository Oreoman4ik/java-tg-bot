package ru.konin.botik.java_t_bot.model;


import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    USER,
    MODERATOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
