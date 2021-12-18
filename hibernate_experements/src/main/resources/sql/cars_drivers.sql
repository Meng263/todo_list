create schema if not exists cars;

create table cars.car
(
    id        serial primary key,
    name      text not null,
    engine_id int  not null references cars.engine (id) unique
);

create table cars.engine
(
    id   serial primary key,
    name text
);

create table cars.driver
(
    id   serial primary key,
    name text
);

create table cars.history_owner
(
    id        serial primary key,
    car_id    int not null references cars.car (id),
    driver_id int not null references cars.driver (id)
);
