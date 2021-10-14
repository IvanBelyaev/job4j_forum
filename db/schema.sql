CREATE TABLE authorities (
    id serial PRIMARY KEY,
    authority varchar(50) NOT NULL UNIQUE
);

create table users(
    id serial PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    email varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    authority_id integer NOT NULL,
    enabled boolean default true,
    credentials_non_expired boolean default true,
    account_non_locked boolean default true,
    account_non_expired boolean default true,
    FOREIGN KEY (authority_id) REFERENCES authorities(id)
);

create table posts(
    id serial PRIMARY KEY,
    name varchar(2000) not null,
    description varchar(2000) not null,
    created timestamp without time zone not null default now(),
    author_id integer not null,
    FOREIGN KEY (author_id) REFERENCES users(id)
);

create table comments(
    id serial PRIMARY KEY,
    text varchar(2000) not null,
    user_id integer not null,
    created timestamp without time zone not null default now(),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

create table posts_comments(
    post_id integer NOT NULL,
    comment_id integer NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (comment_id) REFERENCES comments(id),
    PRIMARY KEY (post_id, comment_id)
);
