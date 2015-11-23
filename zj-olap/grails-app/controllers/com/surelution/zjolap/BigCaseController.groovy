package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class BigCaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bigCaseInstanceList: BigCase.list(params), bigCaseInstanceTotal: BigCase.count()]
    }

    def create() {
        [bigCaseInstance: new BigCase(params)]
    }

    def save() {
        def bigCaseInstance = new BigCase(params)
        if (!bigCaseInstance.save(flush: true)) {
            render(view: "create", model: [bigCaseInstance: bigCaseInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bigCase.label', default: 'BigCase'), bigCaseInstance.id])
        redirect(action: "show", id: bigCaseInstance.id)
    }

    def show(Long id) {
        def bigCaseInstance = BigCase.get(id)
        if (!bigCaseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bigCase.label', default: 'BigCase'), id])
            redirect(action: "list")
            return
        }

        [bigCaseInstance: bigCaseInstance]
    }

    def edit(Long id) {
        def bigCaseInstance = BigCase.get(id)
        if (!bigCaseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bigCase.label', default: 'BigCase'), id])
            redirect(action: "list")
            return
        }

        [bigCaseInstance: bigCaseInstance]
    }

    def update(Long id, Long version) {
        def bigCaseInstance = BigCase.get(id)
        if (!bigCaseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bigCase.label', default: 'BigCase'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bigCaseInstance.version > version) {
                bigCaseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'bigCase.label', default: 'BigCase')] as Object[],
                          "Another user has updated this BigCase while you were editing")
                render(view: "edit", model: [bigCaseInstance: bigCaseInstance])
                return
            }
        }

        bigCaseInstance.properties = params

        if (!bigCaseInstance.save(flush: true)) {
            render(view: "edit", model: [bigCaseInstance: bigCaseInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bigCase.label', default: 'BigCase'), bigCaseInstance.id])
        redirect(action: "show", id: bigCaseInstance.id)
    }

    def delete(Long id) {
        def bigCaseInstance = BigCase.get(id)
        if (!bigCaseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bigCase.label', default: 'BigCase'), id])
            redirect(action: "list")
            return
        }

        try {
            bigCaseInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bigCase.label', default: 'BigCase'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bigCase.label', default: 'BigCase'), id])
            redirect(action: "show", id: id)
        }
    }
}
