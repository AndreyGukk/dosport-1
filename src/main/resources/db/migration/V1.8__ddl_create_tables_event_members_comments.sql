DROP TABLE IF EXISTS event_member;

create table event_member
(
	id bigserial not null
		constraint event_member_pkey
			primary key,
	status varchar(255),
	event_id bigint
		constraint fke7ttqbdxnpwren6fg3wktsash
			references event_member,
	user_id bigint
		constraint fkoobns1hxage1y1gcnf9ol8y2m
			references users
);

DROP TABLE IF EXISTS comments_sportground;

create table comments_sportground
(
	id bigserial not null
		constraint comments_sportground_pkey
			primary key,
	date date not null,
	text varchar(255) not null,
	id_sportground bigint not null
		constraint fkqfh1cd10s9tm56hos97e5l630
			references sportgrounds,
	id_user bigint not null
		constraint fkn1pmgagnamc008wh5mpoy87n6
			references users
);