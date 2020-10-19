DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id            BIGSERIAL,
    user_name     VARCHAR(128) NOT NULL UNIQUE,
    password      VARCHAR(40)  NOT NULL,
    birthday_date DATE,
    first_name    VARCHAR(128),
    last_name     VARCHAR(128),
    gender        SMALLINT,
    info          VARCHAR(4096),
    photo_link    VARCHAR(1024),
    PRIMARY KEY (id),
    CONSTRAINT valid_gender CHECK (gender >= 0 AND gender <= 1)
);