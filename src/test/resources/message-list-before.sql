delete from message;

insert into message (id, text, tag, user_id) values
(1, 'first', 'first_tag', 1),
(2, 'second', 'second_tag', 1),
(3, 'third', 'third_tag', 1),
(4, 'fourth', 'fourth_tag', 1),
(5, 'five', 'second_tag', 1);

alter sequence hibernate_sequence restart with 10;