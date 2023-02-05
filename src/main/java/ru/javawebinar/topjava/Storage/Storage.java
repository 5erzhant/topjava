package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    void save(Meal meal);

    Meal get(int id);

    void delete(int id);

    void update(Meal meal);

    List<Meal> getList();
}
