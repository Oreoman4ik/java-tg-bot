package ru.konin.botik.java_t_bot.service;
import ru.konin.botik.java_t_bot.model.User;
import ru.konin.botik.java_t_bot.model.UserAuthority;

import java.util.List;

public interface UserService {
    User registerNewUser(String username, String rawPassword);

    List<UserAuthority> getUserRoles(Long userId);

    void addRoleToUser(Long userId, UserAuthority role);

    void removeRoleFromUser(Long userId, UserAuthority role);

    void replaceUserRole(Long userId, UserAuthority oldRole, UserAuthority newRole);
}