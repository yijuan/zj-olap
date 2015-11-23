package com.surelution.zjolap

import java.util.Date;

class StockBalance {

    static constraints = {
		timeByDay nullable:true
    }
	static mapping = {
		discriminator column: "_type"
	}
	
	Branch branch
	Customer customer
	Date takingAt
	GasType gasType

	/**
	 * 日期统计
	 */
	TimeByDay timeByDay
	
	/**
	 * 数量
	 */
	Double quantity
}
