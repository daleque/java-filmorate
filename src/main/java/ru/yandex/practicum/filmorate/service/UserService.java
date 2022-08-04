package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    InMemoryUserStorage inMemoryUserStorage;


    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    public User getUser(int userId) {
        return inMemoryUserStorage.getUser(userId);
    }

    public void addFriend(int userID, int friendID) {

        inMemoryUserStorage.addFriend(userID, friendID);

    }

    public void deleteFriend(int userId, int friendId) {
        inMemoryUserStorage.deleteFriend(userId, friendId);
    }

    public User createUser(User user) {
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    public Set<User> getAllFriendsOfUser(int userId) {
        return inMemoryUserStorage.getAllFriendsOfUser(userId);
    }

    public Set<User> getCommonFriends(int userId, int otherId) {
        return inMemoryUserStorage.getCommonFriends(userId, otherId);
    }
}
