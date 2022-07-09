package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private Long filmID;
    private String filmName;
    private String filmDescription;
    private LocalDate filmReleaseDate;
    private Duration filmDuration;

    public Film(Long filmID, String filmName, String filmDescription, LocalDate filmReleaseDate, Duration filmDuration) {
        this.filmID = filmID;
        this.filmName = filmName;
        this.filmDescription = filmDescription;
        this.filmReleaseDate = filmReleaseDate;
        this.filmDuration = filmDuration;
    }
}
