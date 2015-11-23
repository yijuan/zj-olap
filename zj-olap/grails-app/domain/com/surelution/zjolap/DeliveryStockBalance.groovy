package com.surelution.zjolap

class DeliveryStockBalance extends StockBalance {

    static constraints = {
    }
	static mapping = {
        discriminator value: "delivery"
    }
	
	DeliveryTaking taking
	
	public static DeliveryStockBalance createByTaking(DeliveryTaking _taking) {
		DeliveryStockBalance dsb = new DeliveryStockBalance()
		dsb.branch = _taking.branch
		dsb.customer = _taking.customer
		dsb.takingAt = _taking.takingAt
		dsb.gasType = _taking.gasType
		dsb.timeByDay = _taking.timeByDay
		dsb.quantity = -_taking.quantity
		dsb.taking = _taking
		dsb
	}
}
