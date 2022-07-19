package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    int usersID = 0;

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту users, метод POST");
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        validateUser(user);
        user.setId(++usersID);
        saveUser(user);

        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту users, метод PUT");

        if (!users.containsKey(user.getId())) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", user.getId());
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        validateUser(user);
        saveUser(user);
        return user;
    }

    void validateUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Invalid birthday");
        }
        if (!(user.getEmail().contains("@"))) {
            throw new ValidationException("Invalid e-mail");
        }
        if (user.getEmail().isEmpty()) {
            throw new ValidationException("Invalid e-mail");
        }
        if (user.getEmail().isBlank()) {
            throw new ValidationException("Invalid e-mail");
        }
        if (user.getLogin().isEmpty()) {
            throw new ValidationException("Invalid login");
        }
        if (user.getLogin().isBlank()) {
            throw new RuntimeException("Invalid login");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Invalid login");
        }
    }

    private void saveUser(User user) {
        users.put(user.getId(), user);
    }
}
