package ru.yandex.praktikum.dao.like;

public interface LikeStorage {
    void addLike(Long id, Long userId);
    void deleteLike(Long id, Long userId);
}
