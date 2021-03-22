DROP TABLE IF EXISTS sport_types;

create table sport_types
(
	id              serial NOT NULL UNIQUE,
	title           varchar(150) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_sports;

CREATE TABLE user_sports
(
    user_id         BIGINT NOT NULL,
    sport_type_id   SMALLINT NOT NULL,
    PRIMARY KEY (user_id, sport_type_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (sport_type_id) REFERENCES sport_types (id) ON DELETE CASCADE
);