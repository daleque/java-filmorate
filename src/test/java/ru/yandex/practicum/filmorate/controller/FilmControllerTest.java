package ru.yandex.practicum.filmorate.controller;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

   @Test
    void validateFilmWithoutName() {
        InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();

        Film film = new Film("Побег из Шоушенка",
                "Описание",
                LocalDate.of(1993, 11, 2),
                190,
                5);
        inMemoryFilmStorage.createFilm(film);

        Film film2 = new Film("Побег из Шоушенка2",
                "Описание",
                LocalDate.of(1993, 11, 2),
                190,
                3);
        inMemoryFilmStorage.createFilm(film2);

        Film film3 = new Film("Побег из Шоушенка3",
                "Описание",
                LocalDate.of(1993, 11, 2),
                190,
                2);
        inMemoryFilmStorage.createFilm(film3);

        List<Film> rating = inMemoryFilmStorage.getCountPopularFilms(2);

        for(Film film333 : rating) {
            System.out.println(film333.getName());
        }
    }

}