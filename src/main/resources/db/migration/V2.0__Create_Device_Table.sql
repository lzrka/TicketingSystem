create table device
(
    id              integer not null auto_increment,
    code            varchar(255) not null,
    type            varchar(255) not null,
    model           varchar(255) not null,
    user_id         integer,
    primary key (id)


);

alter table device
    add constraint FK_user_id foreign key (user_id) references user(id);





