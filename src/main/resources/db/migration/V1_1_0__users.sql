create table users
(
    id       bigint generated by default as identity
        primary key,
    email    varchar(255)
        constraint uk6dotkott2kjsp8vw4d0m25fb7
            unique,
    fullname varchar(255),
    password varchar(255),
    role     varchar(255),
    user_id  bigint generated by default as identity
);