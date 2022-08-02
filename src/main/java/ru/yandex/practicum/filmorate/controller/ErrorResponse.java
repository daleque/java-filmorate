package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Getter
@Setter
@NoArgsConstructor
    public class ErrorResponse {
        String error;


        public ErrorResponse(String error) {
            this.error = error;
        }

    }
