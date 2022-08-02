package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyExistException extends RuntimeException {

        public AlreadyExistException() {
        }

        public AlreadyExistException(String message) {
            super(message);
        }

        public AlreadyExistException(String message, Throwable cause) {
            super(message, cause);
        }

        public AlreadyExistException(Throwable cause) {
            super(cause);
        }
    }


