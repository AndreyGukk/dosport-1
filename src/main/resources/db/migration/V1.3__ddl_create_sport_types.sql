DROP TABLE IF EXISTS sport_types;

CREATE TABLE sport_types
(
    id    SMALLSERIAL,
    title VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);