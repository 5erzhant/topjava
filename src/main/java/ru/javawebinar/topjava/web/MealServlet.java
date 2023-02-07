package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private MealStorage storage;
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    public void init() {
        storage = MealsUtil.mealStorage;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        String id = request.getParameter("id");
        List<MealTo> mealToList = filteredByStreams(storage.getList(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        Meal meal;
        switch (action) {
            case "add":
                meal = new Meal(LocalDateTime.now(), null, 0);
                log.debug("add meal");
                break;
            case "update":
                meal = storage.get(Integer.parseInt(id));
                log.debug("update meal");
                break;
            case "delete": {
                storage.delete(Integer.parseInt(id));
                log.debug("delete meal, redirect meals");
                response.sendRedirect("meals");
                return;
            }
            default:
                request.setAttribute("meals", mealToList);
                log.debug("view, forward meals");
                request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp").forward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        log.debug("forward editMeal");
        request.getRequestDispatcher("/WEB-INF/jsp/editMeal.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(date, description, calories);
        if (id == 0) {
            storage.save(meal);
        } else {
            meal.setId(id);
            storage.update(meal);
        }
        response.sendRedirect("meals");
    }
}
