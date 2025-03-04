create table books
(
    id         bigint generated by default as identity
        primary key,
    annotation text,
    author     varchar(255),
    genre      varchar(255),
    image_book varchar(255),
    name       varchar(255),
    pages      integer          not null,
    book_id    bigint generated by default as identity,
    price      double precision not null
);