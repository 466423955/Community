create table comment
(
	id bigint auto_increment,
	parent_id bigint not null,
	parent_type int not null,
	commentator bigint not null,
	gmt_create bigint not null,
	gmt_modify bigint not null,
	like_count bigint default 0,
	description text,
	constraint comment_pk
		primary key (id)
);