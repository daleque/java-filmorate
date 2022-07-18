package ru.yandex.practicum.filmorate.controller;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    /*@Test
    void validateFilmWithoutName() {
        FilmController filmController = new FilmController();
        Film film = new Film(null,
                "Description",
                LocalDate.of(1999, 1, 13),
                2);
        Assertions.assertThrows(NullPointerException.class, () -> {
            filmController.validateFilm(film);
        });
    }

    @Test
    void validateFilmWithBlankName() {
        FilmController filmController = new FilmController();
        Film film = new Film("    ",
                "Description",
                LocalDate.of(1999, 1, 13),
                2);
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.validateFilm(film);
        });
    }

    @Test
    void validateFilmWithLongDescription() {
        FilmController filmController = new FilmController();
        Film film = new Film("The Green Mile",
                "At a Louisiana assisted-living home in 1999, elderly retiree Paul Edgecomb becomes emotional while viewing the film Top Hat. His companion Elaine becomes concerned, and Paul explains to her that the film reminded him of events that he witnessed when he was an officer at Cold Mountain Penitentiary's death row, nicknamed \"The Green Mile.\"\n" +
                        "\n" +
                        "In 1935, Paul supervises Corrections Officers Brutus \"Brutal\" Howell, Dean Stanton, Harry Terwilliger, and Percy Wetmore, reporting to chief warden Hal Moores. Paul is introduced to John Coffey, a physically imposing but mild-mannered African American man sentenced to death after being convicted of raping and murdering two young white girls. He joins two other condemned convicts on the block: Eduard \"Del\" Delacroix and Arlen Bitterbuck, the latter of whom is the first to be executed. Percy, the nephew of the state governor's wife, demonstrates a sadistic streak but flaunts his family connections to avoid being held accountable; he is particularly abusive towards Del, breaking his fingers and killing his pet mouse Mr. Jingles.\n" +
                        "\n" +
                        "After John heals Paul's severe bladder infection by touching him and later resurrects Mr. Jingles, Paul gradually realizes that John possesses a supernatural ability to heal others. Suspecting that John is endowed with the power to perform divine miracles, Paul doubts whether he is truly guilty of his crimes. Meanwhile, the officers are forced to deal with psychotic new inmate William \"Wild Bill\" Wharton, who frequently causes trouble by assaulting the officers and racially abusing John, forcing them to restrain him in the block's padded cell on more than one occasion.\n" +
                        "\n",
                LocalDate.of(1999, 1, 13),
                2);
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.validateFilm(film);
        });
    }

    @Test
    void validateFilmWithWrongReleaseDate() {
        FilmController filmController = new FilmController();
        Film film = new Film("The Green Mile",
                "Description",
                LocalDate.of(1777, 1, 13),
                2);
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.validateFilm(film);
        });
    }

    @Test
    void validateFilmWithNegativeDuration() {
        FilmController filmController = new FilmController();
        Film film = new Film("The Green Mile",
                "Description",
                LocalDate.of(1999, 1, 13),
                -2);
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.validateFilm(film);
        });
    }*/
}