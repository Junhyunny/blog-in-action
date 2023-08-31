create table if not exists tb_user (
    id serial primary key,
    name varchar(50)
);

insert into tb_user (name) values ('Junhyunny');
insert into tb_user (name) values ('Jua');
