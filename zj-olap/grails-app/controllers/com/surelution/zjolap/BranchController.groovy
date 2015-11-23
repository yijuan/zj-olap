package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class BranchController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [branchInstanceList: Branch.list(params), branchInstanceTotal: Branch.count()]
    }

    def create() {
        [branchInstance: new Branch(params)]
    }

    def save() {
        def branchInstance = new Branch(params)
        if (!branchInstance.save(flush: true)) {
           // render(view: "create", model: [branchInstance: branchInstance])
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'branch.label', default: 'Branch'), branchInstance.id])
        //redirect(action: "show", id: branchInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def branchInstance = Branch.get(id)
        if (!branchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'branch.label', default: 'Branch'), id])
            redirect(action: "list")
            return
        }

        [branchInstance: branchInstance]
    }

    def edit(Long id) {
        def branchInstance = Branch.get(id)
        if (!branchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'branch.label', default: 'Branch'), id])
            redirect(action: "list")
            return
        }

        [branchInstance: branchInstance]
    }

    def update(Long id, Long version) {
        def branchInstance = Branch.get(id)
        if (!branchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'branch.label', default: 'Branch'), id])
            redirect(action: "list",params:[max:params.max,offset:params.offset])
            return
        }

        if (version != null) {
            if (branchInstance.version > version) {
                branchInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'branch.label', default: 'Branch')] as Object[],
                          "Another user has updated this Branch while you were editing")
              //  render(view: "edit", model: [branchInstance: branchInstance])
				redirect(action:'list',params:[max:params.max,offset:params.offset])
                return
            }
        }

        branchInstance.properties = params

        if (!branchInstance.save(flush: true)) {
           // render(view: "edit", model: [branchInstance: branchInstance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'branch.label', default: 'Branch'), branchInstance.id])
      //  redirect(action: "show", id: branchInstance.id)
		redirect(action:'list',params:[max:params.max,offset:params.offset])
    }

    def delete(Long id) {
	
		
        def branchInstance = Branch.get(id)
        if (!branchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'branch.label', default: 'Branch'), id])
            redirect(action: "list")
            return
        }
		
		
		
		def  salesOrder= SalesOrder.findAllByBranchAndIsVailAndStatus(branchInstance,true,"ABLE")
		
		if(salesOrder) {
			flash.message = "系统中已经存在该分公司的订单信息，不能删除该分公司！"
		//	redirect(action: "show", id: branchInstance.id)
			redirect(action:'list')
			return
		}

        try {
            branchInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'branch.label', default: 'Branch'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'branch.label', default: 'Branch'), id])
           // redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
}
