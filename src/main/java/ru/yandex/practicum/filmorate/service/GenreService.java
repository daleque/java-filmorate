package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDbStorage genreDbStorage;

    public Genre findById(int id) {
        final Genre genre = genreDbStorage.findById(id);

        if (Objects.isNull(genre)) {
            throw new NotFoundException(String.format("Genre with id=%d not found!", id));
        }
        return genre;
    }

    public List<Genre> findAll() {
        return genreDbStorage.findAll();
    }

    public Genre save(Genre genre) {
        return genreDbStorage.save(genre);
    }

    public Genre update(Genre genre) {
        genre = genreDbStorage.update(genre);

        if (Objects.isNull(genre)) {
            throw new NotFoundException("Genre with not found!");
        }
        return genre;
    }


    public void deleteById(int id) {
        final Genre genre = genreDbStorage.findById(id);

        if (Objects.isNull(genre)) {
            throw new NotFoundException(String.format("Genre with id=%d not found!", id));
        }
        genreDbStorage.deleteById(id);
    }

    public void setFilmGenre(Film film) {
        genreDbStorage.setFilmGenre(film);
    }

    public void loadFilmGenre(Film film) {
        genreDbStorage.loadFilmGenre(film);
    }
}
