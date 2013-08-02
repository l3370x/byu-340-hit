drop table product_container;
drop table product;
drop table item;
drop table inventory;

create table product_container
{
	id int primary key autoincrement,
	name varchar(255) not null,
	is_storage_unit boolean,
	3_month_supply_amt real(3,3),
	3_month_supply_unit varchar(255),
	parent_container int
};

create table product
(
	barcode varchar(12) primary key,
	description varchar(255) not null,
	size_amt real(3,3),
	size_unit varchar(255),
	shelf_life_months int,
	create_date date not null
);

create table product_product_container
{
	product varchar(255) not null,
	product_container int not null
	constraint pk_prod_prod_cntnr primary key (product, product_container)
};

create table item
(
	barcode varchar(12) primary key,
	prod_barcode varchar(12) not null,
	entry_date date not null,
	product_container int,
	removed_date date
);

create table removed_items_report
(
	last_report_run date
);


