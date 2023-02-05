package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.MealCount.getCount;

public class Meal {
    private final int id;
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal() {
        this.dateTime = null;
        this.description = "";
        this.calories = 0;
        this.id = 0;
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = getCount();
    }

    public Meal(LocalDateTime dateTime, String description, int calories, int id) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
