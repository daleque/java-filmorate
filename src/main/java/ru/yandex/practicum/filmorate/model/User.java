package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    HashMap<Integer, Boolean> friendOfUser = new HashMap<>();

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void addFriendOfUser(Integer friendID, Boolean isMutually) {
        friendOfUser.put(friendID, isMutually);
    }

    public void deleteFriendOfUser(Integer friendID) {
        if (!(friendOfUser.containsKey(friendID))) {
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

