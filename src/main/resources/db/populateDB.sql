DELETE FROM meals;
DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2023-06-15 07:00', 'завтрак пользователя', 800, 100000),
       ('2023-06-15 09:00', 'завтрак админа', 700, 100001),
       ('2023-05-15 00:00', 'еда на граничное значение', 600, 100000),
       ('2023-05-15 15:00', 'обед пользователя', 1000, 100000),
       ('2023-05-15 20:00', 'ужин пользователя', 500, 100000),
       ('2023-05-14 20:00', 'ужин пользователя', 700, 100000)