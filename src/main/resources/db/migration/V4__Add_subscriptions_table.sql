create table account_subscriptions (
  subscriber_id bigint not null references account,
  channel_id bigint not null references account
  primary key (channel_id, subscriber_id)
)