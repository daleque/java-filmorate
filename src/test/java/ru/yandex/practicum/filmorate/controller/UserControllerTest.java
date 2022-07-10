package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController = new UserController();

    @Test
    void shouldCreateAndFindAll() {
        List<User> users = userController.getAllUsers();

        assertTrue(users.isEmpty());

        User user1 = new User(1L,
                "userLogin1@mail.ru",
                "userLogin1",
                " ",
                LocalDate.of(1987, 11, 13)
                );
        User user2 = new User(2L,
                "userLogin2@mail.ru",
                "userLogin2",
                "CattyParry",
                LocalDate.of(1991, 10, 2)
        );

        userController.create(user1);
        userController.create(user2);

        assertEquals(2, users.size());
    }
}