package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Long userID;
    private String userEmail;
    private String userLogin;
    private String userName;
    private LocalDate userBirthday;


    public User(Long userID, String userEmail, String userLogin, String userName, LocalDate userBirthday) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userLogin = userLogin;
        this.userName = userName;
        this.userBirthday = userBirthday;
    }
}
