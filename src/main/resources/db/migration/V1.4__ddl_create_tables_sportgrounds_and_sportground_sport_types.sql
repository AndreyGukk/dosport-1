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