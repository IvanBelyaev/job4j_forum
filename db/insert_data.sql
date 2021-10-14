insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, password, email, authority_id)
values ('root', '$2a$10$sOlchXROmocEP82VQY5Q1ed9kkp8ps..gmwN4H1E27WZQ/Djk0p16', 'root@mail.ru',
        (select id from authorities where authority = 'ROLE_ADMIN')),
       ('user', '$2a$10$ybieMrRv1aMqrn7yNW2rBuE9HZtpDxUY5kiE.cpBXDP6KGcC6leEe', 'user@mail.ru',
        (select id from authorities where authority = 'ROLE_USER'));

insert into posts (name, description, author_id)
values ('О чем этот форум?', 'Основные темы форума',
        (select id from users where username = 'root')),
       ('Правила форума', 'Здесь собраны все правила форума',
        (select id from users where username = 'root'));
