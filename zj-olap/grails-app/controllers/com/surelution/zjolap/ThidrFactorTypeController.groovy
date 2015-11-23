package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class ThidrFactorTypeController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [thidrFactorTypeInstanceList: ThidrFactorType.list(params), thidrFactorTypeInstanceTotal: ThidrFactorType.count()]
    }

    def create() {
        [thidrFactorTypeInstance: new ThidrFactorType(params)]
    }

    def save() {
        def thidrFactorTypeInstance = new ThidrFactorType(params)
        if (!thidrFactorTypeInstance.save(flush: true)) {
            //render(view: "create", model: [thidrFactorTypeInstance: thidrFactorTypeInstance])
           redirect(action:'list')
			 return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'thidrFactorType.label', default: 'ThidrFactorType'), thidrFactorTypeInstance.id])
     //   redirect(action: "show", id: thidrFactorTypeInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def thidrFactorTypeInstance = ThidrFactorType.get(id)
        if (!thidrFactorTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thidrFactorType.label', default: 'ThidrFactorType'), id])
            redirect(action: "list")
            return
        }

        [thidrFactorTypeInstance: thidrFactorTypeInstance]
    }

    def edit(Long id) {
        def thidrFactorTypeInstance = ThidrFactorType.get(id)
        if (!thidrFactorTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thidrFactorType.label', default: 'ThidrFactorType'), id])
            redirect(action: "list")
            return
        }

        [thidrFactorTypeInstance: thidrFactorTypeInstance]
    }

    def update(Long id, Long version) {
        def thidrFactorTypeInstance = ThidrFactorType.get(id)
        if (!thidrFactorTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thidrFactorType.label', default: 'ThidrFactorType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (thidrFactorTypeInstance.version > version) {
                thidrFactorTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'thidrFactorType.label', default: 'ThidrFactorType')] as Object[],
                          "Another user has updated this ThidrFactorType while you were editing")
               // render(view: "edit", model: [thidrFactorTypeInstance: thidrFactorTypeInstance])
				redirect(action:'list')
				return
            }
        }

        thidrFactorTypeInstance.properties = params

        if (!thidrFactorTypeInstance.save(flush: true)) {
          //  render(view: "edit", model: [thidrFactorTypeInstance: thidrFactorTypeInstance])
			redirect(action:'list')
			   return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'thidrFactorType.label', default: 'ThidrFactorType'), thidrFactorTypeInstance.id])
       // redirect(action: "show", id: thidrFactorTypeInstance.id)
		redirect(action:'list')
    }

    def delete(Long id) {
        def thidrFactorTypeInstance = ThidrFactorType.get(id)
        if (!thidrFactorTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thidrFactorType.label', default: 'ThidrFactorType'), id])
            redirect(action: "list")
            return
        }

        try {
            thidrFactorTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'thidrFactorType.label', default: 'ThidrFactorType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'thidrFactorType.label', default: 'ThidrFactorType'), id])
           // redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
}
