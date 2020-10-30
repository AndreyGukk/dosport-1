DROP TABLE IF EXISTS event_member;

create table event_member
(
	id bigserial not null
		constraint event_member_pkey
			primary key,
	status varchar(255),
	user_id bigint,
	event_id bigint
		constraint fkef0fc2wj4p4m7dd6h25hpnxkm
			references events
);

DROP TABLE IF EXISTS comments_sportground;

create table comments_sportground
(
	id bigserial not null
		constraint comments_sportground_pkey
			primary key,
	date date not null,
	text varchar(255) not null,
	user_full_name varchar(255) not null,
	user_id bigint not null,
	sportground_id bigint not null
		constraint fkgodi1v6dukh2ktmfsgnx83m5y
			references sportgrounds
);