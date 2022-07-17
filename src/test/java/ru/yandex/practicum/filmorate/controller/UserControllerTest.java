package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserControllerTest {
    UserController userController = new UserController();

   @Test
    void shouldCreateAndFindAll() {
       Map<Long, User> users = new HashMap<>();
       List<User> listOfUsers = userController.getAllUsers();
        User user1 = new User(
                "userLogin1@mail.ru",
                "userLogin1",
                " ",
                LocalDate.of(1987, 11, 13)
                );
        User user2 = new User(
                "userLogin2@mail.ru",
                "userLogin2",
                "CattyParry",
                LocalDate.of(1991, 10, 2)
        );

        userController.createUser(user1);
        userController.createUser(user2);

       // assertEquals(2, users.size());
    }

    @Test
    void createUser() {
        Map<Long, User> users = new HashMap<>();
        List<User> listOfUsers = userController.getAllUsers();
        User user1 = new User("mail@mail.ru",
                "dolore",
                "Nick Name",
                LocalDate.of(1946,8, 20)
        );

        userController.createUser(user1);

        assertEquals(1, listOfUsers.size());

    }
}