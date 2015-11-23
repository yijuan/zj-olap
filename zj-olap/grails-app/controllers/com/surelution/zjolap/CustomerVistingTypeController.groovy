package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class CustomerVistingTypeController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [customerVistingTypeInstanceList: CustomerVistingType.list(params), customerVistingTypeInstanceTotal: CustomerVistingType.count()]
    }

    def create() {
        [customerVistingTypeInstance: new CustomerVistingType(params)]
    }

    def save() {
        def customerVistingTypeInstance = new CustomerVistingType(params)
        if (!customerVistingTypeInstance.save(flush: true)) {
            //render(view: "create", model: [customerVistingTypeInstance: customerVistingTypeInstance])
            redirect(action:'list')
			return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'customerVistingType.label', default: 'CustomerVistingType'), customerVistingTypeInstance.id])
       // redirect(action: "show", id: customerVistingTypeInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def customerVistingTypeInstance = CustomerVistingType.get(id)
        if (!customerVistingTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerVistingType.label', default: 'CustomerVistingType'), id])
            redirect(action: "list")
            return
        }

        [customerVistingTypeInstance: customerVistingTypeInstance]
    }

    def edit(Long id) {
        def customerVistingTypeInstance = CustomerVistingType.get(id)
        if (!customerVistingTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerVistingType.label', default: 'CustomerVistingType'), id])
            redirect(action: "list")
            return
        }

        [customerVistingTypeInstance: customerVistingTypeInstance]
    }

    def update(Long id, Long version) {
        def customerVistingTypeInstance = CustomerVistingType.get(id)
        if (!customerVistingTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerVistingType.label', default: 'CustomerVistingType'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (customerVistingTypeInstance.version > version) {
                customerVistingTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'customerVistingType.label', default: 'CustomerVistingType')] as Object[],
                          "Another user has updated this CustomerVistingType while you were editing")
              //  render(view: "edit", model: [customerVistingTypeInstance: customerVistingTypeInstance])
				redirect(action:'list')
				  return
            }
        }

        customerVistingTypeInstance.properties = params

        if (!customerVistingTypeInstance.save(flush: true)) {
           // render(view: "edit", model: [customerVistingTypeInstance: customerVistingTypeInstance])
			redirect(action:'list')
			 return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'customerVistingType.label', default: 'CustomerVistingType'), customerVistingTypeInstance.id])
        //redirect(action: "show", id: customerVistingTypeInstance.id)
		redirect(action:'list')
    }

    def delete(Long id) {
        def customerVistingTypeInstance = CustomerVistingType.get(id)
        if (!customerVistingTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerVistingType.label', default: 'CustomerVistingType'), id])
            redirect(action: "list")
            return
        }

        try {
            customerVistingTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'customerVistingType.label', default: 'CustomerVistingType'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'customerVistingType.label', default: 'CustomerVistingType'), id])
            //redirect(action: "show", id: id)
			redirect(action:'list')
        }
    }
}
