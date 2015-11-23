package com.surelution.zjolap

import java.util.Date;

class DeliveryTaking {

    static constraints = {
		upload nullable:true
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
	
	String deliveryNo
	
	TankFarm tankFarm
	
	DeliveryTakingUpload upload
}
