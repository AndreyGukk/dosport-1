DROP TABLE IF EXISTS sportground_infrastructures;

create table sportground_infrastructures
(
	sportground_id      bigint not null,
	infrastructure_id   smallint not null,
	PRIMARY KEY (sportground_id, infrastructure_id),
	FOREIGN KEY (sportground_id) REFERENCES sportgrounds (id) ON DELETE CASCADE,
	CONSTRAINT valid_infrastructure_id CHECK (infrastructure_id >= 0 AND infrastructure_id <= 8)
);