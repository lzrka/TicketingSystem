create table activity (

    id           integer not null auto_increment,
    modified_by         varchar(255) not null,
    code            varchar(255) not null,
    modification    varchar(255) not null,
    up_dated_at     timestamp not null,
    primary key (id))
