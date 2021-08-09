create table ticket
(

    id          integer not null auto_increment,
    code        varchar(255) not null,
    created_at  date         not null,
    description varchar(255) not null,
    priority    varchar(255) not null,
    status      varchar(255) not null,
    title       varchar(255) not null,
    up_dated_at dateTime         not null,
    assigne_id  integer,
    solution    varchar(255),
    primary key (id)

);


alter table ticket add constraint fk_assigne_id foreign key (assigne_id) references agent(id);

