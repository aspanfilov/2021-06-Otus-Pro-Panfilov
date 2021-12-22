create table client (
    id bigserial not null primary key,
    name varchar(255) not null
);

create table address (
    id bigserial not null primary key,
    client_id bigint references client (id),
    country varchar(50) not null,
    city varchar(50),
    street varchar(50),
    house_number integer,
    building_number integer,
    apartment_number integer
);

create table phone (
    id bigserial not null primary key,
    client_id bigint references client (id),
    number varchar(50) not null
);
