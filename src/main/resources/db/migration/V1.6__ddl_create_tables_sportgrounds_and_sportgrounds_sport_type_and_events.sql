DROP TABLE IF EXISTS sportgrounds;

create table sportgrounds
(
	id bigserial not null
		constraint sportgrounds_pkey
			primary key,
	city varchar(100) not null,
	address varchar(255) not null
		constraint uk_kvtbkuiwnrkn35yqkyi5fqn42
		    unique,
	title varchar(150) not null,
    latitude double precision not null,
	longitude double precision not null,
	metro_station smallint,
	surface_type smallint,
	rent_price integer,
	opened boolean
);

DROP TABLE IF EXISTS sportground_sport_types;

create table sportground_sport_types
(
	sport_type_id smallint not null
		constraint fktjnnc0fyeq1e35dyco54wf3qs
			references sport_types,
	sportground_id bigint not null
		constraint fk3rsawq4m4t1gmfa98kcgdo1f9
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
	end_time time,
	organizer_user_id bigint not null,
	start_time time not null,
	sportground_id bigint not null
		constraint fk6966vhtxb5471hh2ij5lgfuk
			references sportgrounds,
	sport_type_id smallint not null
		constraint fk33979w9uvke4fjm824mu07qdi
			references sport_types
);

DROP TABLE IF EXISTS infrastructures;

create table infrastructures
(
	id integer not null
	    primary key
);

DROP TABLE IF EXISTS sportground_infrastructures;

create table sportground_infrastructures
(
	sportground_id bigint not null
		constraint fk_sportground_id
			references sportgrounds,
	infrastructure_id integer not null
		constraint fk_infrastructure_id
			references infrastructures
);