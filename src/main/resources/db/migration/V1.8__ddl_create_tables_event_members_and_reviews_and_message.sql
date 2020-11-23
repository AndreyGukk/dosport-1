DROP TABLE IF EXISTS event_members;

create table event_members
(
	id bigserial not null
		constraint event_members_pkey
			primary key,
	status varchar(13) not null,
	user_id bigint not null,
	event_id bigint not null
		constraint fkaa8h6gi3vaheiptt7ben9qgn7
			references events
				on delete cascade
);

create unique index if not exists event_members_user_id_event_id_uindex
	on event_members (user_id, event_id);

DROP TABLE IF EXISTS reviews;

create table reviews
(
	id bigserial not null
		constraint reviews_pkey
			primary key,
	date date not null,
	sportground_id bigint not null
		constraint fkg4mnw05ok6evcd500po2onhmh
			references sportgrounds,
	text varchar(255) not null,
	user_full_name varchar(255) not null,
	user_id bigint not null
);

DROP TABLE IF EXISTS event_messages;

create table event_messages
(
	id bigserial not null
		constraint event_messages_pkey
			primary key,
	text varchar(255) not null,
	user_id bigint not null,
	user_name varchar(255),
	event_id bigint not null
		constraint fk9k2alyqybwcu55iwi8v18hjy
			references events
				on delete cascade
);