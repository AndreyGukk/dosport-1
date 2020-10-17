DROP TABLE IF EXISTS cross_users_user_sports;

CREATE TABLE cross_users_user_sports
(
    user_id       SMALLINT,
    user_sport_id SMALLINT,
    PRIMARY KEY (user_id, user_sport_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (user_sport_id) REFERENCES user_sports (id)
);
