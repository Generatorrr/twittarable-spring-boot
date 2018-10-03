create table account_subscriptions (
  subscriber_id int8 not null references account,
  channel_id int8 not null references account,
  primary key (channel_id, subscriber_id)
)