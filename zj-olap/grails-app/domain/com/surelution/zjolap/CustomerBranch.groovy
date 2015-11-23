package com.surelution.zjolap

class CustomerBranch {

	Customer customer
	Branch branch
	TimeByDay date
    static constraints = {
		date nullable:true
    }
	
	
	def static deleteByCustomer(Customer customer) {
		 def result = CustomerBranch.findAllByCustomer(customer)
		 result.each{
			 it.delete(flush: true)
		 }
	}
	
	
	def static deleteByBranch(Branch branch) {
		def result = CustomerBranch.findAllByBranch(branch)
		result.each{
			it.delete(flush: true)
		}
   }
	

	def  static create(Customer customer,Branch  branch,TimeByDay date) {
		def customerBranch = CustomerBranch.findByCustomerAndBranch(customer,branch)
		if(!customerBranch) {
			customerBranch = new CustomerBranch()
			customerBranch.customer = customer
			customerBranch.branch = branch
			customerBranch.date = date
			
		}else {
			if(!customerBranch.date) {
				customerBranch.date = date
			}
		}
		
		customerBranch.save(flush:true)
		
		return customerBranch
	}
	
	
	def  static findOrcreate(Customer customer,Branch  branch) {
		def customerBranch = CustomerBranch.findByCustomerAndBranch(customer,branch)
		if(!customerBranch) {
			customerBranch = new CustomerBranch()
			customerBranch.customer = customer
			customerBranch.branch = branch
			customerBranch.save(flush:true)
		}
		
		
		return customerBranch
	}
	
	
	
	
//	public static getListByBranch(branchId) {
//		
//		Branch branch
//		if(branchId&&branchId !="null") {
//			branch = Branch.get(Long.parseLong(branchId));
//		}
//		println "branch:"+ branch.class
//		 def result= CustomerBranch.createCriteria().list(max:1000000, offset:0) {
//			//if(branch) {
//				//eq("branch":branch)
//			//}
//		}
//		 
//		return result
//	}
//	
	public static getListByBranch(branchId) {
		 def result= CustomerBranch.createCriteria().list(max:100000000, offset:0) {
			if(branchId&&branchId !="null") {
				branch {
					eq("id",Long.parseLong(branchId))
				}
			}
		}
		 
		return result
	}
	
}
