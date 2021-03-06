DROP TABLE IF EXISTS authorities;

CREATE TABLE authorities
(
    id              SMALLSERIAL,
    authority       VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_authorities;

CREATE TABLE user_authorities
(
    user_id         BIGINT,
    authority_id    SMALLINT,
    PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (authority_id) REFERENCES authorities (id) ON DELETE CASCADE
);