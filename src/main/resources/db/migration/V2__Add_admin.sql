insert into account (id, username, password, active, email)
  values (0, 'admin', 'admin', true, 'admin@gmail.com');

insert account_role (account_id, roles)
  values (0, 'USER'), (0, 'ADMIN');