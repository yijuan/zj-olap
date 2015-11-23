package com.surelution.zjolap

import groovy.time.TimeCategory

import org.springframework.dao.DataIntegrityViolationException

class SalesPlannedAmountController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def springSecurityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		def user = springSecurityService.currentUser
		def branch = UserBranch.findByUser(user)
		def salesList
		if(branch) {
			salesList = Sales.findAllByBranch(branch)
		}
		if(!salesList) {
			salesList = Sales.listOrderByBranch()
		}
        params.max = Math.min(max ?: 10, 100)
		def sales
		def month
		
		if(params['sales.id']) {
			sales = Sales.get(params['sales.id'])
		}
		
		if(params.month) {
			month = params.month
		}
		
		def amounts = SalesPlannedAmount.createCriteria().list(max:params.max, offset:params.offset){
			if(sales) {
				eq('sales', sales)
			}
			if(month) {
				eq('month', month)
			}
		}
		
        [salesPlannedAmountInstanceList: amounts, sales:sales, salesList:salesList,salesPlannedAmountInstanceTotal: amounts.totalCount]
    }

    def create() {
		def user = springSecurityService.currentUser
		def branch = UserBranch.findByUser(user)
		def salesList
		if(branch) {
			salesList = Sales.findAllByBranch(branch)
		}
		if(!salesList) {
			salesList = Sales.listOrderByBranch()
		}
        [salesPlannedAmountInstance: new SalesPlannedAmount(params), salesList:salesList]
    }
	
	def progress() {
		def month = params.month
		def user = springSecurityService.currentUser
		def branch = UserBranch.findByUser(user)
		def salesList
		if(branch) {
			salesList = Sales.findAllByBranch(branch)
		}
		if(!salesList) {
			salesList = Sales.listOrderByBranch()
		}
		def sales
		if(params['sales.id']) {
			sales = Sales.get(params['sales.id'])
		}
		if(month) {
			def nextMonth
			use(TimeCategory) {
				nextMonth = month + 1.month
			}
			def ps = [month, month, nextMonth]
			def hql = "select spa, sum(so.quantity) from SalesPlannedAmount spa, SalesOrder so where spa.sales=so.sales and spa.month=? and so.salingAt between ? and ?"
			if(sales) {
				hql += " and spa.sales = ?"
				ps.add(sales)
			}
			if(branch) {
				hql += " and so.branch=?"
				ps.add(branch)
			}

			def spa = SalesPlannedAmount.executeQuery(
				hql, ps)
			[spa:spa, salesList:salesList]
		}
	}

    def save() {
        def salesPlannedAmountInstance = new SalesPlannedAmount(params)
        if (!salesPlannedAmountInstance.save(flush: true)) {
            render(view: "create", model: [salesPlannedAmountInstance: salesPlannedAmountInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount'), salesPlannedAmountInstance.id])
        redirect(action: "show", id: salesPlannedAmountInstance.id)
    }

    def show(Long id) {
        def salesPlannedAmountInstance = SalesPlannedAmount.get(id)
        if (!salesPlannedAmountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount'), id])
            redirect(action: "list")
            return
        }

        [salesPlannedAmountInstance: salesPlannedAmountInstance]
    }

    def edit(Long id) {
		def user = springSecurityService.currentUser
		def branch = UserBranch.findByUser(user)
		def salesList
		if(branch) {
			salesList = Sales.findAllByBranch(branch)
		}
		if(!salesList) {
			salesList = Sales.listOrderByBranch()
		}
        def salesPlannedAmountInstance = SalesPlannedAmount.get(id)
        if (!salesPlannedAmountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount'), id])
            redirect(action: "list")
            return
        }

        [salesPlannedAmountInstance: salesPlannedAmountInstance, salesList:salesList]
    }

    def update(Long id, Long version) {
        def salesPlannedAmountInstance = SalesPlannedAmount.get(id)
        if (!salesPlannedAmountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (salesPlannedAmountInstance.version > version) {
                salesPlannedAmountInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount')] as Object[],
                          "Another user has updated this SalesPlannedAmount while you were editing")
                render(view: "edit", model: [salesPlannedAmountInstance: salesPlannedAmountInstance])
                return
            }
        }

        salesPlannedAmountInstance.properties = params

        if (!salesPlannedAmountInstance.save(flush: true)) {
            render(view: "edit", model: [salesPlannedAmountInstance: salesPlannedAmountInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount'), salesPlannedAmountInstance.id])
        redirect(action: "show", id: salesPlannedAmountInstance.id)
    }

    def delete(Long id) {
        def salesPlannedAmountInstance = SalesPlannedAmount.get(id)
        if (!salesPlannedAmountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount'), id])
            redirect(action: "list")
            return
        }

        try {
            salesPlannedAmountInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'salesPlannedAmount.label', default: 'SalesPlannedAmount'), id])
            redirect(action: "show", id: id)
        }
    }
}
