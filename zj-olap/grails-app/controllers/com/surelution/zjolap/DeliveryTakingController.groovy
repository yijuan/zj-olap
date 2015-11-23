package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class DeliveryTakingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [deliveryTakingInstanceList: DeliveryTaking.list(params), deliveryTakingInstanceTotal: DeliveryTaking.count()]
    }

    def create() {
        [deliveryTakingInstance: new DeliveryTaking(params)]
    }

    def save() {
        def deliveryTakingInstance = new DeliveryTaking(params)
        if (!deliveryTakingInstance.save(flush: true)) {
            render(view: "create", model: [deliveryTakingInstance: deliveryTakingInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'deliveryTaking.label', default: 'DeliveryTaking'), deliveryTakingInstance.id])
        redirect(action: "show", id: deliveryTakingInstance.id)
    }

    def show(Long id) {
        def deliveryTakingInstance = DeliveryTaking.get(id)
        if (!deliveryTakingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deliveryTaking.label', default: 'DeliveryTaking'), id])
            redirect(action: "list")
            return
        }

        [deliveryTakingInstance: deliveryTakingInstance]
    }

    def edit(Long id) {
        def deliveryTakingInstance = DeliveryTaking.get(id)
        if (!deliveryTakingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deliveryTaking.label', default: 'DeliveryTaking'), id])
            redirect(action: "list")
            return
        }

        [deliveryTakingInstance: deliveryTakingInstance]
    }

    def update(Long id, Long version) {
        def deliveryTakingInstance = DeliveryTaking.get(id)
        if (!deliveryTakingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deliveryTaking.label', default: 'DeliveryTaking'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (deliveryTakingInstance.version > version) {
                deliveryTakingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'deliveryTaking.label', default: 'DeliveryTaking')] as Object[],
                          "Another user has updated this DeliveryTaking while you were editing")
                render(view: "edit", model: [deliveryTakingInstance: deliveryTakingInstance])
                return
            }
        }

        deliveryTakingInstance.properties = params

        if (!deliveryTakingInstance.save(flush: true)) {
            render(view: "edit", model: [deliveryTakingInstance: deliveryTakingInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'deliveryTaking.label', default: 'DeliveryTaking'), deliveryTakingInstance.id])
        redirect(action: "show", id: deliveryTakingInstance.id)
    }

    def delete(Long id) {
        def deliveryTakingInstance = DeliveryTaking.get(id)
        if (!deliveryTakingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deliveryTaking.label', default: 'DeliveryTaking'), id])
            redirect(action: "list")
            return
        }

        try {
            deliveryTakingInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'deliveryTaking.label', default: 'DeliveryTaking'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'deliveryTaking.label', default: 'DeliveryTaking'), id])
            redirect(action: "show", id: id)
        }
    }
}
