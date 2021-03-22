INSERT INTO users
(user_name, password, enabled, birthday_date, gender, info, photo_link)
VALUES
('admin', '$2a$10$RPw43x59Msn0V3XizeZxJOxBgN9wroljntTNNkGEGvIr5/5DAtL9G', true, '2001-02-03', 2, 'Инфо об админе', 'myfoto.png'),
('user', '$2a$10$hbCJ1KExJubHBMYc1DVqXeqNggg.zFOhqBBb1efCFoFrT1KPuQjeW', true, '2003-04-05', 1, 'Инфо о пользователе', 'katya.png');

INSERT INTO authorities
(authority)
VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO user_authorities
(user_id, authority_id)
VALUES
(1, 1),
(1, 2),
(2, 2);

INSERT INTO infrastructures
VALUES
(0), (1), (2), (3), (4), (5), (6), (7), (8);

INSERT INTO sport_types (title)
VALUES
('Хоккей'), ('Футбол'), ('Баскетбол'), ('Волейбол'), ('Бег'), ('Воркаут'), ('Теннис'), ('Тренажерный зал'),
('Бокс'), ('Йога'), ('Танцы'), ('Единоборства'), ('Каток'), ('Сквош'), ('Гимнастика'), ('Гандбол'), ('Групповые трени'), ('Другое');

INSERT INTO sportgrounds
(city, address, title, latitude, longitude, metro_station, surface_type, rent_price, opened)
VALUES
('Москва', 'Московская', 'Открытая площадка', '0.1', '0.2', '1', '1', '100', true),
('Санкт Петербург', 'Петербургская', 'Спортзал Заря', '0.15', '0.35', '2', '3', '3000', false);

INSERT INTO sportground_sport_types
(sportground_id, sport_type_id)
VALUES
(1, 1), (1,2), (2,3), (2,4);

INSERT INTO sportground_infrastructures
(sportground_id, infrastructure_id)
VALUES
(1, 1), (1,2), (2,3), (2,4);

INSERT INTO user_sportgrounds
(user_id, sportground_id)
VALUES
(1, 1), (2,2);

insert into user_sports
(user_id, sport_type_id)
values
(1 , 1), (1, 2), (2, 3), (2, 4);