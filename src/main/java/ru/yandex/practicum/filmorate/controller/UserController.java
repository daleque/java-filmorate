package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users;
    }

    @PostMapping(value = "/user")
    public User create(@RequestBody User user) {
        log.info("Получен запрос к эндпоинту user, метод POST");

        try {
            users.add(user);
            if (!(user.getUserEmail().contains("@"))) {
                throw new ValidationException("Email is not correct");
            } else if ((user.getUserLogin().equals("")) || (user.getUserLogin().contains(" "))) {
                throw new ValidationException("UserLogin can not be empty or contain white space!");
            } else if (user.getUserName().isEmpty()) {
                user.setUserName(user.getUserLogin());
            } else if (user.getUserBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Date of birth can not be later than today's date");
            }
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user)    {
        log.info("Получен запрос к эндпоинту" + userId + ", метод PUT");

        try {
            users.add(user);
            if (!(user.getUserEmail().contains("@"))) {
                throw new ValidationException("Email is not correct");
            } else if ((user.getUserLogin().equals("")) || (user.getUserLogin().contains(" "))) {
                throw new ValidationException("UserLogin can not be empty or contain white space!");
            } else if (user.getUserName().isEmpty()) {
                user.setUserName(user.getUserLogin());
            } else if (user.getUserBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Date of birth can not be later than today's date");
            }
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
