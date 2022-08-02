package ru.yandex.practicum.filmorate.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@Slf4j
@RestController
@Validated
@NoArgsConstructor
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту users, метод POST");
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту users, метод PUT");
        return userService.updateUser(user);
    }

	@GetMapping("/{userId}")
	User getUser(@PathVariable int userId) {
		log.info("Get user by id={}", userId);
		return userService.getUser(userId);
	}

	@PutMapping("/{userId}/friends/{friendId}")
	public void addFriend(@PathVariable int userId, @PathVariable int friendId) {
		userService.addFriend(userId, friendId);
	}

	@DeleteMapping("/{userId}/friends/{friendId}")
	public void deleteFriend(@PathVariable int userId, @PathVariable int friendId) {
		userService.deleteFriend(userId, friendId);
	}

	@GetMapping("/{userId}/friends")
	public HashSet<User> getAllFriendsOfUser(@PathVariable int userId) {
		return userService.getAllFriendsOfUser(userId);
	}

	@GetMapping ("/{userId}/friends/common/{otherId}")
	public HashSet<User> getCommonFriends(@PathVariable int userId, @PathVariable int otherId) {
		return userService.getCommonFriends(userId, otherId);
	}

}