package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> getAllFilms();
    Film createFilm(Film film);
    Film updateFilm(Film film);
    void likeFilm(int filmId, int userId);
    void deleteLike(int filmId, int userId);
    Film get(int filmId);
}
