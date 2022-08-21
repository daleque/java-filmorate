package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    int usersID = 0;

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser (User user) {
        if (users.containsKey(user.getId())) {
            String errorMsg = String.format("Пользователь с id=%s уже существует", user.getId());
            log.error(errorMsg);
            throw new AlreadyExistException(errorMsg);
        }

        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        validateUser(user);
        user.setId(++usersID);
        saveUser(user);
        return user;
    }

    @Override
    public User updateUser(User user) {

        if (!(users.containsKey(user.getId()))) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", user.getId());
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        validateUser(user);
        saveUser(user);
        return user;
    }

    private void validateUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Invalid birthday");
        }
        if (!(user.getEmail().contains("@"))) {
            throw new ValidationException("Invalid e-mail");
        }
        if (user.getEmail().isEmpty()) {
            throw new ValidationException("Invalid e-mail");
        }
        if (user.getEmail().isBlank()) {
            throw new ValidationException("Invalid e-mail");
        }
        if (user.getLogin().isEmpty()) {
            throw new ValidationException("Invalid login");
        }
        if (user.getLogin().isBlank()) {
            throw new RuntimeException("Invalid login");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Invalid login");
        }
    }

    private void saveUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void addFriend(int userID, int friendID) {
        if(!(users.containsKey(userID))) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", userID);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        if(!(users.containsKey(friendID))) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", friendID);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        User user = users.get(userID);
        User friend = users.get(friendID);

        user.addFriendOfUser(friendID, false);
        friend.addFriendOfUser(userID, false);
    }

    @Override
    public void deleteFriend(int userID, int friendID) {
        if(!(users.containsKey(userID))) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", userID);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }
        User user = users.get(userID);
        User friend = users.get(friendID);
        user.deleteFriendOfUser(friendID);
        friend.deleteFriendOfUser(userID);
    }

    @Override
    public HashSet<User> getAllFriendsOfUser(int userID) {
        if(!(users.containsKey(userID))) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", userID);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }
        User user = users.get(userID);
        if (user.getFriendOfUser() == null) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", userID);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }

        HashMap<Integer, Boolean> friendsID = user.getFriendOfUser();
        HashSet<User> friends = new HashSet<>();
        for(Integer friendID : friendsID.keySet()) {
            friends.add(users.get(friendID));
        }
        return friends;
    }

    @Override
    public HashSet<User> getCommonFriends(int userID, int otherID) {
        if(!(users.containsKey(userID))) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", userID);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }
        if(!(users.containsKey(otherID))) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", otherID);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }
        User user1 = users.get(userID);
        User user2 = users.get(otherID);

        HashMap<Integer, Boolean> user1FriendsID = user1.getFriendOfUser();
        HashMap<Integer, Boolean> user2FriendsID = user2.getFriendOfUser();

        HashSet<User> user1Friends = new HashSet<>();
        for(Integer friendID : user1FriendsID.keySet()) {
            user1Friends.add(users.get(friendID));
        }
        HashSet<User> user2Friends = new HashSet<>();
        for(Integer friendID : user2FriendsID.keySet()) {
            user2Friends.add(users.get(friendID));
        }

        HashSet<User> commonFriends = new HashSet<>();
        for (User friendOfUser1 : user1Friends) {
            for(User friendOfUser2 : user2Friends) {
                if (friendOfUser1.equals(friendOfUser2)) {
                    commonFriends.add(friendOfUser1);
                }
            }
        }
        return commonFriends;
    }
    @Override
    public User getUser(int userId) {
        if(!(users.containsKey(userId))) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", userId);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }
        User user = users.get(userId);
        return user;
    }

    @Override
    public void deleteUser(int userId) {
        if(!(users.containsKey(userId))) {
            String errorMsg = String.format("Отсутствует пользователь с id=%s", userId);
            log.error(errorMsg);
            throw new NotFoundException(errorMsg);
        }
        users.remove(userId, users.get(userId));
    }

}
