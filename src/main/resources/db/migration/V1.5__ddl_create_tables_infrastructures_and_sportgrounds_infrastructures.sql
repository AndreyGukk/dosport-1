DROP TABLE IF EXISTS infrastructures;

create table infrastructures
(
	id              SERIAL not null UNIQUE,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS sportground_infrastructures;

create table sportground_infrastructures
(
	sportground_id  bigint not null
		constraint fk_sportground_id references sportgrounds,
	infrastructure_id integer not null
		constraint fk_infrastructure_id references infrastructures
);