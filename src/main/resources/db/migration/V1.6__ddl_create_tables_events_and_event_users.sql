DROP TABLE IF EXISTS events;

create table events
(
	id              bigserial not null
		            constraint events_pkey primary key,
	chat_id         bigint,
	creation_date   date not null,
	start_date      date not null,
	end_date        date not null,
	sport_type_id   smallint not null
        constraint  fk_sport_type_id references sport_types,
	sportground_id  bigint not null
		constraint  fk_sportground_id references sportgrounds,
	organizer_id    bigint not null,
	description     varchar(150) not null,
    is_private      boolean not null default false,
    price           INTEGER,
    maximum_members smallint
);

DROP TABLE IF EXISTS event_users;

create table event_users
(
	id bigserial not null
		constraint event_users_pkey
			primary key,
	status smallint not null,
	user_id bigint not null,
	event_id bigint not null
		constraint fkaa8h6gi3vaheiptt7ben9qgn7
			references events
				on delete cascade
);

create unique index if not exists event_users_user_id_event_id_uindex
	on event_users (user_id, event_id);