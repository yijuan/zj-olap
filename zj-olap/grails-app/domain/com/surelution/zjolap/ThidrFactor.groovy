package com.surelution.zjolap

class ThidrFactor {

    static constraints = {
		branch nullable:true
		influncedAt nullable:true
    }

	static mapping = {
		type column:"_type"
	}

	ThidrFactorType type
	Date influncedAt
	Branch branch
	String description
}
