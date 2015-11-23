package com.surelution.zjolap

import com.sun.org.apache.xml.internal.resolver.Catalog;

/**
 * 油品品号，如97#、0#号等，每种油品都属于一个油品大类
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class GasType {

    static constraints = {
		name unique:true
		name nullable:false
		category nullable:false
    }
    
    String name
    Category category
	
	public int hashCode() {
		id?.hashCode()
	}

	@Override
	public boolean equals(Object obj) {
		if(!obj instanceof GasType) {
			return false;
		}
		if(id != null && obj.id != null) {
			return id == obj.id
		}
		return super.equals(obj);
	}
	
	
}
