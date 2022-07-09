package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmControllerTest {

    FilmController filmController = new FilmController();

    @Test
    void shouldCreateAndFindAll() {
        List<Film> films = filmController.findAll();

        assertTrue(films.isEmpty());

        Film film1 = new Film(1L,
                "FilmName1",
                "Film1 description",
                LocalDate.of(2000, 3, 12),
                Duration.between(LocalTime.of(10, 10), LocalTime.of(12, 12))
                );
        Film film2 = new Film(2L,
                "FilmName2",
                "Film2 description",
                LocalDate.of(2003, 5, 10),
                Duration.between(LocalTime.of(13, 30), LocalTime.of(17, 50))
        );

        filmController.create(film1);
        filmController.create(film2);

        assertEquals(2, films.size());
    }
}