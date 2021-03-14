DROP TABLE IF EXISTS user_subscribes;

CREATE TABLE user_subscribes
(
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (friend_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS user_sportgrounds;

CREATE TABLE user_sportgrounds
(
    user_id BIGINT NOT NULL,
    sportground_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, sportground_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (sportground_id) REFERENCES sportgrounds (id)
);
