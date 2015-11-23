package com.surelution.zjolap

import grails.converters.JSON;

import org.springframework.dao.DataIntegrityViolationException

class CustomerTypeController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [customerTypeInstanceList: CustomerType.list(params), customerTypeInstanceTotal: CustomerType.count()]
    }

    def create() {
        [customerTypeInstance: new CustomerType(params)]
    }
	
	def nameValidate(customerTypeInstance) {
		
		if(!customerTypeInstance?.name) {
			return "名称不能为空";
		}
		
		def  br = CustomerType.findByName(customerTypeInstance?.name)
		
		
		if(customerTypeInstance?.id) {
			if(customerTypeInstance?.id == br?.id) {
				return "";
			}
		}
		
		if(br) {
			return "名称不能重复";
		}
	}

    def save() {
        def customerTypeInstance = new CustomerType(params)
		
		def msg = nameValidate(customerTypeInstance);
		if(msg) {
			flash.message = msg;
			//render(view: "create", model: [customerTypeInstance: customerTypeInstance])
			redirect(action:'list')
			return
		}
		
		
        if (!customerTypeInstance.save(flush: true)) {
           // render(view: "create", model: [customerTypeInstance: customerTypeInstance])
			redirect(action:'list')
            return
        }
		
		saveIfNoChild(customerTypeInstance);

        flash.message = message(code: 'default.created.message', args: [message(code: 'customerType.label', default: 'CustomerType'), customerTypeInstance.id])
       // redirect(action: "show", id: customerTypeInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def customerTypeInstance = CustomerType.get(id)
        if (!customerTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerType.label', default: 'CustomerType'), id])
            redirect(action: "list")
            return
        }

        [customerTypeInstance: customerTypeInstance]
    }

    def edit(Long id) {
        def customerTypeInstance = CustomerType.get(id)
        if (!customerTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerType.label', default: 'CustomerType'), id])
            redirect(action: "list")
            return
        }

        [customerTypeInstance: customerTypeInstance]
    }

    def update(Long id, Long version) {
        def customerTypeInstance = CustomerType.get(id)
        if (!customerTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerType.label', default: 'CustomerType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (customerTypeInstance.version > version) {
                customerTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'customerType.label', default: 'CustomerType')] as Object[],
                          "Another user has updated this CustomerType while you were editing")
                render(view: "edit", model: [customerTypeInstance: customerTypeInstance])
                return
            }
        }

        customerTypeInstance.properties = params
		
		def msg = nameValidate(customerTypeInstance);
		if(msg) {
			flash.message = msg;
			//render(view: "edit", model: [customerTypeInstance: customerTypeInstance])
			redirect(action:'list')
			return
		}

        if (!customerTypeInstance.save(flush: true)) {
            //render(view: "edit", model: [customerTypeInstance: customerTypeInstance])
			redirect(action:'list')
            return
        }
		updateChangeNochild(customerTypeInstance)

        flash.message = message(code: 'default.updated.message', args: [message(code: 'customerType.label', default: 'CustomerType'), customerTypeInstance.id])
       // redirect(action: "show", id: customerTypeInstance.id)
		redirect(action:'list')
    }

    def delete(Long id) {
        def customerTypeInstance = CustomerType.get(id)
        if (!customerTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerType.label', default: 'CustomerType'), id])
            redirect(action: "list")
            return
        }

        try {
            customerTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'customerType.label', default: 'CustomerType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'customerType.label', default: 'CustomerType'), id])
          //  redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
	
	def loadType2() {
		def typeId = params.typeID
		
		def customerType= CustomerType.load(typeId);
		
		if(customerType.isHasChild) {
			render getJSONDataForType1(customerType)
			return 
		}else {
			render "" ;
			return 
		}
		
	}
	
	
	def loadType3() {
		def typeId = params.typeID
		
		def customerType= CustomerTypeLevel2.load(typeId);
		
		if(customerType.isHasChild) {
			render getJSONDataForType2(customerType)
			return
		}else {
			render "" ;
			return
		}
		
	}
	
	
	
	private def getJSONDataForType1(customerType) {
		List result = new ArrayList();
		
		 def type2s = CustomerTypeLevel2.findAllByLevel1(customerType)
		 type2s.each {
			 if(it.level1.isHasChild) {
			 def map = new HashMap();
			 map.put("name", it.name);
			 map.put("id",it.id);
			 result.add(map);
			 }
		 }
		 
		 
		return  result as JSON
	}
	
	
	private def getJSONDataForType2(customerType) {
		List result = new ArrayList();
		
		 def type3s = CustomerTypeLevel3.findAllByLevel2(customerType)
		 type3s.each {
			 if(it.level2.isHasChild) {
			 def map = new HashMap();
			 map.put("name", it.name);
			 map.put("id",it.id);
			 result.add(map);
			 }
		 }
		 
		 
		return  result as JSON
	}
	
	
	private def saveIfNoChild(CustomerType customerType) {
		def name  = customerType.name
		if(!customerType.isHasChild) {
			def level2 = new CustomerTypeLevel2();
			level2.name = name
			level2.isHasChild = false;
			level2.level1 = customerType;
			level2.save(flush:true);
			
			customerType.noChildLevel2 = level2;
			customerType.save(flush:true);
			
			
			
			def level3 = new CustomerTypeLevel3();
			level3.name = name
			level3.level2 = level2;
			level3.save(flush:true);
			
			
			
			level2.noChildLevel3 = level3;
			level2.save(flush:true);
			
		}
	}
	
	
	private def updateChangeNochild(CustomerType customerType)  {
		def name  = customerType.name
		if(!customerType.isHasChild) {
			//如果以前有孩子 改成没有孩子
			if(!customerType.noChildLevel2) {
				deleteChild(customerType);
				saveIfNoChild(customerType);
				return 
			}
			
			//如果以前就没有孩子
			 def level2= customerType.noChildLevel2;
			 level2.name = name;
			 level2.save(flush:true);
			 
			 def level3 = level2.noChildLevel3;
			 level3.name = name;
			 level3.save(flush:true);
		}else {
			//1.如果以前有孩子 现在有孩子  不用操作
		
			//2如果以前没孩子 现在有孩子
			if(customerType.noChildLevel2) {
				def level2 = customerType.noChildLevel2
				def level3 = level2.noChildLevel3
				
				level2.noChildLevel3 = null;
				level2.save(flush:true);
				
				customerType.noChildLevel2 = null;
				customerType.save(flush:true);
				
				level3.delete(flush:true);
				level2.delete(flush:true);
			}
			
		}
	}
	
	private def deleteChild(CustomerType customerType) {
		customerType = CustomerType.get(customerType.id)
		 def allLevel2= CustomerTypeLevel2.findAllByLevel1(customerType)
		 
		 allLevel2.each { level2->
			  def allLevel3 = CustomerTypeLevel3.findAllByLevel2(level2);
			  allLevel3.each { level3->
				  level3.delete(flush:true);
			  }
			  level2.delete(flush:true);
		 }
	}
}