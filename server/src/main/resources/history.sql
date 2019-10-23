-- date 2019-10-04 23:43:31
create table raw_log
(
    time    timestamp with time zone not null,
    type    text                     not null,
    raw_str text                     not null
);

alter table raw_log
    owner to postgres;

create index raw_log_time_idx
    on raw_log (time desc);

create trigger ts_insert_blocker
    before insert
    on raw_log
    for each row
execute procedure "_timescaledb_internal".insert_blocker();


create table raw_data
(
    time       timestamp with time zone not null,
    type       text                     not null,
    account_id text                     not null,
    raw_str    text                     not null
);

alter table raw_data
    owner to postgres;

create index raw_data_time_idx
    on raw_data (time desc);

create trigger ts_insert_blocker
    before insert
    on raw_data
    for each row
execute procedure "_timescaledb_internal".insert_blocker();

-- date 2019-10-04 23:50:32
create table item_desc
(
    id          text not null,
    name        text not null,
    item_class  text not null,
    sub_class   text,
    item_lv     integer,
    require_lv  integer,
    vendor_buy  integer,
    vendor_sell integer,
    icon        text
);

create index item_desc_id_idx
    on item_desc (id);

-- date 2019-10-08 09:17:41
create table item_scan
(
    item_id      text                     not null,
    min_buyout   integer                  not null,
    market_value integer                  not null,
    num_auctions integer                  not null,
    quantity     integer                  not null,
    scan_time    timestamp with time zone not null,
    realm        text                     not null,
    add_time     timestamp with time zone not null
);

SELECT create_hypertable('item_scan', 'scan_time');

create index item_scan_scan_time_item_id_idx
    on item_scan (scan_time DESC, item_id);

-- date 2019-10-22 12:23:46
create table account_stats
(
    account_id text                     not null,
    chars      text                     not null,
    power      integer,
    last_push  timestamp with time zone not null

);

create index account_stats_account_id_idx
    on account_stats (account_id);

-- date 2019-10-23 11:27:44
CREATE TABLE biz_cache
(
    id        text,
    cache_str text
);
