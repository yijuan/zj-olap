package com.surelution.zjolap

/**
 * 销售环节， 批发、批转零、小额配送
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class SalingType {

    static constraints = {
		name unique:true
		name nullable:true
    }
    
    String name
}
