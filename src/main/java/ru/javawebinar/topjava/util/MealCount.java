package ru.javawebinar.topjava.util;

public class MealCount {
    private static int count;

    public static synchronized int getCount() {
        count++;
        return count;
    }
}
