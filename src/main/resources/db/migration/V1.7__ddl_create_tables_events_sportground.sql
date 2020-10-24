DROP TABLE IF EXISTS sportgrounds;

create table sportgrounds
(
	id bigserial not null
		constraint sportgrounds_pkey
			primary key,
	address varchar(255) not null
		constraint uk_kvtbkuiwnrkn35yqkyi5fqn42
			unique,
	title varchar(255) not null,
	sport_type_id smallint not null
		constraint fkf1jnc63363fuk2fek3ycc5d6x
			references sport_types
);

DROP TABLE IF EXISTS events;

create table events
(
	id bigserial not null
		constraint events_pkey
			primary key,
	date date not null,
	end_time timestamp,
	chat_id bigint,
	start_time timestamp not null,
	organizer_user_id bigint
		constraint fk400ta890a9egd9lgnplapfm0m
			references users,
	sportground_id bigint not null
		constraint fk6966vhtxb5471hh2ij5lgfuk
			references sportgrounds,
	sport_type_id smallint not null
		constraint fk33979w9uvke4fjm824mu07qdi
			references sport_types
);