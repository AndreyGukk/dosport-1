DROP TABLE IF EXISTS user_sports;

CREATE TABLE user_sports
(
    user_id       BIGINT,
    sport_type_id SMALLINT,
    level         SMALLINT NOT NULL,
    PRIMARY KEY (user_id, sport_type_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (sport_type_id) REFERENCES sport_types (id),
    CONSTRAINT valid_level CHECK (level >= 0 AND level <= 5)
);