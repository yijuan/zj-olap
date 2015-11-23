package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class CustomerTypeLevel3Controller {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		
		def result = CustomerTypeLevel3.createCriteria().list(max:10, offset:params.offset) {
			level2 {
				eq("isHasChild",true)
			}
	   }
        
        [customerTypeLevel3InstanceList: result, customerTypeLevel3InstanceTotal: result.totalCount]
    }

    def create() {
        [customerTypeLevel3Instance: new CustomerTypeLevel3(params)]
    }
	
	def nameValidate(customerTypeLevel3Instance) {
		def  br = CustomerTypeLevel3.findByName(customerTypeLevel3Instance?.name)
		
		if(!customerTypeLevel3Instance?.name) {
			return "类型名称不能为空";
		}
		
		if(customerTypeLevel3Instance?.id) {
			if(customerTypeLevel3Instance?.id == br?.id) {
				return "";
			}
		}
		
		if(br) {
			return "类型名称不能重复";
		}
	}

    def save() {
        def customerTypeLevel3Instance = new CustomerTypeLevel3(params)
		
		def msg = nameValidate(customerTypeLevel3Instance);
		if(msg) {
			flash.message = msg;
			//render(view: "create", model: [customerTypeLevel3Instance: customerTypeLevel3Instance])
			redirect(action:'list')
			return
		}
		
        if (!customerTypeLevel3Instance.save(flush: true)) {
           // render(view: "create", model: [customerTypeLevel3Instance: customerTypeLevel3Instance])
			redirect(action:'list')
			return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3'), customerTypeLevel3Instance.id])
     //   redirect(action: "show", id: customerTypeLevel3Instance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def customerTypeLevel3Instance = CustomerTypeLevel3.get(id)
        if (!customerTypeLevel3Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3'), id])
            redirect(action: "list")
            return
        }

        [customerTypeLevel3Instance: customerTypeLevel3Instance]
    }

    def edit(Long id) {
        def customerTypeLevel3Instance = CustomerTypeLevel3.get(id)
        if (!customerTypeLevel3Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3'), id])
            redirect(action: "list")
            return
        }

        [customerTypeLevel3Instance: customerTypeLevel3Instance]
    }

    def update(Long id, Long version) {
        def customerTypeLevel3Instance = CustomerTypeLevel3.get(id)
        if (!customerTypeLevel3Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (customerTypeLevel3Instance.version > version) {
                customerTypeLevel3Instance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3')] as Object[],
                          "Another user has updated this CustomerTypeLevel3 while you were editing")
              //  render(view: "edit", model: [customerTypeLevel3Instance: customerTypeLevel3Instance])
				redirect(action:'list')
				  return
            }
        }

        customerTypeLevel3Instance.properties = params

		def msg = nameValidate(customerTypeLevel3Instance);
		if(msg) {
			flash.message = msg;
			//render(view: "edit", model: [customerTypeLevel3Instance: customerTypeLevel3Instance])
			redirect(action:'list')
			return
		}
		
        if (!customerTypeLevel3Instance.save(flush: true)) {
           // render(view: "edit", model: [customerTypeLevel3Instance: customerTypeLevel3Instance])
			redirect(action:'list')
			 return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3'), customerTypeLevel3Instance.id])
		redirect(action:'list')
		 //redirect(action: "show", id: customerTypeLevel3Instance.id)
    }

    def delete(Long id) {
        def customerTypeLevel3Instance = CustomerTypeLevel3.get(id)
        if (!customerTypeLevel3Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3'), id])
            redirect(action: "list")
            return
        }
		
	
        try {
            customerTypeLevel3Instance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'customerTypeLevel3.label', default: 'CustomerTypeLevel3'), id])
           // redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
}
