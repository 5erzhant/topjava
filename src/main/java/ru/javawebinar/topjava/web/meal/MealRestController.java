package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;
//    private final int userId = SecurityUtil.authUserId();

    public void create(Meal meal) {
        service.create(meal);
    }

    public void delete(int id) {
        log.info("Delete id={}", id);
        service.delete(id);
    }

    public Meal get(int id) {
        log.info("Get id={}", id);
        return service.get(id);
    }

    public List<MealTo> getAllTo() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal getEmpty() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
    }

}