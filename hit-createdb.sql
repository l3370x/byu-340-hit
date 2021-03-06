drop table if exists product_container;
drop table if exists product;
drop table if exists item;
drop table if exists removed_items_report;
drop table if exists product_product_container;

create table product_container
(
	id INTEGER not null primary key autoincrement,
	name varchar(255) not null,
	is_storage_unit boolean,
	_3_month_supply_amt real(10,3),
	_3_month_supply_unit varchar(255),
	parent_container INTEGER constraint fk_parent REFERENCES product_container (id)
);

create table product
(
	barcode varchar(12) not null primary key,
	description varchar(255) not null,
	size_amt real(10,3),
	size_unit varchar(255),
	shelf_life_months int,
	_3_month_supply int,
	create_date date not null
);

create table product_product_container
(
	product_id varchar(12) not null constraint fk_product REFERENCES product (barcode) ON DELETE CASCADE,
	product_container_id INTEGER not null constraint fk_container REFERENCES product_container (id),
	primary key (product_id, product_container_id)
);

create table item
(
	barcode varchar(12) not null primary key,
	product varchar(12) not null constraint fk_item_product REFERENCES product (barcode),
	entry_date date not null,
	product_container int,
	removed_date date
);

create table removed_items_report
(
	id INTEGER not null primary key,
	last_report_run date
);


