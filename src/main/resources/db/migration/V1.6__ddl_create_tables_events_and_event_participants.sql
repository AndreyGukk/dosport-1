DROP TABLE IF EXISTS events;

create table events
(
	id              bigserial NOT NULL UNIQUE,
	chat_id         bigint,
	creation_date   date not null,
	start_date      date not null,
	end_date        date not null,
	sport_type_id   smallint not null,
	sportground_id  bigint not null,
	organizer_id    bigint not null,
	description     varchar(150) not null,
    is_private      boolean not null default false,
    price           INTEGER,
    maximum_users   smallint,
    users_amount    smallint,
    messages_amount smallint,
    PRIMARY KEY (id),
    FOREIGN KEY (organizer_id) REFERENCES users (id),
    FOREIGN KEY (sport_type_id) REFERENCES sport_types (id),
    FOREIGN KEY (sportground_id) REFERENCES sportgrounds (id)
);

DROP TABLE IF EXISTS event_participants;

create table event_participants
(
	event_id        bigint not null,
	user_id         bigint not null,
	PRIMARY KEY (event_id, user_id),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);

create unique index if not exists event_participants_user_id_event_id_uindex
	on event_participants (user_id, event_id);

DROP TABLE IF EXISTS event_invitations;

create table event_invitations
(
	event_id        bigint not null,
	user_id         bigint not null,
	PRIMARY KEY (event_id, user_id),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);