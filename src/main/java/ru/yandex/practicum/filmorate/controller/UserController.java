package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    int usersID = 0;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        for(User user : users.values()) {
            listOfUsers.add(user);
        }
        return listOfUsers;
    }

    @PostMapping("/users")
    public User createUser (@RequestBody User user) {
        log.info("Получен запрос к эндпоинту users, метод POST");
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        validateUser(user);
        user.setId(++usersID);
        saveUser(user);

        return user;
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        log.info("Получен запрос к эндпоинту users, метод PUT");

        validateUser(user);
        saveUser(user);

    }

    private void validateUser (User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new  RuntimeException("Invalid birthday");
        }
        if (!(user.getEmail().contains("@"))) {
            throw new  RuntimeException("Invalid e-mail");
        }
        if (user.getEmail().isEmpty()) {
            throw new  RuntimeException("Invalid e-mail");
        }
        if (user.getEmail().isBlank()) {
            throw new  RuntimeException("Invalid e-mail");
        }
        if (user.getLogin().isEmpty()) {
            throw new  RuntimeException("Invalid login");
        }
        if (user.getLogin().isBlank()) {
            throw new  RuntimeException("Invalid login");
        }
        if (user.getLogin().contains(" ")) {
            throw new  RuntimeException("Invalid login");
        }
    }

    private void saveUser (User user) {
        users.put(user.getId(), user);
    }

}
