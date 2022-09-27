package ru.yandex.praktikum.dao.film;

import java.util.List;
import java.util.LinkedHashSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import lombok.RequiredArgsConstructor;
import ru.yandex.praktikum.dao.genre.GenreStorage;
import ru.yandex.praktikum.exception.NotFoundException;
import ru.yandex.praktikum.model.Mpa;
import ru.yandex.praktikum.model.Film;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;

    @Override
    public Film findById(Long id) {
        final String sqlQuery =
                " SELECT film_id, film_name, description, release_date, duration, m.mpa_id, m.mpa_name FROM films" +
                        " JOIN mpa m ON m.mpa_id = films.mpa_id" +
                        " WHERE film_id = ?";
        if (id < 1) {
            throw new NotFoundException(String.format("Film with id=%d not found!", id));
        }
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
    }

    @Override
    public List<Film> findAll() {
        final String sqlQuery =
                " SELECT film_id, film_name, description, release_date, duration, m.mpa_id, m.mpa_name FROM films" +
                        " JOIN mpa m ON m.mpa_id = films.mpa_id";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public List<Film> findPopularFilms(Integer count) {
        final String sqlQuery =
                " SELECT films.film_id, film_name, description, release_date, duration, m.mpa_id, m.mpa_name FROM films" +
                        " JOIN mpa m ON m.mpa_id = films.mpa_id" +
                        " LEFT JOIN likes AS l ON films.film_id = l.film_id" +
                        " GROUP BY films.film_id ORDER BY COUNT(l.user_id) DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, count);
    }

    @Override
    public Film save(Film film) {
        final String sqlQuery = "INSERT INTO films(film_name, description, release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setLong(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        genreStorage.setFilmGenre(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        final String sqlQuery =
                "UPDATE films SET film_name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ?" +
                        " WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getMpa().getId()
                , film.getId()
        );
        genreStorage.setFilmGenre(film);
        return findById(film.getId());
    }

    @Override
    public void deleteById(Long id) {
        final String sqlQuery = "DELETE FROM films WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film(
                resultSet.getLong("film_id"),
                resultSet.getString("film_name"),
                resultSet.getString("description"),
                resultSet.getDate("release_date").toLocalDate(),
                resultSet.getInt("duration"),
                new Mpa(
                        resultSet.getLong("mpa_id"),
                        resultSet.getString("mpa_name")
                ),
                new LinkedHashSet<>()
        );
        film.setGenres(genreStorage.loadFilmGenre(film));
        return film;
    }
}
