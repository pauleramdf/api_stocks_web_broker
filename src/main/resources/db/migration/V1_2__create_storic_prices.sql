create table IF NOT EXISTS STOCKS_HISTORIC_PRICES(
                                     id bigint primary key GENERATED ALWAYS AS IDENTITY,
                                     id_stock bigint not null,
                                     ask_min numeric default 0,
                                     ask_max numeric default 0,
                                     bid_min numeric default 0,
                                     bid_max numeric default 0,
                                     created_on timestamp not null default current_timestamp
);