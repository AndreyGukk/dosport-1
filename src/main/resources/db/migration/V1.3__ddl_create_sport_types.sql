DROP TABLE IF EXISTS sport_types;

create table sport_types
(
	id serial not null
		constraint sport_types_pkey
			primary key,
	title varchar(255) not null
		constraint uk_fdrclpm25ykaj8cgy59dxxauc
			unique
);