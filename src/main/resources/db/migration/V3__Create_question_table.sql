create table question
(
	id BIGINT auto_increment primary key,
	title varchar(50),
	description text,
	gmt_create bigint,
	gmt_modify bigint,
	creator bigint,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(256)
);

