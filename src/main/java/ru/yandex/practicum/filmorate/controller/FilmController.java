package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private List<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return films;
    }

    @PostMapping(value = "/film")
    public Film create(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту user, метод POST");

        try {
            films.add(film);
            if (film.getFilmName().equals("")) {
                throw new ValidationException("FilmName can not be empty!");
            } else if (film.getFilmDescription().length() > 200) {
                throw new ValidationException("FilmDescription is too long");
            } else if (film.getFilmReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                throw new ValidationException("FilmReleaseDate can not be earlier than 28.12.1895");
            } else if (film.getFilmDuration().isNegative()) {
                throw new ValidationException("FilmDuration should be positive");
            }
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    @PutMapping("/{filmId}")
    public Film updateUser(@PathVariable Long filmId, @RequestBody Film film)    {
        log.info("Получен запрос к эндпоинту" + filmId + ", метод PUT");

        try {
            films.add(film);
            if (film.getFilmName().equals("")) {
                throw new ValidationException("FilmName can not be empty!");
            } else if (film.getFilmDescription().length() > 200) {
                throw new ValidationException("FilmDescription is too long");
            } else if (film.getFilmReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                throw new ValidationException("FilmReleaseDate can not be earlier than 28.12.1895");
            } else if (film.getFilmDuration().isNegative()) {
                throw new ValidationException("FilmDuration should be positive");
            }
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        return film;
    }
}
