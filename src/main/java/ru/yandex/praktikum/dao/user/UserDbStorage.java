package ru.yandex.praktikum.dao.user;

import java.util.List;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import lombok.RequiredArgsConstructor;
import ru.yandex.praktikum.exception.NotFoundException;
import ru.yandex.praktikum.model.User;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User findById(Long id) {
        final String sqlQuery = "SELECT user_id, user_name, login, email, birthday FROM users WHERE user_id = ?";
        if (id < 1) {
            throw new NotFoundException(String.format("User with id=%d not found!", id));
        }
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
    }

    @Override
    public List<User> findAll() {
        final String sqlQuery = "SELECT user_id, user_name, login, email, birthday FROM users";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public List<User> findAllFriends(Long id) {
        final String sqlQuery = "SELECT u.* FROM users u JOIN friends f on u.user_id = f.friend_id WHERE f.user_id = ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id);
    }

    @Override
    public List<User> findCommonFriends(Long id, Long otherId) {
        final String sqlQuery =
                "SELECT u.* FROM users u" +
                        " JOIN friends f on u.user_id = f.friend_id" +
                        " JOIN friends f2 on u.user_id = f2.friend_id WHERE f.user_id = ? AND f2.user_id = ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser, id, otherId);
    }

    @Override
    public User save(User user) {
        final String sqlQuery = "INSERT INTO users(user_name, login, email, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getEmail());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public User update(User user) {
        final String sqlQuery = "UPDATE users SET user_name = ?, login = ?, email = ?, birthday = ? WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery
                , user.getName()
                , user.getLogin()
                , user.getEmail()
                , user.getBirthday()
                , user.getId());
        return findById(user.getId());
    }

    @Override
    public void deleteById(Long id) {
        final String sqlQuery = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getLong("user_id"),
                resultSet.getString("user_name"),
                resultSet.getString("login"),
                resultSet.getString("email"),
                resultSet.getDate("birthday").toLocalDate()
        );
    }
}
