package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealStorage implements MealStorage {
    private final AtomicInteger count = new AtomicInteger(1);
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();

    @Override
    public Meal save(Meal meal) {
        meal.setId(count.getAndIncrement());
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal get(int id) {
        return mealMap.get(id);
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public List<Meal> getList() {
        return new ArrayList<>(mealMap.values());
    }
}

