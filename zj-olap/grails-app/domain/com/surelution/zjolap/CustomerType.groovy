package com.surelution.zjolap

/**
 * 销售类别，包括：社会单位、社会加油站、机构用户
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class CustomerType {

    static constraints = {
		noChildLevel2 nullable:true
		name unique:true
		name nullable:false
    }
	
	boolean isHasChild
	
	CustomerTypeLevel2 noChildLevel2
	String name
	
	def static getListForUpdate() {
		 def list= CustomerType.list();
		 def result = new ArrayList()
		 
		 list.each {
			 if(it.isHasChild) {
				 result << it
			 }
		 }
		 
		 return result;
		 
	}
}
