package ru.yandex.praktikum.dao.mpa;

import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import lombok.RequiredArgsConstructor;
import ru.yandex.praktikum.exception.NotFoundException;
import ru.yandex.praktikum.model.Mpa;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa findById(Long id) {
        final String sqlQuery = "SELECT mpa_id, mpa_name FROM mpa WHERE mpa_id = ?";
        if (id < 1) {
            throw new NotFoundException(String.format("Mpa with id=%d not found!", id));
        }
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, id);
    }

    @Override
    public List<Mpa> findAll() {
        final String sqlQuery = "SELECT mpa_id, mpa_name FROM mpa";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    @Override
    public Mpa save(Mpa mpa) {
        final String sqlQuery = "INSERT INTO mpa(mpa_name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"MPA_ID"});
            stmt.setString(1, mpa.getName());
            return stmt;
        }, keyHolder);
        return mpa;
    }

    @Override
    public Mpa update(Mpa mpa) {
        final String sqlQuery = "UPDATE mpa SET mpa_name = ? WHERE mpa_id = ?";
        jdbcTemplate.update(sqlQuery
                , mpa.getName()
                , mpa.getId());
        return findById(mpa.getId());
    }

    @Override
    public void deleteById(Long id) {
        final String sqlQuery = "DELETE FROM mpa WHERE mpa_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return new Mpa(
                resultSet.getLong("mpa_id"),
                resultSet.getString("mpa_name")
        );
    }
}
