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

INSERT INTO sport_types (title)
VALUES
('Баскетбол'), ('Бег'), ('Бокс'), ('Волейбол'), ('Воркаут'), ('Гандбол'), ('Гимнастика'), ('Групповые тренировки'), ('Единоборства'),
('Йога'), ('Каток'), ('Легкая атлетика'), ('Сквош'), ('Танцы'), ('Теннис'), ('Тренажерный зал'), ('Футбол'), ('Хоккей'), ('Другое');


INSERT INTO sportgrounds
(city, address, title, latitude, longitude, surface_type, rent_price, opened)
VALUES
('Москва', 'Московская', 'Открытая площадка', '0.1', '0.2', '1', '100', true),
('Санкт Петербург', 'Петербургская', 'Спортзал Заря', '0.15', '0.35', '3', '3000', false);

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

insert into events
(creation_date, start_date, end_date, sport_type_id, sportground_id, organizer_id, description, is_private, price, maximum_users)
values
('2021-03-24 17:00:00+03', '2021-03-24 17:30:00+03', '2021-03-24 19:00:00+03', 1, 1, 1, 'Первое мероприятие', false, 0, 10),
('2021-03-25 10:00:00+03', '2021-03-25 13:30:00+03', '2021-03-25 14:00:00+03', 2, 1, 1, 'Второе мероприятие', true, 10, 2),
('2021-03-26 20:00:00+03', '2021-03-26 22:30:00+03', '2021-03-26 23:30:00+03', 3, 1, 2, 'Третье мероприятие', false, 100, 5);
