package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FilmControllerTest {

    FilmController filmController = new FilmController();

   @Test
    void shouldCreateAndFindAll() {
        Map<Integer, Film> films = filmController.getAllFilms();

        assertTrue(films.isEmpty());

        Film film1 = new Film(
                "FilmName1",
                "Film1 description",
                LocalDate.of(2000, 3, 12),
                3
                );
        Film film2 = new Film(
                "FilmName2",
                "Film2 description",
                LocalDate.of(2003, 5, 10),
                4
        );

        filmController.createFilm(film1);
        filmController.createFilm(film2);

        assertEquals(2, films.size());
    }

}