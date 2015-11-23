package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class CustomerTypeLevel2Controller {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		
		
		 def result = CustomerTypeLevel2.createCriteria().list(max:10, offset:params.offset) {
			 level1 {
				 eq("isHasChild",true)
			 }
		}
		
		
        [customerTypeLevel2InstanceList: result, customerTypeLevel2InstanceTotal: result.totalCount]
    }
	
	

    def create() {
        [customerTypeLevel2Instance: new CustomerTypeLevel2(params)]
    }

	def nameValidate(customerTypeLevel2Instance) {
		
		if(!customerTypeLevel2Instance?.name) {
			return "类型名称不能为空";
		}
		
		def  br = CustomerTypeLevel2.findByName(customerTypeLevel2Instance?.name)
		
		if(customerTypeLevel2Instance?.id) {
			if(customerTypeLevel2Instance?.id == br?.id) {
				return "";
			}
		}
		
		
		if(br) {
			return "类型名称不能重复";
		}
	}
	
    def save() {
        def customerTypeLevel2Instance = new CustomerTypeLevel2(params)
		
		def msg = nameValidate(customerTypeLevel2Instance);
		if(msg) {
			flash.message = msg;
			//render(view: "create", model: [customerTypeLevel2Instance: customerTypeLevel2Instance])
			redirect(action:'list')
			return
		}
		
		
        if (!customerTypeLevel2Instance.save(flush: true)) {
           // render(view: "create", model: [customerTypeLevel2Instance: customerTypeLevel2Instance])
			redirect(action:'list')
			 return
        }
		saveIfNoChild(customerTypeLevel2Instance);
        flash.message = message(code: 'default.created.message', args: [message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2'), customerTypeLevel2Instance.id])
      //  redirect(action: "show", id: customerTypeLevel2Instance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def customerTypeLevel2Instance = CustomerTypeLevel2.get(id)
        if (!customerTypeLevel2Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2'), id])
            redirect(action: "list")
            return
        }

        [customerTypeLevel2Instance: customerTypeLevel2Instance]
    }

    def edit(Long id) {
        def customerTypeLevel2Instance = CustomerTypeLevel2.get(id)
        if (!customerTypeLevel2Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2'), id])
            redirect(action: "list")
            return
        }

        [customerTypeLevel2Instance: customerTypeLevel2Instance]
    }

    def update(Long id, Long version) {
        def customerTypeLevel2Instance = CustomerTypeLevel2.get(id)
        if (!customerTypeLevel2Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2'), id])
            redirect(action: "list",params:[max:params.max,offset:params.offset])
            return
        }

        if (version != null) {
            if (customerTypeLevel2Instance.version > version) {
                customerTypeLevel2Instance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2')] as Object[],
                          "Another user has updated this CustomerTypeLevel2 while you were editing")
               // render(view: "edit", model: [customerTypeLevel2Instance: customerTypeLevel2Instance])
				redirect(action:'list',params:[max:params.max,offset:params.offset])
				 return
            }
        }

        customerTypeLevel2Instance.properties = params
		
		//println params

		
		def msg = nameValidate(customerTypeLevel2Instance);
		if(msg) {
			flash.message = msg;
			//render(view: "edit", model: [customerTypeLevel2Instance: customerTypeLevel2Instance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
			return
		}
		
        if (!customerTypeLevel2Instance.save(flush: true)) {
           // render(view: "edit", model: [customerTypeLevel2Instance: customerTypeLevel2Instance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
			return
        }
		updateChangeNochild(customerTypeLevel2Instance)
       // flash.message = message(code: 'default.updated.message', args: [message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2'), customerTypeLevel2Instance.id])
       // redirect(action: "show", id: customerTypeLevel2Instance.id)
		flash.message="机构类型更新成功！"
		redirect(action:'list',params:[max:params.max,offset:params.offset])
    }

    def delete(Long id) {
        def customerTypeLevel2Instance = CustomerTypeLevel2.get(id)
        if (!customerTypeLevel2Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2'), id])
            redirect(action: "list")
            return
        }

        try {
            customerTypeLevel2Instance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'customerTypeLevel2.label', default: 'CustomerTypeLevel2'), id])
           // redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
	
	
	
	private def saveIfNoChild(CustomerTypeLevel2 customerType2) {
		def name  = customerType2.name
		if(!customerType2.isHasChild) {
			
			def level3 = new CustomerTypeLevel3();
			level3.name = name
			level3.level2 = customerType2;
			level3.save(flush:true);
			
			customerType2.noChildLevel3 = level3;
			customerType2.save(flush:true);
		}
	}
	
	
	
	private def updateChangeNochild(CustomerTypeLevel2 customerTypeLevel2)  {
		def name  = customerTypeLevel2.name
		if(!customerTypeLevel2.isHasChild) {
			//如果以前有孩子 改成没有孩子
			if(!customerTypeLevel2.noChildLevel3) {
				deleteChild(customerTypeLevel2);
				saveIfNoChild(customerTypeLevel2);
				return
			}
			
			
			//如果以前就没有孩子
			def level3 = customerTypeLevel2.noChildLevel3
			level3.name = name;
			level3.save(flush:true);
		} else {
		
			//1.如果以前有孩子 现在有孩子  不用操作
		
			//2如果以前没孩子 现在有孩子
			if(customerTypeLevel2.noChildLevel3) {
				 def level3= customerTypeLevel2.noChildLevel3
				 customerTypeLevel2.noChildLevel3 = null;
				 customerTypeLevel2.save(flush:true);
				 
				 
				 level3.delete(flush:true);
				 
				
			}
		
		
		}
	}
	
	
	private def deleteChild(CustomerTypeLevel2 customerTypeLevel2) {
		def allLevel3= CustomerTypeLevel3.findAllByLevel2(customerTypeLevel2)
		
		allLevel3.each { level3->
			level3.delete(flush:true);
		}
   }
	
}