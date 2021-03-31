DROP TABLE IF EXISTS sportgrounds;

create table sportgrounds
(
	id              bigserial not null UNIQUE,
	city            varchar(100) not null,
	address         varchar(255) not null,
	title           varchar(150) not null,
    latitude        double precision not null,
	longitude       double precision not null,
	metro_station   smallint,
	surface_type    smallint,
	rent_price      integer,
	opened          boolean not null,
    opening_time    time with time zone,
    closing_time    time with time zone,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS sportground_sport_types;

create table sportground_sport_types
(
	sportground_id  bigint not null,
	sport_type_id   smallint not null,
	PRIMARY KEY (sportground_id, sport_type_id),
    FOREIGN KEY (sportground_id) REFERENCES sportgrounds (id) ON DELETE CASCADE,
    FOREIGN KEY (sport_type_id) REFERENCES sport_types (id) ON DELETE CASCADE
);