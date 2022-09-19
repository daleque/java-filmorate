package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUsers() {
        final String sqlQuery = "SELECT USER_ID, USER_NAME, LOGIN, EMAIL, BIRTHDAY FROM USERS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }
    @Override
    public User createUser(User user) {
        final String sqlQuery = "INSERT INTO USERS(USER_NAME, LOGIN, EMAIL, BIRTHDAY) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getEmail());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());
        return user;
    }
    @Override
    public User updateUser(User user) {
        final String sqlQuery = "UPDATE USERS SET USER_NAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getName()
                , user.getLogin()
                , user.getEmail()
                , user.getBirthday()
                , user.getId());
        return getUser(user.getId());

    }
    @Override
    public void addFriend(int userID, int friendID) {
        final String sqlQuery = "INSERT INTO FRIENDS(USER_ID, FRIEND_ID) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, userID, friendID);
    }
    @Override
    public void deleteFriend(int userID, int friendID) {
        final String sqlQuery = "DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, userID, friendID);
    }
    @Override
    public HashSet<User> getAllFriendsOfUser(int userID) {
        final String sqlQuery = "SELECT U.* FROM USERS U JOIN FRIENDS F on U.USER_ID = F.FRIEND_ID WHERE F.USER_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sqlQuery, this::mapRowToUser, userID));
    }
    @Override
    public HashSet<User> getCommonFriends(int userID, int otherID) {
        final String sqlQuery =
                "SELECT U.* FROM USERS U" +
                        " JOIN FRIENDS F on U.USER_ID = F.FRIEND_ID" +
                        " JOIN FRIENDS F2 on U.USER_ID = F2.FRIEND_ID WHERE F.USER_ID = ? AND F2.USER_ID = ?";
        return new HashSet<>(jdbcTemplate.query(sqlQuery, this::mapRowToUser, userID, otherID));
    }
    @Override
    public User getUser(int userId) {
        final String sqlQuery = "SELECT USER_ID, USER_NAME, LOGIN, EMAIL, BIRTHDAY FROM USERS WHERE USER_ID = ?";
        if (userId < 1) {
            throw new NotFoundException(String.format("User with id=%d not found!", userId));
        }
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, userId);
    }

    @Override
    public void deleteUser(int userId) {
        final String sqlQuery = "DELETE FROM USERS WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery, userId);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getInt("USER_ID"),
                resultSet.getString("USER_NAME"),
                resultSet.getString("USER_LOGIN"),
                resultSet.getString("USER_EMAIL"),
                resultSet.getDate("USER_BIRTHDAY").toLocalDate()
        );
    }
}
