package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;

@Service
public class FilmService {
    @Autowired
    InMemoryFilmStorage inMemoryFilmStorage;
    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film createFilm(Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    public Film getFilm(int filmId) {
        return inMemoryFilmStorage.get(filmId);
    }

    public void likeFilm(int filmId, int userId) {
        inMemoryFilmStorage.likeFilm(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        inMemoryFilmStorage.deleteLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        return inMemoryFilmStorage.getCountPopularFilms(count);
    }
}
