package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class GasTypeController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [gasTypeInstanceList: GasType.list(params), gasTypeInstanceTotal: GasType.count()]
    }

    def create() {
        [gasTypeInstance: new GasType(params)]
    }

    def save() {
        def gasTypeInstance = new GasType(params)
		
		
		def msg = nameValidate(gasTypeInstance);
		if(msg) {
			flash.message = msg;
			//render(view: "create", model: [gasTypeInstance: gasTypeInstance])
			redirect(action:'list')
			return
		}

		
        if (!gasTypeInstance.save(flush: true)) {
            //render(view: "create", model: [gasTypeInstance: gasTypeInstance])
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'gasType.label', default: 'GasType'), gasTypeInstance.id])
       // redirect(action: "show", id: gasTypeInstance.id)
		redirect(action:'list')
    }

	def nameValidate(gasTypeInstance) {
		
		if(!gasTypeInstance?.name) {
			return "类型名称不能为空";
		}
		
		def  br = GasType.findByName(gasTypeInstance?.name)
		
		if(gasTypeInstance?.id) {
			if(gasTypeInstance?.id == br?.id) {
				return "";
			}
		}
		
		if(br) {
			return "类型名称不能重复";
		}
	}
	
	
    def show(Long id) {
        def gasTypeInstance = GasType.get(id)
        if (!gasTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gasType.label', default: 'GasType'), id])
            redirect(action: "list")
            return
        }

        [gasTypeInstance: gasTypeInstance]
    }

    def edit(Long id) {
        def gasTypeInstance = GasType.get(id)
        if (!gasTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gasType.label', default: 'GasType'), id])
            redirect(action: "list")
            return
        }

        [gasTypeInstance: gasTypeInstance]
    }

    def update(Long id, Long version) {
        def gasTypeInstance = GasType.get(id)
        if (!gasTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gasType.label', default: 'GasType'), id])
            redirect(action: "list",params:[max:params.max,offset:params.offset])
            return
        }

        if (version != null) {
            if (gasTypeInstance.version > version) {
                gasTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'gasType.label', default: 'GasType')] as Object[],
                          "Another user has updated this GasType while you were editing")
               // render(view: "edit", model: [gasTypeInstance: gasTypeInstance])
				redirect(action:'list',params:[max:params.max,offset:params.offset])
                return
            }
        }

        gasTypeInstance.properties = params

		def msg = nameValidate(gasTypeInstance);
		if(msg) {
			flash.message = msg;
		//	render(view: "edit", model: [gasTypeInstance: gasTypeInstance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
			return
		}
		
        if (!gasTypeInstance.save(flush: true)) {
          //  render(view: "edit", model: [gasTypeInstance: gasTypeInstance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'gasType.label', default: 'GasType'), gasTypeInstance.id])
      //  redirect(action: "show", id: gasTypeInstance.id)
		redirect(action:'list',params:[max:params.max,offset:params.offset])
    }

    def delete(Long id) {
        def gasTypeInstance = GasType.get(id)
        if (!gasTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'gasType.label', default: 'GasType'), id])
            redirect(action: "list")
            return
        }
		
		
		def  salesOrder= SalesOrder.findAllByGasTypeAndIsVailAndStatus(gasTypeInstance,true,"ABLE")
		
		if(salesOrder) {
			flash.message = "系统中已经存在该品号的订单信息，不能删除该品号！"
			//redirect(action: "show", id: gasTypeInstance.id)
			redirect(action:'list')
			return
		}
		

        try {
            gasTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'gasType.label', default: 'GasType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'gasType.label', default: 'GasType'), id])
          //  redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
}
