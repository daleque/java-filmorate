package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaDbStorage mpaDbStorage;

    public Mpa findById(int id) {
        final Mpa mpa = mpaDbStorage.findById(id);

        if (Objects.isNull(mpa)) {
            throw new NotFoundException(String.format("Mpa with id=%d not found!", id));
        }
        return mpa;
    }

    public List<Mpa> findAll() {
        return mpaDbStorage.findAll();
    }

    public Mpa save(Mpa mpa) {
        return mpaDbStorage.save(mpa);
    }

    public Mpa update(Mpa mpa) {
        mpa = mpaDbStorage.update(mpa);

        if (Objects.isNull(mpa)) {
            throw new NotFoundException("Mpa with not found!");
        }
        return mpa;
    }

    public void deleteById(int id) {
        final Mpa mpa = mpaDbStorage.findById(id);

        if (Objects.isNull(mpa)) {
            throw new NotFoundException(String.format("Mpa with id=%d not found!", id));
        }
        mpaDbStorage.deleteById(id);
    }
}
