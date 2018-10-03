insert into account (id, email, username, password, active)
    values (1, 'admin', 'admin@dev.com', '123456', true);

insert into account_role (account_id, roles)
    values (1, 'USER'), (1, 'ADMIN');