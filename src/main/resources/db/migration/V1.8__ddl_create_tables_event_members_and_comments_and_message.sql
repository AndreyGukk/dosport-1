DROP TABLE IF EXISTS event_members;

create table event_members
(
	id bigserial not null
		constraint event_member_pkey
			primary key,
	status varchar(255),
	event_id bigint
		constraint fke7ttqbdxnpwren6fg3wktsash
			references events,
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

DROP TABLE IF EXISTS event_messages;

create table event_messages
(
	id bigserial not null
		constraint event_message_pkey
			primary key,
	text varchar(255) not null,
	user_id bigint not null
	    constraint fkn1pmgagnamc008wh5myoy87n6
            references users,
	user_name varchar(255),
	event_id bigint not null
		constraint fkd36re9dyewxsuy0kp0om139t4
			references events
				on delete cascade
);