-- date 2019-10-04 23:43:31
create table raw_log
(
	time timestamp with time zone not null,
	type text not null,
	raw_str text not null
);

alter table raw_log owner to postgres;

create index raw_log_time_idx
	on raw_log (time desc);

create trigger ts_insert_blocker
	before insert
	on raw_log
	for each row
	execute procedure "_timescaledb_internal".insert_blocker();


create table raw_data
(
	time timestamp with time zone not null,
	type text not null,
	account_id text not null,
	raw_str text not null
);

alter table raw_data owner to postgres;

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
    id text not null,
    name text not null,
    item_class text not null,
    sub_class text,
    item_lv integer,
    require_lv integer,
    vendor_buy integer,
    vendor_sell integer,
    icon text
);

create index item_desc_id_idx
    on item_desc (id);