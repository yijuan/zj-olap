package com.surelution.zjolap

class CustomerVisting {

    static constraints = {
		customer nullable:true
		vistingAt nullable:true
    }
	
	static mapping = {
		description type:'text'
		type column:'_type'
	}
	
	Customer customer
	Sales sales
	
	Date vistingAt
	String description
	CustomerVistingType type
	
	User operator
}
