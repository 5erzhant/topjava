package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    Meal create(Meal meal);

    Meal get(int id);

    void delete(int id);

    Meal update(Meal meal);

    List<Meal> getList();
}
