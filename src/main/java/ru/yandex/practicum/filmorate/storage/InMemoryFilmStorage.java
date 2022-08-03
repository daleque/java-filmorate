package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    int filmNumberID = 0;

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        validateFilm(film);
        film.setId(++filmNumberID);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {

        if (!(films.containsKey(film.getId()))) {
            String errorMsg = String.format("Отсутствует фильм с id=%s", film.getId());
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        validateFilm(film);
        films.put(film.getId(), film);
        return film;
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

    public void likeFilm(int filmId, int userId) {
        if(!(films.containsKey(filmId))) {
            String errorMsg = String.format("Отсутствует фильм с id=%s", filmId);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        Film film = films.get(filmId);
        film.setLikes(userId);
    }


    public void deleteLike(int filmId, int userId) {
        if(!(films.containsKey(filmId))) {
            String errorMsg = String.format("Отсутствует фильм с id=%s", filmId);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        if(filmId < 0) {
            String errorMsg = String.format("Некорректный id=%s", filmId);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        if(userId < 0) {
            String errorMsg = String.format("Некорректный id=%s", filmId);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        Film film = films.get(filmId);
        film.deleteLikesFromUsers(userId);
    }

    public Film get(int filmId) {
        if(!(films.containsKey(filmId))) {
            String errorMsg = String.format("Отсутствует фильм с id=%s", filmId);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }
        if(filmId < 0) {
            String errorMsg = String.format("Некорректный id=%s", filmId);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }
        return films.get(filmId);
    }
}
