create table Company
(
    company_id          serial      not null,
    company_name        varchar(50) not null,
    stock_price         float       not null,
    company_stock_count int         not null,

    constraint Company_PK primary key (company_id),
    constraint Company_K1 unique (company_name),
    constraint Company_positive_stock_price check (stock_price > 0),
    constraint Company_positive_stock_count check (company_stock_count > 0)
);

create table Client
(
    client_id    serial      not null,
    client_login varchar(50) not null,
    money        float       not null,

    constraint Client_PK primary key (client_id),
    constraint Client_K1 unique (client_login)
);

create table ClientStock
(
    client_id          serial not null,
    company_id         serial not null,
    client_stock_count int    not null,

    constraint ClientStock_FK1 foreign key (client_id) references Client (client_id) deferrable initially immediate,
    constraint ClientStock_FK2 foreign key (company_id) references Company (company_id) deferrable initially immediate,
    constraint ClientStock_positive_stock_count check (client_stock_count > 0)
);
