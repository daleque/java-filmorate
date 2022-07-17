package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    int filmNumberID = 0;

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту films, метод POST");

        validateFilm(film);
        film.setId(++filmNumberID);
        saveFilm(film);
        return film;
    }

    @PutMapping
    public Film updateUser(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту films, метод PUT");

        if (!films.containsKey(film.getId())) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", film.getId());
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        validateFilm(film);
        saveFilm(film);
        return film;
    }

    private void saveFilm(Film film) {
        films.put(film.getId(), film);
    }

    private void validateFilm(Film film) {
        if (film.getName().isEmpty()) {
            throw new ValidationException("Invalid film name");
        }
        if (film.getName().isBlank()) {
            throw new ValidationException("Invalid film name");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Invalid film description");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Invalid film release date");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Invalid film duration");
        }
    }
}
