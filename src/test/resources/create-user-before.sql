delete from user_subscriptions;
delete from message;
delete from user_role;
delete from usr;

insert into usr (id, active, password, username) values
(1, true, '$2a$08$ljreucnvLaSPdb6by8/tCuFXW99GL2ltAmfUA2hzkZElLpIJM25a6', 'e'),
(2, true, '$2a$08$ljreucnvLaSPdb6by8/tCuFXW99GL2ltAmfUA2hzkZElLpIJM25a6', 'a');

insert into user_role (user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');
