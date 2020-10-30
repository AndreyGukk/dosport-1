DROP TABLE IF EXISTS sportgrounds;

create table sportgrounds
(
	id bigserial not null
		constraint sportgrounds_pkey
			primary key,
	address varchar(255) not null
		constraint uk_kvtbkuiwnrkn35yqkyi5fqn42
			unique,
	location point not null,
	title varchar(255) not null
);

DROP TABLE IF EXISTS sportgrounds_sport_type;

create table sportground_sport_type
(
	sport_type_id smallint not null
		constraint fkgsbbap0fjt33b89b4b6cp6rd5
			references sport_types,
	sportground_id bigint not null
		constraint fk96cds45cx49j3wyh4dginho21
			references sportgrounds
);

DROP TABLE IF EXISTS events;

create table events
(
	id bigserial not null
		constraint events_pkey
			primary key,
	chat_id bigint,
	date date not null,
	end_time timestamp,
	organizer_user_id bigint not null,
	start_time timestamp not null,
	sportground_id bigint not null
		constraint fk6966vhtxb5471hh2ij5lgfuk
			references sportgrounds,
	sport_type_id smallint not null
		constraint fk33979w9uvke4fjm824mu07qdi
			references sport_types
);