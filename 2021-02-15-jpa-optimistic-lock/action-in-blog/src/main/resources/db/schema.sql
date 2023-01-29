drop table if exists post CASCADE
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 2 increment by 1
create table post (id bigint not null, contents varchar(255), title varchar(255), version_no bigint, primary key (id))
