package ru.yandex.praktikum.dao.like;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void addLike(Long id, Long userId) {
        final String sqlQuery = "INSERT INTO likes(film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, id, userId);
    }

    @Override
    public void deleteLike(Long id, Long userId) {
        final String sqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, id, userId);
    }
}
