create table Clients
(
    client_id serial      not null,
    name      varchar(50) not null,

    constraint Client_PK primary key (client_id)
);

create table Subscriptions
(
    subscription_id serial not null,
    client_id       int    not null,

--  Пока Считаю, что у клиента должна быть одна подписка. В будущем это стоит изменить, но возникают вопросы,
--  могут ли одновременно действовать 2 подписки? Если да, то будут сложнее запросы на проверку прав на вход...
    constraint Subscriptions_PK primary key (subscription_id),
    constraint Subscriptions_K1 unique (client_id),
    constraint Subscriptions_FK1 foreign key (client_id) references Clients (client_id) deferrable initially immediate
);

-- TODO: Разрешать ли продлевать подписку, которая ещё действует?
-- Конечно, разрешать, но как это хранить?..
-- В поле until последней записи хранить итоговое время точного окончания
-- действия подписки суммарно после всех продлений?
create table SubscriptionEvents
(
    subscription_id      int       not null,
--  true, если продление существующей подписки. false, если покупка подписки
    extension_of_existed boolean   not null,
    time                 timestamp not null,
--  Дата окончания действия абонемента. В этот день начиная с 00:00 подписка приостанавливает действие
--  Поле until - date, хотя time - timestamp, потому что в один день могут происходить несколько событий с абонементом,
--  однако его действие длится в днях и нет смысла хранить в поле until дату и время.
    until                date      not null,

    constraint SubscriptionEvents_PK primary key (subscription_id, time),
    constraint SubscriptionEvents_FK1 foreign key (subscription_id) references Subscriptions (subscription_id)
);

create table TurnstileEvents
(
--  true, если вход. false, если выход
    in_             boolean   not null,
--  Считаю, что турникет пропускает абонемент (пластиковую карту например, или штрих код в приложении)
    subscription_id int       not null,
    time            timestamp not null,
--  Для out событий - время входа
    previous_time   timestamp,

    constraint SubscriptionEvents_P1 primary key (subscription_id, time),
    constraint SubscriptionEvents_FK1 foreign key (subscription_id) references Subscriptions (subscription_id)
);

create index Clients_СId on Clients using hash (client_id);
create index Subscriptions_СId on Subscriptions using hash (client_id);
create index Subscriptions_SId on Subscriptions using hash (subscription_id);
create index SubscriptionEvents_SId_time on SubscriptionEvents using btree (subscription_id, time);
create index TurnstileEvents_SId_time on TurnstileEvents using btree (subscription_id, time);
