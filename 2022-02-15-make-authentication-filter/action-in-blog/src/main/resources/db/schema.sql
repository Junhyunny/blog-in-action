drop table if exists tb_user CASCADE;

create table tb_user
(
    user_name varchar(255) not null,
    password  varchar(255),
    authority varchar(255),
    primary key (user_name)
);