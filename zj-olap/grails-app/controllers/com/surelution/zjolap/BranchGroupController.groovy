package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class BranchGroupController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [branchGroupInstanceList: BranchGroup.list(params), branchGroupInstanceTotal: BranchGroup.count()]
    }

    def create() {
        [branchGroupInstance: new BranchGroup(params)]
    }

    def save() {
        def branchGroupInstance = new BranchGroup(params)
		
		def msg = nameValidate(branchGroupInstance);
		if(msg) {
			flash.message = msg;
			//render(view: "create", model: [branchGroupInstance: branchGroupInstance])
			redirect(action:'list')
			return
		}
		
        if (!branchGroupInstance.save(flush: true)) {
            //render(view: "create", model: [branchGroupInstance: branchGroupInstance])
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'branchGroup.label', default: 'BranchGroup'), branchGroupInstance.id])
       // redirect(action: "show", id: branchGroupInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def branchGroupInstance = BranchGroup.get(id)
        if (!branchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'branchGroup.label', default: 'BranchGroup'), id])
            redirect(action: "list")
            return
        }

        [branchGroupInstance: branchGroupInstance]
    }

    def edit(Long id) {
        def branchGroupInstance = BranchGroup.get(id)
        if (!branchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'branchGroup.label', default: 'BranchGroup'), id])
            redirect(action: "list")
            return
        }

        [branchGroupInstance: branchGroupInstance]
    }

	def nameValidate(branchGroup) {
		if(!branchGroup?.name) {
			return "名称不能为空";
		}
		
		def  br = BranchGroup.findByName(branchGroup?.name)
		
		
		if(branchGroup?.id) {
			if(branchGroup?.id == br?.id) {
				return "";
			}
		}
		
		if(br) {
			return "名称不能重复";
		}
	}
    def update(Long id, Long version) {
        def branchGroupInstance = BranchGroup.get(id)
        if (!branchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'branchGroup.label', default: 'BranchGroup'), id])
            redirect(action: "list",params:[max:params.max,offset:params.offset])
            return
        }

        if (version != null) {
            if (branchGroupInstance.version > version) {
                branchGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'branchGroup.label', default: 'BranchGroup')] as Object[],
                          "Another user has updated this BranchGroup while you were editing")
             //   render(view: "edit", model: [branchGroupInstance: branchGroupInstance])
				redirect(action:'list',params:[max:params.max,offset:params.offset])
                return
            }
        }

        branchGroupInstance.properties = params
		
		def msg = nameValidate(branchGroupInstance);
		if(msg) {
			flash.message = msg;
		//	render(view: "edit", model: [branchGroupInstance: branchGroupInstance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
			return
		}

        if (!branchGroupInstance.save(flush: true)) {
         //   render(view: "edit", model: [branchGroupInstance: branchGroupInstance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'branchGroup.label', default: 'BranchGroup'), branchGroupInstance.id])
       // redirect(action: "show", id: branchGroupInstance.id)
		redirect(action:'list',params:[max:params.max,offset:params.offset])
    }

    def delete(Long id) {
        def branchGroupInstance = BranchGroup.get(id)
        if (!branchGroupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'branchGroup.label', default: 'BranchGroup'), id])
            redirect(action: "list")
            return
        }

        try {
            branchGroupInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'branchGroup.label', default: 'BranchGroup'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'branchGroup.label', default: 'BranchGroup'), id])
          //  redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
}
