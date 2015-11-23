package com.surelution.zjolap

import org.springframework.dao.DataIntegrityViolationException

class TimeByDayController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [timeByDayInstanceList: TimeByDay.list(params), timeByDayInstanceTotal: TimeByDay.count()]
    }

    def create() {
        [timeByDayInstance: new TimeByDay(params)]
    }

    def save() {
        def timeByDayInstance = new TimeByDay(params)
        if (!timeByDayInstance.save(flush: true)) {
            render(view: "create", model: [timeByDayInstance: timeByDayInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'timeByDay.label', default: 'TimeByDay'), timeByDayInstance.id])
        redirect(action: "show", id: timeByDayInstance.id)
    }

    def show(Long id) {
        def timeByDayInstance = TimeByDay.get(id)
        if (!timeByDayInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'timeByDay.label', default: 'TimeByDay'), id])
            redirect(action: "list")
            return
        }

        [timeByDayInstance: timeByDayInstance]
    }

    def edit(Long id) {
        def timeByDayInstance = TimeByDay.get(id)
        if (!timeByDayInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'timeByDay.label', default: 'TimeByDay'), id])
            redirect(action: "list")
            return
        }

        [timeByDayInstance: timeByDayInstance]
    }

    def update(Long id, Long version) {
        def timeByDayInstance = TimeByDay.get(id)
        if (!timeByDayInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'timeByDay.label', default: 'TimeByDay'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (timeByDayInstance.version > version) {
                timeByDayInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'timeByDay.label', default: 'TimeByDay')] as Object[],
                          "Another user has updated this TimeByDay while you were editing")
                render(view: "edit", model: [timeByDayInstance: timeByDayInstance])
                return
            }
        }

        timeByDayInstance.properties = params

        if (!timeByDayInstance.save(flush: true)) {
            render(view: "edit", model: [timeByDayInstance: timeByDayInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'timeByDay.label', default: 'TimeByDay'), timeByDayInstance.id])
        redirect(action: "show", id: timeByDayInstance.id)
    }

    def delete(Long id) {
        def timeByDayInstance = TimeByDay.get(id)
        if (!timeByDayInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'timeByDay.label', default: 'TimeByDay'), id])
            redirect(action: "list")
            return
        }

        try {
            timeByDayInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'timeByDay.label', default: 'TimeByDay'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'timeByDay.label', default: 'TimeByDay'), id])
            redirect(action: "show", id: id)
        }
    }
}
