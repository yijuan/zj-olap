package com.surelution.zjolap

/**
 * 油品品种，如汽油、柴油等
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class Category {

    static constraints = {
		name unique:true
		name nullable:false
    }
    
    String name
}
