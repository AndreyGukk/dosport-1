DROP TABLE IF EXISTS infrastructures;

create table infrastructures
(
	id                  SERIAL not null UNIQUE,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS sportground_infrastructures;

create table sportground_infrastructures
(
	sportground_id      bigint not null,
	infrastructure_id   integer not null,
	PRIMARY KEY (sportground_id, infrastructure_id),
	FOREIGN KEY (sportground_id) REFERENCES sportgrounds (id) ON DELETE CASCADE,
	FOREIGN KEY (infrastructure_id) REFERENCES infrastructures (id) ON DELETE CASCADE
);