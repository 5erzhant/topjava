package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USERS_MEAL_ID = START_SEQ + 3;
    public static final Meal usersMeal = new Meal(USERS_MEAL_ID, LocalDateTime.of(2023, Month.JUNE, 15,
            7, 0, 36), "завтрак пользователя", 500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2023, Month.JUNE, 19, 5, 36, 0),
                "новый завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(usersMeal);
        updated.setDateTime(LocalDateTime.of(2023, Month.FEBRUARY, 12, 9, 0, 0));
        updated.setDescription("updated");
        updated.setCalories(777);
        return updated;
    }
}