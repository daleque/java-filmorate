package ru.yandex.praktikum.dao.genre;

import java.util.*;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import lombok.RequiredArgsConstructor;
import ru.yandex.praktikum.exception.NotFoundException;
import ru.yandex.praktikum.model.Film;
import ru.yandex.praktikum.model.Genre;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Genre findById(Long id) {
        final String sqlQuery = "SELECT genre_id, genre_name FROM genres WHERE genre_id = ?";
        if (id < 1) {
            throw new NotFoundException(String.format("Genre with id=%d not found!", id));
        }
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
    }

    @Override
    public List<Genre> findAll() {
        final String sqlQuery = "SELECT genre_id, genre_name FROM genres";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public Genre save(Genre genre) {
        final String sqlQuery = "INSERT INTO genres(genre_name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"GENRE_ID"});
            stmt.setString(1, genre.getName());
            return stmt;
        }, keyHolder);
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        final String sqlQuery = "UPDATE genres SET genre_name = ? WHERE genre_id = ?";
        jdbcTemplate.update(sqlQuery
                , genre.getName()
                , genre.getId());
        return findById(genre.getId());
    }

    @Override
    public void deleteById(Long id) {
        final String sqlQuery = "DELETE FROM genres WHERE genre_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public void setFilmGenre(Film film) {
        if (film.getGenres() == null) {
            return;
        }
        if (film.getGenres().isEmpty()) {
            film.setGenres(new LinkedHashSet<>());
        }
        final String sqlDelete = "DELETE FROM films_genres WHERE film_id = ?";
        jdbcTemplate.update(sqlDelete, film.getId());

        final String sqlInsert = "INSERT INTO films_genres (film_id, genre_id) VALUES (?, ?)";
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sqlInsert, film.getId(), genre.getId());
        }
    }

    @Override
    public Set<Genre> loadFilmGenre(Film film) {
        final String sqlQuery =
                "SELECT genres.genre_id, genre_name FROM genres" +
                        " JOIN films_genres fg ON genres.genre_id = fg.genre_id WHERE film_id = ?";
        return new LinkedHashSet<>(jdbcTemplate.query(sqlQuery, this::mapRowToGenre, film.getId()));
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return new Genre(
                resultSet.getLong("genre_id"),
                resultSet.getString("genre_name")
        );
    }
}
