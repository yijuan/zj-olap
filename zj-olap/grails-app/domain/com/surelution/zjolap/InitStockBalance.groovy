package com.surelution.zjolap

class InitStockBalance extends StockBalance {

    static constraints = {
    }
	static mapping = {
        discriminator value: "init"
    }
}
