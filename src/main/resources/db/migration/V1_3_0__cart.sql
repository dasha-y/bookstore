create table cart_books
(
    id      bigint generated by default as identity
        primary key,
    book_id bigint
        constraint fk49eoh0fay9phyalg4fodf29jx
            references books,
    user_id bigint
        constraint fkc933ynqkl9pt49ir45ljmbl40
            references users
);