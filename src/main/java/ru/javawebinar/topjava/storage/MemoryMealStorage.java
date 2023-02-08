package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealStorage implements MealStorage {

    public MemoryMealStorage() {
        this.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        this.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        this.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        this.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        this.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        this.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        this.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

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
        if (mealMap.containsKey(meal.getId())) {
            mealMap.put(meal.getId(), meal);
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getList() {
        return new ArrayList<>(mealMap.values());
    }
}

