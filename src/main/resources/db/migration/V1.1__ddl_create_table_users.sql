DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id              BIGSERIAL,
    user_name       VARCHAR(50) NOT NULL UNIQUE,
    password        VARCHAR(150) NOT NULL,
    enabled         BOOLEAN NOT NULL,
    birthday_date   TIMESTAMP,
    gender          SMALLINT,
    info            VARCHAR(150),
    photo_link      VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT valid_gender CHECK (gender >= 0 AND gender <= 2)
);