DROP TABLE IF EXISTS sportgrounds;

create table sportgrounds
(
	id              bigserial not null
		            constraint sportgrounds_pkey primary key,
	city            varchar(100) not null,
	address         varchar(255) not null unique,
	title           varchar(150) not null,
    latitude        double precision not null,
	longitude       double precision not null,
	metro_station   smallint,
	surface_type    smallint,
	rent_price      integer,
	opened          boolean
);

DROP TABLE IF EXISTS sportground_sport_types;

create table sportground_sport_types
(
	sport_type_id   smallint not null
		constraint  fk_sport_type references sport_types,
	sportground_id  bigint not null
		constraint  fk_sportground_id references sportgrounds
);

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

DROP TABLE IF EXISTS infrastructures;

create table infrastructures
(
	id integer not null
	    primary key
);

DROP TABLE IF EXISTS sportground_infrastructures;

create table sportground_infrastructures
(
	sportground_id  bigint not null
		constraint fk_sportground_id references sportgrounds,
	infrastructure_id integer not null
		constraint fk_infrastructure_id references infrastructures
);