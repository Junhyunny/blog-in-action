drop table if exists post CASCADE
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 1 increment by 1
create table post (postId bigint not null, postContent varchar(255), postTitle varchar(255), postTp varchar(255), primary key (postId))