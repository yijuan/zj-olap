package com.surelution.zjolap

class CustomerStockLog {
	CustomerBranch customerBranch
	double stockQty
	TimeByDay date
	GasType gasType
	CustomerStock updateFrom
	String optionType
	
	static String OPTION_VALUE_EXCEL_ADD = "EXCEL_ADD"
	static String OPTION_VALUE_ADD = "ADD"
	static String OPTION_VALUE_DELETE = "DELETE"
	static String OPTION_VALUE_UPDATE = "UPDATE"
	
	
    static constraints = {
		
    }
	
	String status	
}
