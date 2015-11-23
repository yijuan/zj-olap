package com.surelution.zjolap

class UserBranch {

    static constraints = {
		user unique:true
		branch unique:true
    }
	
	User user
	Branch branch
}
