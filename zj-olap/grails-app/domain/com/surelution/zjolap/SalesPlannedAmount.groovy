package com.surelution.zjolap

class SalesPlannedAmount {

    static constraints = {
		sales(unique:'month')
    }
	
	static mapping = {
		month column:'_month'
	}

	Sales sales
	Float amount
	Date month
	Date dateCreated
}
