package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USERS_MEAL_ID, USER_ID);
        assertMatch(meal, usersMeal);
    }

    @Test
    public void getForeignFood() {
        assertThrows(NotFoundException.class, () -> service.get(USERS_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(USERS_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USERS_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteForeignFood() {
        assertThrows(NotFoundException.class, () -> service.delete(USERS_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, usersMeal, usersMeal4, usersMeal3, usersMeal2, usersMeal5);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USERS_MEAL_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateForeignFood() {
        assertThrows(NotFoundException.class, () -> service.update(usersMeal, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, usersMeal.getDateTime(), "duplicate", 555), USER_ID));
    }
}