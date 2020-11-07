DROP TABLE IF EXISTS sportgrounds;

create table sportgrounds
(
	id bigserial not null
		constraint sportgrounds_pkey
			primary key,
	city varchar(255) not null,
	address varchar(255) not null
		constraint uk_kvtbkuiwnrkn35yqkyi5fqn42
			unique,
	location point not null,
	title varchar(255) not null
);

DROP TABLE IF EXISTS sportground_sport_types;

create table sportground_sport_types
(
	sportground_id bigint not null
		constraint fk9tqum2ai20tvt84ax4t2jov6p
			references sportgrounds,
	sport_type_id smallint not null
		constraint fkr7ubsrb22peqtauunliodxc9a
			references sport_types
);

DROP TABLE IF EXISTS events;

create table events
(
	id bigserial not null
		constraint events_pkey
			primary key,
	date date not null,
	start_time time not null,
	end_time time,
	chat_id bigint,
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
