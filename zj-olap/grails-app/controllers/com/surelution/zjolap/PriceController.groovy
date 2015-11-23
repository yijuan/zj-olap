package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class PriceController {

    static allowedMethods = [save: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [priceInstanceList: Price.list(params), priceInstanceTotal: Price.count()]
    }

    def create() {
        [priceInstance: new Price(params)]
    }

    def save() {
        def priceInstance = new Price(params)
        if (!priceInstance.save(flush: true)) {
          //  render(view: "create", model: [priceInstance: priceInstance])
			redirect(action: "list")
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'price.label', default: 'Price'), priceInstance.id])
       // redirect(action: "show", id: priceInstance.id)
		redirect(action: "list")
    }

    def show(Long id) {
        def priceInstance = Price.get(id)
        if (!priceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), id])
            redirect(action: "list")
            return
        }

        [priceInstance: priceInstance]
    }

    def edit(Long id) {
        def priceInstance = Price.get(id)
        if (!priceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), id])
            redirect(action: "list")
            return
        }

        [priceInstance: priceInstance]
    }

    def update(Long id, Long version) {
        def priceInstance = Price.get(id)
        if (!priceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), id])
            redirect(action: "list",params:[max:params.max,offset:params.offset])
            return
        }

        if (version != null) {
            if (priceInstance.version > version) {
                priceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'price.label', default: 'Price')] as Object[],
                          "Another user has updated this Price while you were editing")
            //    render(view: "edit", model: [priceInstance: priceInstance])
				redirect(action: "list",params:[max:params.max,offset:params.offset])
                return
            }
        }

        priceInstance.properties = params

        if (!priceInstance.save(flush: true)) {
           // render(view: "edit", model: [priceInstance: priceInstance])
			redirect(action: "list",params:[max:params.max,offset:params.offset])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'price.label', default: 'Price'), priceInstance.id])
        //redirect(action: "show", id: priceInstance.id)
		redirect(action: "list",params:[max:params.max,offset:params.offset])
    }

    def delete(Long id) {
        def priceInstance = Price.get(id)
        if (!priceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'price.label', default: 'Price'), id])
            redirect(action: "list")
            return
        }

        try {
            priceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'price.label', default: 'Price'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'price.label', default: 'Price'), id])
        //   redirect(action: "show", id: id)
			redirect(action: "list")
        }
    }
}
