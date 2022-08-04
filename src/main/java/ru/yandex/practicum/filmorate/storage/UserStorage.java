package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.List;

public interface UserStorage {
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(User user);
    void addFriend(int userID, int friendID);
    void deleteFriend(int userID, int friendID);
    HashSet<User> getAllFriendsOfUser(int userID);
    HashSet<User> getCommonFriends(int userID, int otherID);
    User getUser(int userId);
}
