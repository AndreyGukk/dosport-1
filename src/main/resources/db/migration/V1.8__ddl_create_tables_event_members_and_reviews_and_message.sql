DROP TABLE IF EXISTS event_members;

create table event_members
(
	id bigserial not null
		constraint event_members_pkey
			primary key,
	status varchar(255),
	user_id bigint,
	event_id bigint
		constraint fkaa8h6gi3vaheiptt7ben9qgn7
			references events
);

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