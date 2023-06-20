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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USERS_MEAL_ID, USER_ID);
        assertThat(meal).usingRecursiveComparison().isEqualTo(usersMeal);
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
        assertThat(all).usingRecursiveFieldByFieldElementComparator().isEqualTo(Collections.singletonList(usersMeal));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertThat(service.get(USERS_MEAL_ID, USER_ID)).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    public void updateForeignFood() {
        assertThrows(NotFoundException.class, () -> service.update(service.get(USERS_MEAL_ID, USER_ID), ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertThat(created).usingRecursiveComparison().isEqualTo(newMeal);
        assertThat(service.get(newId, USER_ID)).usingRecursiveComparison().isEqualTo(newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2023, Month.JUNE, 15,
                        7, 0, 36), "duplicate", 555), USER_ID));
    }
}