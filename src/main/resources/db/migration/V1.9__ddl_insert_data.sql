INSERT INTO users
(user_name, password, enabled, birthday_date, hide_birthday_date, first_name, last_name, gender, info, photo_link)
VALUES
('admin', '$2a$10$ArsakpPHT5jPbPEAeVc/lup1V4tJS9hqaa1PfRNIUy459JkPjK5xS', true, '2001-02-03', false, 'Иван', 'Иванов', 2, 'Инфо об админе', 'myfoto.png'),
('user', '$2a$10$7v8.w1xVYIu6TKY3a58CX.xcmSXPpW6mVLTqB11kAn10jezppGdE2', true, '2003-04-05', false, 'Катя', 'Петрова', 1, 'Инфо о пользователе', 'katya.png');

INSERT INTO authorities
(authority)
VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO users_authorities
(user_id, authority_id)
VALUES
(1, 1),
(2, 2);

INSERT INTO infrastructures
VALUES
(0), (1), (2), (3), (4), (5), (6), (7), (8);

INSERT INTO sport_types (title)
VALUES
('Хоккей'), ('Футбол'), ('Баскетбол'), ('Волейбол'), ('Бег'), ('Воркаут'), ('Теннис'), ('Тренажерный зал'),
('Бокс'), ('Йога'), ('Танцы'), ('Единоборства'), ('Каток'), ('Сквош'), ('Гимнастика'), ('Гандбол'), ('Групповые трени'), ('Другое');