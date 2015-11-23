update sales_order set gas_type_id=7 where gas_type_id in (8,9,11,12,13,14,15,16,17);
update customer_stock set gas_type_id=7 where gas_type_id in (8,9,11,12,13,14,15,16,17);
update customer_stock_log set gas_type_id=7 where gas_type_id in (8,9,11,12,13,14,15,16,17);

-- double check whether there is stock with the gas_type_id
-- select * from init_customer_stock where gas_type_id in (8,9,11,12,13,14,15,16,17);
-- make sure stock_qty is 0
delete from init_customer_stock where gas_type_id in (8,9,11,12,13,14,15,16,17);

delete from price where type_id in (8,9,11,12,13,14,15,16,17);
delete from gas_type where id in (8,9,11,12,13,14,15,16,17);

ALTER TABLE `customer_type_level2` DROP FOREIGN KEY `FKD3078EF24D19E6F4`;

update customer set customer_type_id=1 where customer_type_id=14;
delete from customer_type_level3 where id=14;

update customer set customer_type_id=5 where customer_type_id=16;
delete from customer_type_level3 where id=16;
delete from customer_type_level2 where id=13;

update customer set customer_type_id=2 where customer_type_id=17;
delete from customer_type_level3 where id=17;
delete from customer_type_level2 where id=14;


update customer set customer_type_id=4 where customer_type_id=15;
update customer set customer_type_id=4 where customer_type_id=18;
update customer set customer_type_id=4 where customer_type_id=19;
delete from customer_type_level3 where id=15;
delete from customer_type_level3 where id=18;
delete from customer_type_level3 where id=19;
delete from customer_type_level2 where id=12;
delete from customer_type_level2 where id=15;
delete from customer_type_level2 where id=16;