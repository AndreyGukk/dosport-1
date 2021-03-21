DROP TABLE IF EXISTS sportgrounds;

create table sportgrounds
(
	id              bigserial not null UNIQUE,
	city            varchar(100) not null,
	address         varchar(255) not null unique,
	title           varchar(150) not null,
    latitude        double precision not null,
	longitude       double precision not null,
	metro_station   smallint,
	surface_type    smallint,
	rent_price      integer,
	opened          boolean not null,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS sportground_sport_types;

create table sportground_sport_types
(
	sportground_id  bigint not null,
	sport_type_id   smallint not null,
	PRIMARY KEY (sportground_id, sport_type_id),
    FOREIGN KEY (sportground_id) REFERENCES sportgrounds (id),
    FOREIGN KEY (sport_type_id) REFERENCES sport_types (id)
);