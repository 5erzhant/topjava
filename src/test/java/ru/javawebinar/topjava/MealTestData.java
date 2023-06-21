package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USERS_MEAL_ID = START_SEQ + 3;
    public static final Meal usersMeal = new Meal(USERS_MEAL_ID, LocalDateTime.of(2023, Month.JUNE, 15,
            7, 0), "завтрак пользователя", 800);
    public static final Meal usersMeal2 = new Meal(USERS_MEAL_ID + 2, LocalDateTime.of(2023, Month.MAY, 15,
            0, 0), "еда на граничное значение", 600);
    public static final Meal usersMeal3 = new Meal(USERS_MEAL_ID + 3, LocalDateTime.of(2023, Month.MAY, 15,
            15, 0), "обед пользователя", 1000);
    public static final Meal usersMeal4 = new Meal(USERS_MEAL_ID + 4, LocalDateTime.of(2023, Month.MAY, 15,
            20, 0), "ужин пользователя", 500);
    public static final Meal usersMeal5 = new Meal(USERS_MEAL_ID + 5, LocalDateTime.of(2023, Month.MAY, 14,
            20, 0), "ужин пользователя", 700);


    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2023, Month.JUNE, 19, 5, 36),
                "новый завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(usersMeal);
        updated.setDateTime(LocalDateTime.of(2023, Month.FEBRUARY, 12, 9, 0));
        updated.setDescription("updated");
        updated.setCalories(777);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}