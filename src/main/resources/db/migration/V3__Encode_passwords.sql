create extension if not exists pgcrypto;

update account set password = crypt(password, gen_salt('bf', 8));