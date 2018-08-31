drop table if exists account;
drop table if exists account_role;
drop table if exists hibernate_sequence;
drop table if exists message;

create table account (
  id bigint not null,
  activation_code varchar(255),
  active bit not null,
  email varchar(255) not null,
  password varchar(255) not null,
  username varchar(255) not null,
  primary key (id)
);

create table account_role (
  account_id bigint not null,
  roles varchar(255)
);

create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

create table message (
  id bigint not null,
  filename varchar(255),
  tag varchar(255),
  text varchar(2048) not null,
  user_id bigint,
  primary key (id)
);

alter table account_role
  add constraint account_role_account_fk
  foreign key (account_id) references account (id);
  
alter table message
  add constraint message_account_fk
  foreign key (user_id) references account (id);