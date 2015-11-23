package com.surelution.zjolap

class ViewCustomerStock implements Serializable {

    long cuseromBranchId;
	long gasTypeId;
	long dateId;
	String strDate;
	long branchId;
	double soQty;
	double initQty;
	double sellQty;
	double sellAllQty;
	double soAllQty;
	
	
	static mapping = {
		table 'customer_stock_view'
		version false
		cache usage:'read-only',inlcude:'non-lazy'
	    id composite:['cuseromBranchId', 'gasTypeId', 'dateId']
		cuseromBranchId column:'customer_branch_id'
		gasTypeId column:'gas_type_id'
		dateId column:'date_id'
		strDate column:'str_date'
		branchId column:'branch_id'
		soQty column:'so_qty'
		initQty column:'init_qty'
		sellQty column:'sell_qty'
		sellAllQty column:'sell_all_qty'
		soAllQty column:'so_all_qty'
	}
}
