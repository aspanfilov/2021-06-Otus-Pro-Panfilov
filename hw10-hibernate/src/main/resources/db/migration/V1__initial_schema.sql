-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence hibernate_sequence start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id bigint
);

create table address (
    id bigint generated by default as identity primary key,
    country varchar(50) not null,
    region varchar(50),
    city varchar(50),
    street varchar(50),
    house_number integer,
    building_number integer,
    apartment_number integer
);

create table phone (
    id bigint generated by default as identity primary key,
    number varchar(255) not null,
    client_id bigint
)

--alter table address
--add constraint UK_fx5oeyyotqc1jurjbmt9pwgjq unique (country)
--
--alter table courses
--add constraint UK_5o6x4fpafbywj4v2g0owhh11r unique (name)
--
--alter table emails
--add constraint UK_1u6nfbte7kqw4vwsaw6pq0qyh unique (email)
--
--alter table phone
--add constraint UK_jpobbsduo00bgyro8gurj7for unique (number)

--alter table otus_students
--add constraint FK2lk44ooibcyrfql3jb3eesv1c
--foreign key (address_id)
--references address
--
--alter table phone
--add constraint FK6ll1pon8k6l6063rje3ayppwj
--foreign key (client_id)
--references otus_students