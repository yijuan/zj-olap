package com.surelution.zjolap

/**
 * 分公司，或公司机关营销部门
 * 
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class Branch {

    static constraints = {
		name unique:true
		branchGroup nullable:true
    }
    
    String name
	BranchGroup branchGroup
}
