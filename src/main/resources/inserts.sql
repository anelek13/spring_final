insert into roles (id, created_at, deleted_at, updated_at, name) values (nextval('s_roles'), '2019-07-14 11:45:49.344428', null, '2019-07-14 11:45:49.344428', 'ROLE_ADMIN');
insert into roles (id, created_at, deleted_at, updated_at, name) values (nextval('s_roles'), '2019-07-14 11:46:01.432630', null, '2019-07-14 11:46:01.432630', 'ROLE_TEACHER');
insert into roles (id, created_at, deleted_at, updated_at, name) values (nextval('s_roles'), '2019-07-14 11:46:05.958924', null, '2019-07-14 11:46:05.958924', 'ROLE_STUDENT');

INSERT INTO users (id, created_at, deleted_at, updated_at, first_name, last_name, login, password, phone_number, role_id) VALUES (nextval('s_users'), '2019-07-14 18:25:32.856000', null, '2019-07-14 18:25:32.856000', 'admin', 'admin', 'admin', '$2a$10$ucIJJWRSsGpF8LbZZP4GbeYjoKmSN9ADD3W3rA3xNsfJIVaK1Mv2q', '8777-777-77-77', 1);
INSERT INTO users (id, created_at, deleted_at, updated_at, first_name, last_name, login, password, phone_number, role_id) VALUES (nextval('s_users'), '2019-07-14 18:25:32.856000', null, '2019-07-14 18:25:32.856000', 'admin2', 'admin2', 'admin2', '$2a$10$ucIJJWRSsGpF8LbZZP4GbeYjoKmSN9ADD3W3rA3xNsfJIVaK1Mv2q', '8777-777-77-77', 1);
