package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class User {
    int id;
    @Email
    @NotBlank
    @NotNull
    String email;
    String login;
    String name;
    LocalDate birthday;
    HashSet<Integer> friendOfUser = new HashSet<>();

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void addFriendOfUser(Integer friendID) {
        friendOfUser.add(friendID);
    }

    public void deleteFriendOfUser(Integer friendID) {
        if (!(friendOfUser.contains(friendID))) {
            String errorMsg = String.format("Отсутствует друг с id=%s", friendID);
            throw new NotFoundException(errorMsg);
        }
        friendOfUser.remove(friendID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

