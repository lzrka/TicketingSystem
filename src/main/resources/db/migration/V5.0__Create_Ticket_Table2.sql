create table user_ticket (

    user_id integer,
    ticket_id integer,
    primary key (user_id, ticket_id)

);

alter table user_ticket add constraint FKticket_id foreign key (ticket_id) references ticket(id);
alter table user_ticket add constraint Fkuser_id foreign key (user_id) references user(id);
