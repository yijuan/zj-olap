package com.surelution.zjolap

/**
 * 机构用户分类，包括：
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class CustomerTypeLevel2 {

    static constraints = {
		noChildLevel3 nullable:true
		name unique:true
		name nullable:false
    }
	
	CustomerType level1
	boolean isHasChild
	
	CustomerTypeLevel3 noChildLevel3
	
	String name
	
	
	
	def static getListForUpdate() {
		def list= CustomerTypeLevel2.list();
		def result = new ArrayList()
		
		list.each {
			if(it.isHasChild) {
				result << it
			}
		}
		
		return result;
		
   }
	
	
	def getDisplayName() {
		if(level1.isHasChild) {
			return name
		}
		return ""
	}
}
