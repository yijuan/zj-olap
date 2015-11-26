package com.surelution.zjolap

import grails.converters.JSON
import java.text.SimpleDateFormat

import org.springframework.dao.DataIntegrityViolationException

class CustomerVistingController {

    static allowedMethods = [save: "POST"]
	
	def springSecurityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		def branch = UserBranch.findByUser(springSecurityService.currentUser)
		def sales = []
		if(branch) {
			sales = Sales.findAllByBranch(branch.branch)
		}
		params.max = Math.min(max ?: 10, 100)
		def customer
		def caller
		def timeFrom, timeTo
		if(params['customer.id']) {
			customer = Customer.get(params['customer.id'])
		}
		if(params['sales.id']) {
			caller = Sales.get(params['sales.id'])
		}
		if(params.vistingAtFrom) {
			timeFrom = params.date('vistingAtFrom', 'yyyy.MM.dd HH:mm')
		}
		if(params.vistingAtTo) {
			timeTo = params.date('vistingAtTo', 'yyyy.MM.dd HH:mm')
		}
		
		params.max = Math.min(max ?: 10, 100)
		def vistings = CustomerVisting.createCriteria().list(max:params.max, offset:params.offset) {
			if(customer) {
				eq('customer', customer)
			}
			if(caller) {
				eq('sales', caller)
			}
			if(timeFrom) {
				ge('vistingAt', timeFrom)
			}
			if(timeTo) {
				le('vistingAt', timeTo)
			}
		}
		
		[customerVistingInstanceList: vistings, customerVistingInstanceTotal: vistings.totalCount, sales:sales]
    }

    def create() {
		def branch = UserBranch.findByUser(springSecurityService.currentUser)
		def sales
		if(branch) {
			sales = Sales.findAllByBranch(branch.branch)
		}
	
        [customerVistingInstance: new CustomerVisting(params), sales:sales]
    }

    def save() {
		params.vistingAt = params.date('vistingAt', 'yyyy.MM.dd hh:mm')		
        def customerVistingInstance = new CustomerVisting(params)		
		customerVistingInstance.operator = springSecurityService.currentUser
        if (!customerVistingInstance.save(flush: true)) {
           // render(view: "create", model: [customerVistingInstance: customerVistingInstance])			
			flash.message="客户回访没有增加成功，请重新操作！"
            redirect(action:'list')
			  return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'customerVisting.label', default: 'CustomerVisting'), customerVistingInstance.id])
       // redirect(action: "show", id: customerVistingInstance.id)
		redirect(action:'list')
    }

    def show(Long id) {
        def customerVistingInstance = CustomerVisting.get(id)
        if (!customerVistingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerVisting.label', default: 'CustomerVisting'), id])
            redirect(action: "list")
            return
        }

        [customerVistingInstance: customerVistingInstance]
    }

    def edit(Long id) {
		def branch = UserBranch.findByUser(springSecurityService.currentUser)
		def sales
		if(branch) {
			sales = Sales.findAllByBranch(branch.branch)
		}
        def customerVistingInstance = CustomerVisting.get(id)
        if (!customerVistingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerVisting.label', default: 'CustomerVisting'), id])
            redirect(action: "list")
            return
        }

        [customerVistingInstance: customerVistingInstance, sales:sales]
    }

    def update(Long id, Long version) {
        def customerVistingInstance = CustomerVisting.get(id)
        if (!customerVistingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerVisting.label', default: 'CustomerVisting'), id])
			println customerVistingInstance.errors
			redirect(action: "list")
            return
        }

        if (version != null) {
            if (customerVistingInstance.version > version) {
                customerVistingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'customerVisting.label', default: 'CustomerVisting')] as Object[],
                          "Another user has updated this CustomerVisting while you were editing")
               // render(view: "edit", model: [customerVistingInstance: customerVistingInstance])
				println customerVistingInstance.errors
				redirect(action:'list')
				  return
            }
        }

        customerVistingInstance.properties = params	

        if (!customerVistingInstance.save(flush: true)) {
          //  render(view: "edit", model: [customerVistingInstance: customerVistingInstance])
			println customerVistingInstance.errors
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'customerVisting.label', default: 'CustomerVisting'), customerVistingInstance.id])
        //redirect(action: "show", id: customerVistingInstance.id)
		redirect(action:'list')
    }

    def delete(Long id) {
        def customerVistingInstance = CustomerVisting.get(id)
        if (!customerVistingInstance) {
           // flash.message = message(code: 'default.not.found.message', args: [message(code: 'customerVisting.label', default: 'CustomerVisting'), id])
            flash.message="该用户不存在"
			 redirect(action: "list")
            return
        }

        try {
            customerVistingInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'customerVisting.label', default: 'CustomerVisting'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
          //  flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'customerVisting.label', default: 'CustomerVisting'), id])
        //    redirect(action: "show", id: id)
			flash.message="客户回访删除失败"
			redirect(action:'list')
        }
    }

	def coustomerlist = {
		def user = springSecurityService.currentUser
		def term = params.term
		
		
		def cs =Customer.findAllByNameLike("%${term}%").collect() {
			['id' : it.id,'label':it.name, 'value':it.name]
		}
		
		render cs as JSON
	}
}
