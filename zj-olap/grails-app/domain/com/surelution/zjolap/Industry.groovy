package com.surelution.zjolap

/**
 * 客户所属行业，行业属于某一个客户类别
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class Industry {

    static constraints = {
    }
    
    static mapping = {
        type column:'s_type'
    }
    
    String name
    CustomerType type
}
