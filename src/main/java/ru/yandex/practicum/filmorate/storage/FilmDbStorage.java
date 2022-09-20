package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;

    @Override
    public List<Film> getAllFilms() {
        return null;
    }

    @Override
    public Film createFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public void likeFilm(int filmId, int userId) {

    }

    @Override
    public void deleteLike(int filmId, int userId) {

    }

    @Override
    public Film get(int filmId) {
        final String sqlQuery =
                "SELECT FILM_ID, FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, M.MPA_ID, M.MPA_NAME FROM FILMS" +
                        " JOIN MPA M ON M.MPA_ID = FILMS.MPA_ID " +
                        "WHERE FILM_ID = ?";
        if (filmId < 1) {
            throw new NotFoundException(String.format("Film with id=%d not found!", filmId));
        }
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, filmId);
    }

    @Override
    public void deleteFilm(int filmId) {

    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film(
                resultSet.getInt("FILM_ID"),
                resultSet.getString("FILM_NAME"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getDate("RELEASE_DATE").toLocalDate(),
                resultSet.getInt("DURATION"),
                new Mpa(
                        resultSet.getInt("MPA_ID"),
                        resultSet.getString("MPA_NAME")
                ),
                new LinkedHashSet<>()
        );
        film.setGenres(genreDbStorage.loadFilmGenre(film));
        return film;
    }
}
