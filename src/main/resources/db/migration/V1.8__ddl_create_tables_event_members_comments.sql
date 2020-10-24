DROP TABLE IF EXISTS event_member;

create table event_member
(
	event_id bigint not null
		constraint fkef0fc2wj4p4m7dd6h25hpnxkm
			references events,
	users_id bigint not null
		constraint uk_7pq3uhr4meu5l0ll87xrtvnhv
			unique
		constraint fkfrv833vdxaa4clyh122liby6h
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