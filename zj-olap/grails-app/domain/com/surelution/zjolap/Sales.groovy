package com.surelution.zjolap

/**
 * 客户经理
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class Sales {

    static constraints = {
		name nullable:false
    }
    
    String name
    Branch branch
	boolean enabled
	SalesFullTime fullTime
}
