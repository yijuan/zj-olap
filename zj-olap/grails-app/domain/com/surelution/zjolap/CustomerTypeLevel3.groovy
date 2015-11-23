package com.surelution.zjolap

/**
 * 工业分类，包括：
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class CustomerTypeLevel3 {

    static constraints = {
		name unique:true
		name nullable:false
    }

	CustomerTypeLevel2 level2
	
	String name
	
	
	def getDisplayName() {
		if(level2.isHasChild) {
			return name
		}
		return ""
	}
}
