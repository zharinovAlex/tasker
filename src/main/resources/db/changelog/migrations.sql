--liquibase formatted sql

--changeset nvoxland:1
create table test1 (
    id int primary key,
    name varchar(255)
);
--rollback drop table test1;

--changeset nvoxland:2
insert into test1 (id, name) values (1, "name 1");
insert into test1 (id, name) values (2, "name 2");

--changeset tasker:1
drop table test1;
--rollback create table test1 (id int primary key);

--changeset tasker:2
create table user (
    id bigint(20) not null primary key AUTO_INCREMENT,
    first_name varchar(100) not null,
    last_name varchar(150) not null,
    email_address varchar(255) not null,
    created_at timestamp default current_timestamp ,
    updated_at timestamp default current_timestamp ,
    active bit,
    role int,
    team int,
    UNIQUE (email_address)
);

--changeset tasker:3
create table task (
    id bigint(20) not null primary key AUTO_INCREMENT,
    name varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    description varchar(1000),
    status int,
    created_by_id bigint(20) references user(id),
    user_id bigint(20) references user(id)
);