package com.surelution.zjolap

class SalesStockBalance extends StockBalance {

    static constraints = {
    }
	static mapping = {
        discriminator value: "sales"
    }
	
	SalesOrder salesOrder
}
