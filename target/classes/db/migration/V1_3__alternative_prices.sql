create table IF NOT EXISTS STOCKS_HISTORIC_PRICES(
                                                     id bigint primary key GENERATED ALWAYS AS IDENTITY,
                                                     id_stock bigint not null,
                                                     open numeric default 0,
                                                     close numeric default 0,
                                                     high numeric default 0,
                                                     low numeric default 0,
                                                     created_on timestamp not null default current_timestamp
);