package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class TankFarmController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tankFarmInstanceList: TankFarm.list(params), tankFarmInstanceTotal: TankFarm.count()]
    }

    def create() {
        [tankFarmInstance: new TankFarm(params)]
    }

    def save() {
        def tankFarmInstance = new TankFarm(params)
        if (!tankFarmInstance.save(flush: true)) {
            render(view: "create", model: [tankFarmInstance: tankFarmInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tankFarm.label', default: 'TankFarm'), tankFarmInstance.id])
        redirect(action: "show", id: tankFarmInstance.id)
    }

    def show(Long id) {
        def tankFarmInstance = TankFarm.get(id)
        if (!tankFarmInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tankFarm.label', default: 'TankFarm'), id])
            redirect(action: "list")
            return
        }

        [tankFarmInstance: tankFarmInstance]
    }

    def edit(Long id) {
        def tankFarmInstance = TankFarm.get(id)
        if (!tankFarmInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tankFarm.label', default: 'TankFarm'), id])
            redirect(action: "list")
            return
        }

        [tankFarmInstance: tankFarmInstance]
    }

    def update(Long id, Long version) {
        def tankFarmInstance = TankFarm.get(id)
        if (!tankFarmInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tankFarm.label', default: 'TankFarm'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tankFarmInstance.version > version) {
                tankFarmInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'tankFarm.label', default: 'TankFarm')] as Object[],
                          "Another user has updated this TankFarm while you were editing")
                render(view: "edit", model: [tankFarmInstance: tankFarmInstance])
                return
            }
        }

        tankFarmInstance.properties = params

        if (!tankFarmInstance.save(flush: true)) {
            render(view: "edit", model: [tankFarmInstance: tankFarmInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tankFarm.label', default: 'TankFarm'), tankFarmInstance.id])
        redirect(action: "show", id: tankFarmInstance.id)
    }

    def delete(Long id) {
        def tankFarmInstance = TankFarm.get(id)
        if (!tankFarmInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tankFarm.label', default: 'TankFarm'), id])
            redirect(action: "list")
            return
        }

        try {
            tankFarmInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tankFarm.label', default: 'TankFarm'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tankFarm.label', default: 'TankFarm'), id])
            redirect(action: "show", id: id)
        }
    }
}
