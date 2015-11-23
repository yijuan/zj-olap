ALTER TABLE sales_order ADD INDEX sales_order_query_index (customer_branch_id,gas_type_id,time_by_day_id);
alter table init_customer_stock add index init_customer_stock_query_index(customer_branch_id,gas_type_id)
alter table customer_stock add index customer_stock_query_index(customer_branch_id,gas_type_id,date_id )






create view all_customer_branch_gasType as

select  ics.customer_branch_id,ics.gas_type_id,ics.date_id,(select date_format(t.c_date,'%Y-%m-%d') from time_by_day t where t.id =ics.date_id) str_date from init_customer_stock ics 
union 
select cs.customer_branch_id,cs.gas_type_id,cs.date_id ,(select date_format(t.c_date,'%Y-%m-%d') from time_by_day t where t.id =cs.date_id) str_date from  customer_stock cs where cs.status = 'ABLE' and cs.is_vail = true
union 
select  so.customer_branch_id,so.gas_type_id,so.time_by_day_id,(select date_format(t.c_date,'%Y-%m-%d') from time_by_day t where t.id =so.time_by_day_id) str_date from sales_order so where  so.status = 'ABLE' and so.is_vail = true








create view customer_stock_view as


select 
a.customer_branch_id,
a.gas_type_id,
a.date_id,
a.str_date,
(select branch_id from customer_branch cb where cb.id = a.customer_branch_id) branch_id,
(select customer_id from customer_branch cb where cb.id = a.customer_branch_id) customer_id,
(select ifnull(sum(so.quantity),0) from sales_order so where   so.status = 'ABLE' and so.is_vail = true and so.customer_branch_id=a.customer_branch_id and so.gas_type_id = a.gas_type_id and so.time_by_day_id = a.date_id) so_qty,
(select ifnull(sum(ics.stock_qty),0) from init_customer_stock ics where ics.customer_branch_id=a.customer_branch_id and ics.gas_type_id = a.gas_type_id) init_qty,
(select ifnull(sum(cs.stock_qty),0) from customer_stock cs where cs.customer_branch_id=a.customer_branch_id and cs.gas_type_id = a.gas_type_id and  cs.date_id=a.date_id )  sell_qty,
(select ifnull(sum(cs.stock_qty),0) from customer_stock cs where cs.customer_branch_id=a.customer_branch_id and cs.gas_type_id = a.gas_type_id and   date_format(cs.cdate,'%Y-%m-%d')<=a.str_date)  sell_all_qty,
(select ifnull(sum(so.quantity),0) from sales_order so where   so.status = 'ABLE' and so.is_vail = true and so.customer_branch_id=a.customer_branch_id and so.gas_type_id = a.gas_type_id and date_format(so.saling_at,'%Y-%m-%d')  <= a.str_date) so_all_qty
from all_customer_branch_gasType  a
order by a.str_date


