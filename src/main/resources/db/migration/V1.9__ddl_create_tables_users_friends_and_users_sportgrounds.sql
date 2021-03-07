DROP TABLE IF EXISTS users_friends;

CREATE TABLE users_friends
(
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (friend_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS users_friends;

CREATE TABLE users_sportgrounds
(
    user_id BIGINT NOT NULL,
    sportground_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, sportground_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (sportground_id) REFERENCES sportgrounds (id)
);
