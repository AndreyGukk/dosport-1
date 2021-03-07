DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id            BIGSERIAL,
    user_name     VARCHAR(50) NOT NULL UNIQUE,
    password      VARCHAR(150) NOT NULL,
    enabled       BOOLEAN NOT NULL,
    birthday_date TIMESTAMP,
    hide_birthday_date BOOLEAN NOT NULL,
    first_name    VARCHAR(50),
    last_name     VARCHAR(100),
    gender        SMALLINT,
    info          VARCHAR(250),
    photo_link    VARCHAR(250),
    PRIMARY KEY (id),
    CONSTRAINT valid_gender CHECK (gender >= 0 AND gender <= 2)
);