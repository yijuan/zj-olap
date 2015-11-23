package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class SalingTypeController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [salingTypeInstanceList: SalingType.list(params), salingTypeInstanceTotal: SalingType.count()]
    }

    def create() {
        [salingTypeInstance: new SalingType(params)]
    }

    def save() {
        def salingTypeInstance = new SalingType(params)
        if (!salingTypeInstance.save(flush: true)) {
          //  render(view: "create", model: [salingTypeInstance: salingTypeInstance])
			redirect(action: "list")
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'salingType.label', default: 'SalingType'), salingTypeInstance.id])
        //redirect(action: "show", id: salingTypeInstance.id)
		redirect(action: "list")
    }

    def show(Long id) {
        def salingTypeInstance = SalingType.get(id)
        if (!salingTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salingType.label', default: 'SalingType'), id])
            redirect(action: "list")
            return
        }

        [salingTypeInstance: salingTypeInstance]
    }

    def edit(Long id) {
        def salingTypeInstance = SalingType.get(id)
        if (!salingTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salingType.label', default: 'SalingType'), id])
            redirect(action: "list")
            return
        }

        [salingTypeInstance: salingTypeInstance]
    }

    def update(Long id, Long version) {
        def salingTypeInstance = SalingType.get(id)
        if (!salingTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salingType.label', default: 'SalingType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (salingTypeInstance.version > version) {
                salingTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'salingType.label', default: 'SalingType')] as Object[],
                          "Another user has updated this SalingType while you were editing")
             //   render(view: "edit", model: [salingTypeInstance: salingTypeInstance])
				redirect(action: "list")
                return
            }
        }

        salingTypeInstance.properties = params

        if (!salingTypeInstance.save(flush: true)) {
         //   render(view: "edit", model: [salingTypeInstance: salingTypeInstance])
			redirect(action: "list")
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'salingType.label', default: 'SalingType'), salingTypeInstance.id])
       // redirect(action: "show", id: salingTypeInstance.id)
		redirect(action: "list")
    }

    def delete(Long id) {
        def salingTypeInstance = SalingType.get(id)
        if (!salingTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salingType.label', default: 'SalingType'), id])
            redirect(action: "list")
            return
        }
		
		
		def  salesOrder= SalesOrder.findAllBySalingtypeAndIsVailAndStatus(salingTypeInstance,true,"ABLE")
		
		if(salesOrder) {
			flash.message = "系统中已经存在该销售环节的订单信息，不能删除该销售环节！"
			//redirect(action: "show", id: salingTypeInstance.id)
			redirect(action: "list")
			return
		}
		

        try {
            salingTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'salingType.label', default: 'SalingType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'salingType.label', default: 'SalingType'), id])
            redirect(action: "show", id: id)
        }
    }
}
