package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class SalesFullTimeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [salesFullTimeInstanceList: SalesFullTime.list(params), salesFullTimeInstanceTotal: SalesFullTime.count()]
    }

    def create() {
        [salesFullTimeInstance: new SalesFullTime(params)]
    }

    def save() {
        def salesFullTimeInstance = new SalesFullTime(params)
        if (!salesFullTimeInstance.save(flush: true)) {
            render(view: "create", model: [salesFullTimeInstance: salesFullTimeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'salesFullTime.label', default: 'SalesFullTime'), salesFullTimeInstance.id])
        redirect(action: "show", id: salesFullTimeInstance.id)
    }

    def show(Long id) {
        def salesFullTimeInstance = SalesFullTime.get(id)
        if (!salesFullTimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salesFullTime.label', default: 'SalesFullTime'), id])
            redirect(action: "list")
            return
        }

        [salesFullTimeInstance: salesFullTimeInstance]
    }

    def edit(Long id) {
        def salesFullTimeInstance = SalesFullTime.get(id)
        if (!salesFullTimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salesFullTime.label', default: 'SalesFullTime'), id])
            redirect(action: "list")
            return
        }

        [salesFullTimeInstance: salesFullTimeInstance]
    }

    def update(Long id, Long version) {
        def salesFullTimeInstance = SalesFullTime.get(id)
        if (!salesFullTimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salesFullTime.label', default: 'SalesFullTime'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (salesFullTimeInstance.version > version) {
                salesFullTimeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'salesFullTime.label', default: 'SalesFullTime')] as Object[],
                          "Another user has updated this SalesFullTime while you were editing")
                render(view: "edit", model: [salesFullTimeInstance: salesFullTimeInstance])
                return
            }
        }

        salesFullTimeInstance.properties = params

        if (!salesFullTimeInstance.save(flush: true)) {
            render(view: "edit", model: [salesFullTimeInstance: salesFullTimeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'salesFullTime.label', default: 'SalesFullTime'), salesFullTimeInstance.id])
        redirect(action: "show", id: salesFullTimeInstance.id)
    }

    def delete(Long id) {
        def salesFullTimeInstance = SalesFullTime.get(id)
        if (!salesFullTimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salesFullTime.label', default: 'SalesFullTime'), id])
            redirect(action: "list")
            return
        }

        try {
            salesFullTimeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'salesFullTime.label', default: 'SalesFullTime'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'salesFullTime.label', default: 'SalesFullTime'), id])
            redirect(action: "show", id: id)
        }
    }
}
