drop table if exists type;


create table type (
	id bigint auto_increment,
	grp varchar(128) not null,
	name varchar(255) not null,
	detail varchar(255),
    primary key (id)
) engine=InnoDB;