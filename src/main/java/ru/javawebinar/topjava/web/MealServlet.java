package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Storage.SimpleStorage;
import ru.javawebinar.topjava.Storage.Storage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.caloriesPerDay;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {

    private static Storage storage;
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    public void init() {
        storage = new SimpleStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        List<MealTo> mealsTo = filteredByStreams(storage.getList(), LocalTime.of(0, 0), LocalTime.of(23, 59), caloriesPerDay);
        if (action == null) {
            log.debug("redirect to meals");
            request.setAttribute("meals", mealsTo);
            request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp").forward(request, response);
            return;
        }
        Meal meal = null;
        switch (action) {
            case "add" -> meal = new Meal();
            case "update" -> {
                try {
                    meal = storage.get(Integer.parseInt(id));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "delete" -> {
                try {
                    storage.delete(Integer.parseInt(id));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                response.sendRedirect("meals");
                return;
            }
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id == 0) {
            storage.save(new Meal(date, description, calories));
        } else {
            storage.update(new Meal(date, description, calories, id));
        }
        response.sendRedirect("meals");
    }
}
