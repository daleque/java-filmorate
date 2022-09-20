package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping("/{id}")
    public Mpa findById(@PathVariable int id) {
        log.info("Send get request /mpa/{}", id);
        return mpaService.findById(id);
    }

    @GetMapping
    public List<Mpa> findAll() {
        log.info("Send get request /mpa");
        return mpaService.findAll();
    }

    @PostMapping
    public Mpa save(@Valid @RequestBody Mpa mpa) {
        log.info("Send post request /mpa {}", mpa);
        return mpaService.save(mpa);
    }

    @PutMapping
    public Mpa update(@Valid @RequestBody Mpa mpa) {
        log.info("Send put request /mpa {}", mpa);
        return mpaService.update(mpa);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        log.info("Send delete request /mpa/{}", id);
        mpaService.deleteById(id);
    }
}
