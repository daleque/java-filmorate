package ru.yandex.praktikum.dao.friend;

public interface FriendStorage {
    void addFriend(Long id, Long friendId);
    void deleteFriend(Long id, Long friendId);
}
