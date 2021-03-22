DROP TABLE IF EXISTS reviews;

create table reviews
(
	id              bigserial NOT NULL UNIQUE,
	date            date NOT NULL,
	sportground_id  bigint NOT NULL,
	user_id         bigint,
	text            varchar(255) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (sportground_id) REFERENCES sportgrounds (id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS event_messages;

create table event_messages
(
	id              bigserial NOT NULL UNIQUE,
	event_id        bigint NOT NULL,
	user_id         bigint,
	text            varchar(255) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL,
	FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);