package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.javawebinar.topjava.util.MealsUtil.meals;

public class SimpleStorage implements Storage {
    private static final List<Meal> mealStorage = new CopyOnWriteArrayList<>();

    static {
        mealStorage.addAll(meals);
    }

    @Override
    public void save(Meal meal) {
        if (getExistingPosition(meal.getId()) >= 0) {
            throw new RuntimeException("Meal already exist");
        }
        mealStorage.add(meal);
    }

    @Override
    public Meal get(int id) {
        return mealStorage.get(getExistingPosition(id));
    }

    @Override
    public void delete(int id) {
        mealStorage.remove(getExistingPosition(id));
    }

    @Override
    public void update(Meal meal) {
        int pos = getExistingPosition(meal.getId());
        mealStorage.remove(pos);
        mealStorage.add(pos, meal);
    }

    @Override
    public List<Meal> getList() {
        return mealStorage;
    }

    public int getExistingPosition(int id) {
        for (int i = 0; i < mealStorage.size(); i++) {
            if (mealStorage.get(i).getId() == id) {
                return i;
            }
        }
        throw new RuntimeException("Meal not exist");
    }
}

