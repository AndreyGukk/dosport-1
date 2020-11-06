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

DROP TABLE IF EXISTS sportground_comments;

create table sportground_comments
(
	id bigserial not null
		constraint comments_sportground_pkey
			primary key,
	user_id bigint not null
    		constraint fkn1pmgagnamc008wh5mpoy87n6
    			references users,
    user_full_name varchar(255) not null,
    sportground_id bigint not null
    	constraint fkqfh1cd10s9tm56hos97e5l630
    		references sportgrounds,
	date date not null,
	text varchar(255) not null
);

DROP TABLE IF EXISTS message_event;

create table message_event
(
	id bigserial not null
		constraint message_event_pkey
			primary key,
	text varchar(255),
	user_id bigint,
	user_name varchar(255),
	event_id bigint not null
		constraint fklhhl7j0qe0wkj7i2e687ygymh
			references events
);