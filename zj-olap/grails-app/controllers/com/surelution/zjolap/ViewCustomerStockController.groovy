package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class ViewCustomerStockController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [viewCustomerStockInstanceList: ViewCustomerStock.list(params), viewCustomerStockInstanceTotal: ViewCustomerStock.count()]
    }

    def create() {
        [viewCustomerStockInstance: new ViewCustomerStock(params)]
    }

    def save() {
        def viewCustomerStockInstance = new ViewCustomerStock(params)
        if (!viewCustomerStockInstance.save(flush: true)) {
            render(view: "create", model: [viewCustomerStockInstance: viewCustomerStockInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock'), viewCustomerStockInstance.id])
        redirect(action: "show", id: viewCustomerStockInstance.id)
    }

    def show(Long id) {
        def viewCustomerStockInstance = ViewCustomerStock.get(id)
        if (!viewCustomerStockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock'), id])
            redirect(action: "list")
            return
        }

        [viewCustomerStockInstance: viewCustomerStockInstance]
    }

    def edit(Long id) {
        def viewCustomerStockInstance = ViewCustomerStock.get(id)
        if (!viewCustomerStockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock'), id])
            redirect(action: "list")
            return
        }

        [viewCustomerStockInstance: viewCustomerStockInstance]
    }

    def update(Long id, Long version) {
        def viewCustomerStockInstance = ViewCustomerStock.get(id)
        if (!viewCustomerStockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (viewCustomerStockInstance.version > version) {
                viewCustomerStockInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock')] as Object[],
                          "Another user has updated this ViewCustomerStock while you were editing")
                render(view: "edit", model: [viewCustomerStockInstance: viewCustomerStockInstance])
                return
            }
        }

        viewCustomerStockInstance.properties = params

        if (!viewCustomerStockInstance.save(flush: true)) {
            render(view: "edit", model: [viewCustomerStockInstance: viewCustomerStockInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock'), viewCustomerStockInstance.id])
        redirect(action: "show", id: viewCustomerStockInstance.id)
    }

    def delete(Long id) {
        def viewCustomerStockInstance = ViewCustomerStock.get(id)
        if (!viewCustomerStockInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock'), id])
            redirect(action: "list")
            return
        }

        try {
            viewCustomerStockInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'viewCustomerStock.label', default: 'ViewCustomerStock'), id])
            redirect(action: "show", id: id)
        }
    }
}
