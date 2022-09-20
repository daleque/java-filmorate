package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Film {
    private int id;
    @NotNull(message = "Необходимо указать название фильма.")
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;

    @NotNull(message = "Необходимо указать описание фильма.")
    @NotBlank(message = "Описание фильма не может быть пустым.")
    @Length(max = 200)
    private String description;

    @NotNull(message = "Необходимо указать дату релиза фильма.")
    private LocalDate releaseDate;

    @Min(1)
    private long duration;

    int rate;
    HashSet<Integer> likes = new HashSet<>();
    private Mpa mpa;
    private Set<Genre> genres;


    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(String name, String description, LocalDate releaseDate, int duration, int rate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
    }

    public Film(int id, String name, String description, LocalDate releaseDate, long duration, Mpa mpa, Set<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genres;
    }

    public void setLikes(int userId) {
        likes.add(userId);
    }

    public void deleteLikesFromUsers(int userId) {
        if(!(likes.contains(userId))) {
            String errorMsg = String.format("Отсутствует лайк от пользователя с id=%s", userId);
            throw new NotFoundException(errorMsg);
        }
        if(userId < 0) {
            String errorMsg = String.format("Некорректный id=%s", userId);
            throw new NotFoundException(errorMsg);
        }
        likes.remove(userId);
    }

}
