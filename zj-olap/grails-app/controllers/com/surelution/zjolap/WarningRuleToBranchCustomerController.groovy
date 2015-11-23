package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class WarningRuleToBranchCustomerController {

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		
		def branch
		if(getLogBranch()) {
			branch = getLogBranch()
		}
		
		def result  = WarningRuleToBranchCustomer.createCriteria().list(max:10, offset:params.offset) {
			if(branch) {
				customerBranch {
					eq('branch', branch)
				}
			}
		}
		
        [branch:getLogBranch(),warningRuleToBranchCustomerInstanceList: result, warningRuleToBranchCustomerInstanceTotal:result.totalCount]
    }
	
	
	
	def listByCustomer() {

		def customerId = params.customerId;
		
		def branch
		def customer = Customer.get(Long.parseLong(customerId))
		if(getLogBranch()) {
			branch = getLogBranch()
		}
		if(!branch) {
			flash.message = "总公司不能在这设置警告规则"
			redirect(controller:"customer",  action: "list")
			return ;
		}



		def cb = CustomerBranch.findByCustomerAndBranch(customer,branch)

		if(cb) {
			
			def result  = WarningRuleToBranchCustomer.createCriteria().list(max:10, offset:params.offset) {
				eq("customerBranch",cb)
			}
		
			
			[customerBranch:cb,warningRuleToBranchCustomerInstanceList: result, warningRuleToBranchCustomerInstanceTotal:result.totalCount]
		}else {

			flash.message = "初始数据出现问题，请过会试试！"
			redirect(controller:"customer",  action: "list")
			return ;
		}
	}
	
	

    def create() {
        [branch:getLogBranch(),warningRuleToBranchCustomerInstance: new WarningRuleToBranchCustomer(params)]
    }

	def springSecurityService
	
	
	private def getLogBranch() {
		def user = springSecurityService.currentUser
		def loginBranch = user?.branch
		return loginBranch
	}
	
	
	
	
    def save() {
        def warningRuleToBranchCustomerInstance = new WarningRuleToBranchCustomer(params)
		println params
        if (!warningRuleToBranchCustomerInstance.save(flush: true)) {
          //  render(view: "create", model: [warningRuleToBranchCustomerInstance: warningRuleToBranchCustomerInstance])
           flash.message="客户警告设置不成功，请重新创建!"
			redirect(action:'list')
			 return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer'), warningRuleToBranchCustomerInstance.id])
        //redirect(action: "show", id: warningRuleToBranchCustomerInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def warningRuleToBranchCustomerInstance = WarningRuleToBranchCustomer.get(id)
        if (!warningRuleToBranchCustomerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer'), id])
            redirect(action: "list")
            return
        }

        [warningRuleToBranchCustomerInstance: warningRuleToBranchCustomerInstance]
    }

    def edit(Long id) {
        def warningRuleToBranchCustomerInstance = WarningRuleToBranchCustomer.get(id)
        if (!warningRuleToBranchCustomerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer'), id])
            redirect(action: "list")
            return
        }

        [branch:getLogBranch(),warningRuleToBranchCustomerInstance: warningRuleToBranchCustomerInstance]
    }

    def update(Long id, Long version) {
        def warningRuleToBranchCustomerInstance = WarningRuleToBranchCustomer.get(id)
	 
		 if (!warningRuleToBranchCustomerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (warningRuleToBranchCustomerInstance.version > version) {
                warningRuleToBranchCustomerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer')] as Object[],
                          "Another user has updated this WarningRuleToBranchCustomer while you were editing")
             //   render(view: "edit", model: [warningRuleToBranchCustomerInstance: warningRuleToBranchCustomerInstance])
				redirect(action:'list')
				 return
            }
        }

        warningRuleToBranchCustomerInstance.properties = params
		
        if (!warningRuleToBranchCustomerInstance.save(flush: true)) {
          //  render(view: "edit", model: [warningRuleToBranchCustomerInstance: warningRuleToBranchCustomerInstance])
			redirect(action:'list')
			 return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer'), warningRuleToBranchCustomerInstance.id])
       // redirect(action: "show", id: warningRuleToBranchCustomerInstance.id)
		redirect(action:'list')
    }

    def delete(Long id) {
        def warningRuleToBranchCustomerInstance = WarningRuleToBranchCustomer.get(id)
        if (!warningRuleToBranchCustomerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer'), id])
            redirect(action: "list")
            return
        }

        try {
            warningRuleToBranchCustomerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'warningRuleToBranchCustomer.label', default: 'WarningRuleToBranchCustomer'), id])
            //redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
}