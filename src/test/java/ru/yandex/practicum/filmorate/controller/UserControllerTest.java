package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    /*@Test
    void validateUserWithoutEmail() {
        UserController userController = new UserController();
        User user = new User("era@mail.com",
                "Login",
                "Name",
                LocalDate.of(1999, 1, 13));
        userController.createUser(user);


    }

    /*@Test
    void validateUserWithBlankEmail() {
        UserController userController = new UserController();
        User user = new User("     ",
                "Login",
                "Name",
                LocalDate.of(1999, 1, 13));
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });
    }

    @Test
    void validateUserWithWrongEmail() {
        UserController userController = new UserController();
        User user = new User("tytytyty.com",
                "Login",
                "Name",
                LocalDate.of(1999, 1, 13));
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });
    }

    @Test
    void validateUserWithWrongLogin() {
        UserController userController = new UserController();
        User user = new User("tytytyty@gmail.com",
                "Login with blank",
                "Name",
                LocalDate.of(1999, 1, 13));
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });
    }

    @Test
    void validateUserWithoutLogin() {
        UserController userController = new UserController();
        User user = new User("tytytyty@gmail.com",
                null,
                "Name",
                LocalDate.of(1999, 1, 13));
        Assertions.assertThrows(NullPointerException.class, () -> {
            userController.validateUser(user);
        });
    }

    @Test
    void validateUserWitBlankLogin() {
        UserController userController = new UserController();
        User user = new User("tytytyty@gmail.com",
                "     ",
                "Name",
                LocalDate.of(1999, 1, 13));
        Assertions.assertThrows(RuntimeException.class, () -> {
            userController.validateUser(user);
        });
    }*/
}