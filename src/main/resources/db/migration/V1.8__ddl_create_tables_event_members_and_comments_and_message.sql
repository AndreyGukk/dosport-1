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

DROP TABLE IF EXISTS sportground_comments;

create table sportground_comments
(
	id bigserial not null
		constraint sportground_comments_pkey
			primary key,
	date date not null,
	text varchar(255) not null,
	user_full_name varchar(255) not null,
	user_id bigint not null,
	sportground_id bigint not null
		constraint fk5ixkf3pbbkdrwrcw56h6x1y86
			references sportgrounds
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