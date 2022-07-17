package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    int filmNumberID = 0;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/films")
    public Map<Integer, Film> getAllFilms() {
        return films;
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту films, метод POST");

        validateFilm(film);
        film.setId(++filmNumberID);
        saveFilm(film);
        return film;
    }

    @PutMapping("/films")
    public Film updateUser(@RequestBody Film film)    {
        log.info("Получен запрос к эндпоинту films, метод PUT");

        validateFilm(film);
        saveFilm(film);
        return film;
    }

    private void saveFilm(Film film) {
        films.put(film.getId(), film);
    }

    private void validateFilm(Film film) {
        if (film.getName().isEmpty()) {
            throw new  RuntimeException("Invalid film name");
        }
        if (film.getName().isBlank()) {
            throw new  RuntimeException("Invalid film name");
        }
        if (film.getDescription().length() > 200) {
            throw new  RuntimeException("Invalid film description");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new  RuntimeException("Invalid film release date");
        }
        if (film.getDuration() < 0) {
            throw new  RuntimeException("Invalid film duration");
        }
    }
}
