package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class ThidrFactorController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [thidrFactorInstanceList: ThidrFactor.list(params), thidrFactorInstanceTotal: ThidrFactor.count()]
    }

    def create() {
        [thidrFactorInstance: new ThidrFactor(params)]
    }

    def save() {
		params.influncedAt = params.date('influncedAt', 'yyyy-MM-dd')
        def thidrFactorInstance = new ThidrFactor(params)
        if (!thidrFactorInstance.save(flush: true)) {
           // render(view: "create", model: [thidrFactorInstance: thidrFactorInstance])
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'thidrFactor.label', default: 'ThidrFactor'), thidrFactorInstance.id])
        //redirect(action: "show", id: thidrFactorInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def thidrFactorInstance = ThidrFactor.get(id)
        if (!thidrFactorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thidrFactor.label', default: 'ThidrFactor'), id])
            redirect(action: "list")
            return
        }

        [thidrFactorInstance: thidrFactorInstance]
    }

    def edit(Long id) {
        def thidrFactorInstance = ThidrFactor.get(id)
        if (!thidrFactorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thidrFactor.label', default: 'ThidrFactor'), id])
            redirect(action: "list")
            return
        }

        [thidrFactorInstance: thidrFactorInstance]
    }

    def update(Long id, Long version) {
		params.influncedAt = params.date('influncedAt', 'yyyy-mm-dd')
        def thidrFactorInstance = ThidrFactor.get(id)
        if (!thidrFactorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thidrFactor.label', default: 'ThidrFactor'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (thidrFactorInstance.version > version) {
                thidrFactorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'thidrFactor.label', default: 'ThidrFactor')] as Object[],
                          "Another user has updated this ThidrFactor while you were editing")
              //  render(view: "edit", model: [thidrFactorInstance: thidrFactorInstance])
				redirect(action: "list")
                return
            }
        }

        thidrFactorInstance.properties = params

        if (!thidrFactorInstance.save(flush: true)) {
           // render(view: "edit", model: [thidrFactorInstance: thidrFactorInstance])
			redirect(action: "list")
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'thidrFactor.label', default: 'ThidrFactor'), thidrFactorInstance.id])
        //redirect(action: "show", id: thidrFactorInstance.id)
		redirect(action: "list")
    }

    def delete(Long id) {
        def thidrFactorInstance = ThidrFactor.get(id)
        if (!thidrFactorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thidrFactor.label', default: 'ThidrFactor'), id])
            redirect(action: "list")
            return
        }

        try {
            thidrFactorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'thidrFactor.label', default: 'ThidrFactor'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'thidrFactor.label', default: 'ThidrFactor'), id])
            redirect(action: "show", id: id)
        }
    }
}
