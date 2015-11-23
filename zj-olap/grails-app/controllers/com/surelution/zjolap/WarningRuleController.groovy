package com.surelution.zjolap

import groovy.time.TimeCategory

import org.springframework.dao.DataIntegrityViolationException

class WarningRuleController {

    static allowedMethods = [save: "POST"]

	def springSecurityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		
		def branch
		if(getLogBranch()) {
			branch = getLogBranch()
		}
		
		def result  = WarningRule.createCriteria().list(max:10, offset:params.offset) {
			if(branch) {
				 eq('branch', branch)
			}
		}
		
        [warningRuleInstanceList: result, warningRuleInstanceTotal: result.totalCount]
    }
	
	private def getLogBranch() {
		def user = springSecurityService.currentUser
		def loginBranch = user?.branch
		return loginBranch
	}
	
	def listBaseStockWarning(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		
		def branch
		if(params.branchId) { 
			branch= Branch.get(Long.parseLong(params.branchId));
		}
		if(getLogBranch()) {
			branch = getLogBranch()
		}
		
		if(!branch) {
			branch = Branch.list().get(0);
		}
		
		 def customerName = params.customerName;
		
		//def warningQty = geBaseStocktWarningQty(branch) 
		//def rule = getBaseStockWarningRule(branch);
		//def customers = getBaseStockWarningCustomer(rule,warningQty)
		
		
		def settingIds = getBaseStockWarningCustomer(branch,customerName)
		
		
		
		//迭代循环查询 客户的最低客户存量
		
		//反悔客户低于客存列表
		
		
		//def customers = getBaseStockFineCustomers(warningQty,branch);
		
		def result = WarningRuleToBranchCustomer.createCriteria().list(max:10, offset:params.offset) {
			
			if(settingIds) {
				'in'('id',settingIds)
			}else {
				eq('id',-1L)
			}
		}
		
		
		List<WarningRuleToBranchCustomerSummary> resultSummary = new ArrayList<WarningRuleToBranchCustomerSummary>();
		
		result.each {
			WarningRuleToBranchCustomerSummary summary = new WarningRuleToBranchCustomerSummary();
			summary.rule = it.rule;
			summary.customerBranch = it?.customerBranch;
			summary.baseQty = getStockQty(summary.customerBranch, it?.rule.value, it.rule?.gasType);
			summary.warningQty = it?.rule.value;
			
			resultSummary.add(summary);
		}
		
		
//		List<CustomerBranchSummary> resultSummary = new ArrayList<CustomerBranchSummary>();
//		result.each {
//			CustomerBranchSummary summary = new CustomerBranchSummary();
//			summary.customer = it.customer
//			summary.branch = it.branch
//			summary.date = it.date
//			summary.warningQty = getWarningQty(it,"baseStockQtyWarning");
//			summary.baseQty = getStockQty(it, warningQty, rule?.gasType);
//			resultSummary.add(summary);
//		}
//		
	
		
		[baseStockWarningInstanceList: resultSummary, baseStockWarningInstanceTotal: result.totalCount]
	}
	
	def getWarningQty(customerBranch,type,getType) {
		def branch = customerBranch?.branch;
		def rule = WarningRule.findByBranchAndTypeAndGasType(branch,type,gasType);
		def wrbc = WarningRuleToBranchCustomer.findByCustomerBranchAndRule(customerBranch,rule) 
		 return wrbc?.rule?.value
	}
	
	def listDaysSalesOrderWarning(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		
		def branch = getLogBranch()
		if(!branch && params.branchId) { 
			branch= Branch.get(Long.parseLong(params.branchId))
		}
		
		if(!branch) {
			branch = Branch.list().get(0);
		}
		
		def customerName = params.customerName;
		def settingIds = getDaysSalesWarningCustomer(branch,customerName)
		
		println settingIds
		
		def result = WarningRuleToBranchCustomer.createCriteria().list(max:10, offset:params.offset) {
			if(settingIds) {
				'in'('id',settingIds)
			}else {
				eq('id',-1L)
			}
		}
		
		List<WarningRuleToBranchCustomerSummary> resultSummary = new ArrayList<WarningRuleToBranchCustomerSummary>();
		
		result.each {
			WarningRuleToBranchCustomerSummary summary = new WarningRuleToBranchCustomerSummary();
			summary.rule = it.rule;
			summary.customerBranch = it.customerBranch;
			
			summary.lastDate = getLastStockDate(it?.customerBranch, it?.rule?.gasType)
			summary.warningQty = it?.rule.value;
			
			resultSummary.add(summary);
		}

		[baseStockWarningInstanceList: resultSummary, baseStockWarningInstanceTotal: result.totalCount]
	}
	
	
	def listDaysStockWarning(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		
		def branch
		if(params.branchId) { 
			branch= Branch.get(Long.parseLong(params.branchId));
		}
		if(getLogBranch()) {
			branch = getLogBranch()
		}
		
		if(!branch) {
			branch = Branch.list().get(0);
		}
		
		//def warningQty = geDaysStocktWarningQty(branch)
		//def rule = getDaysStockWarningRule(branch);
		
		def customerName = params.customerName;
		
		def settingIds = getDaysStockWarningCustomer(branch,customerName)
		
		//def customers = getBaseStockFineCustomers(warningQty,branch);
		
		def result = WarningRuleToBranchCustomer.createCriteria().list(max:10, offset:params.offset) {
			if(settingIds) {
				'in'('id',settingIds)
			}else {
				eq('id',-1L)
			}
		}
		
		List<WarningRuleToBranchCustomerSummary> resultSummary = new ArrayList<WarningRuleToBranchCustomerSummary>();
		
		result.each {
			WarningRuleToBranchCustomerSummary summary = new WarningRuleToBranchCustomerSummary();
			summary.rule = it.rule;
			summary.customerBranch = it.customerBranch;
			//summary.baseQty = getLastStockDate(it?.customerBranch, it?.rule.value, it?.rule?.gasType)
			summary.lastDate = getLastSaleDate(it?.customerBranch, it?.rule?.gasType)
			summary.warningQty = it?.rule.value;
			
			resultSummary.add(summary);
		}

		[baseStockWarningInstanceList: resultSummary, baseStockWarningInstanceTotal: result.totalCount]
	}
	
	
	private List getDaysSalesWarningCustomer(branch,customerName) {
		List<Long>  result = new ArrayList<Long>();
		
		def settings = WarningRuleToBranchCustomer.createCriteria().list (max:100000, offset:params.offset){
			rule {
				eq("type","daysSaleOrderQtyWarning")
			}
			customerBranch {
				eq("branch",branch)
				if(customerName) {
					customer {
						like("name","%"+customerName+"%")
					}
				}
			}
		}
		settings.each { wrbc ->
			 def customerBranch= wrbc?.customerBranch
			 def customer = customerBranch?.customer
			 def warningQty = wrbc?.rule?.value as Integer
			 def gasType = wrbc?.rule?.gasType;
			 def now = new Date()
			 def from
			 use(TimeCategory) {
				 from = new Date() - warningQty.day
			 }
			 def c = SalesOrder.createCriteria().get() {
				 projections {
					 count('id')
				 }
				 eq('customerBranch', customerBranch)
				 between('salingAt', from, now)
				 eq('gasType', gasType)
			 }
			 
			 if(c == 0) {
				 result.add(wrbc.id)
			 }
		}
		
		return result;
	}
	
	private List getDaysStockWarningCustomer(branch,customerName) {
		List<Long>  result = new ArrayList<Long>();
		
		//def settings = WarningRuleToBranchCustomer.findAllByRule(rule)
		
		def settings = WarningRuleToBranchCustomer.createCriteria().list (max:100000, offset:params.offset){
			rule {
				eq("type","daysStockQtyWarning")
			}
			customerBranch {
				eq("branch",branch)
				if(customerName) {
					customer {
						like("name","%"+customerName+"%")
					}
				}
			}
		}
		
		settings.each { wrbc ->
			 def customerBranch= wrbc?.customerBranch
			 def customer = customerBranch?.customer
			 def warningQty = wrbc?.rule?.value;
			 def gasType = wrbc?.rule?.gasType;
			 if(isGEDaysStockQty(customerBranch,warningQty,gasType)) {
				 result.add(wrbc.id)
			 }
		}
		
		return result;
	}
	
	private Date getLastSaleDate(customerBranch,gasType) {
		def stockList= SalesOrder.createCriteria().list {
			eq("customerBranch",customerBranch)
			eq("gasType",gasType)
			eq('isVail',true)
			eq('status',"ABLE")
			order("salingAt", "desc")
		}
		
		if(stockList) {
			return stockList.get(0)?.cdate
		}
		
		return null;
	}
	
	private Date getLastStockDate(customerBranch,gasType) {
		def stockList= CustomerStock.createCriteria().list {
			eq("customerBranch",customerBranch)
			eq("gasType",gasType)
			eq('isVail',true)
			eq('status',"ABLE")
			order("cdate", "desc")
		}
		
		if(stockList) {
			return stockList.get(0)?.cdate
		}
		
		return null;
	}
	
	private isGEDaysStockQty(customerBranch,warningQty,gasType) {
		
		def cal = Calendar.instance;
		def days= warningQty as Integer
		
		cal.set(Calendar.DATE, -days)
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		
		//def date  = new Date();
		//date.time = cal.time
		
		def stockList= CustomerStock.createCriteria().list {
			eq("customerBranch",customerBranch)
			eq("gasType",gasType)
			eq('isVail',true)
			eq('status',"ABLE")
			gt("cdate", cal.time)
		}
		
		
		
		if(stockList) {
			return false
		}
		
		return true;
	}
	
	private WarningRule getDaysStockWarningRule(branch) {
		def warningRule  = WarningRule.findByBranchAndType(branch,"daysStockQtyWarning");
		return warningRule
	}
	
	private double geDaysStocktWarningQty(branch) {
		def warningRule  =getDaysStockWarningRule(branch)
		if(warningRule) {
			return warningRule.value
		}
		
		return -1;
		
	}
	
	
	
	private List<Long> getBaseStockWarningCustomer(branch,customerName) {
		
		
		List<Long>  result = new ArrayList<Long>();
		
		def settings = WarningRuleToBranchCustomer.createCriteria().list (max:100000, offset:params.offset){
			rule {
				eq("type","baseStockQtyWarning")
			}
			customerBranch {
				eq("branch",branch)
				if(customerName) {
					customer {
						like("name","%"+customerName+"%")
					}
				}
			}
		}
		
		settings.each { wrbc ->
			def customerBranch= wrbc?.customerBranch
			def customer = customerBranch?.customer
			def warningQty = wrbc?.rule?.value;
			def gasType = wrbc?.rule?.gasType;
			if(isBlowStockQty(customerBranch,warningQty,gasType)) {
				result.add(wrbc.id)
			}
	   }
		
		return result
	}
	
	
	private isBlowStockQty(customerBranch,warningQty,gasType) {
		if(customerBranch&&warningQty!=-1) {
			def stockQty = getStockQty(customerBranch,warningQty,gasType);
			return warningQty>=stockQty;
		}
		
		return false;
	}
	
	
	private double getStockQty(customerBranch,warningQty,gasType) {
		def soQty = 0;
		def stockQty = 0;
		def initQty = 0;
		
		//获得SO总销售数据
		def soList= SalesOrder.createCriteria().list {
			eq("customerBranch",customerBranch)
			eq("gasType",gasType)
			eq('isVail',true)
			eq('status',"ABLE")
			projections {
				sum("quantity")
			}
		}
		if(soList&&soList.get(0)) {
			soQty = soList.get(0)
		}
		
		
		//获得init促使库存数据
		def stockList= CustomerStock.createCriteria().list {
			eq("customerBranch",customerBranch)
			eq("gasType",gasType)
			eq('isVail',true)
			eq('status',"ABLE")
			projections {
				sum("stockQty")
			}
		}
		if(stockList&&stockList.get(0)) {
			stockQty = stockList.get(0)
		}
		
		//获得提货数据
		
		def initList= InitCustomerStock.createCriteria().list {
			eq("customerBranch",customerBranch)
			eq("gasType",gasType)
			projections {
				sum("stockQty")
			}
		}
		if(initList&&initList.get(0)) {
			initQty = initList.get(0)
		}
		
		
		return soQty+initQty-stockQty;
	}
	
	private WarningRule getBaseStockWarningRule(branch) {
		def warningRule  = WarningRule.findByBranchAndType(branch,"baseStockQtyWarning");
		
		return warningRule
	}
	
	private double geBaseStocktWarningQty(branch) {
		def warningRule  =getBaseStockWarningRule(branch)
		if(warningRule) {
			return warningRule.value
		}
		
		return -1;
		
	}

    def create() {
        [warningRuleInstance: new WarningRule(params)]
    }

    def save() {
        def warningRuleInstance = new WarningRule(params)
		
		if(getLogBranch()) {
			warningRuleInstance.branch = getLogBranch()
		}
		
		def msg =validateForSave(warningRuleInstance)
		if(msg){
			flash.message = msg
			//render(view: "create", model: [warningRuleInstance: warningRuleInstance])
			redirect(action:'list')
			return
		}
        if (!warningRuleInstance.save(flush: true)) {
           // render(view: "create", model: [warningRuleInstance: warningRuleInstance])
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'warningRule.label', default: 'WarningRule'), warningRuleInstance.id])
       // redirect(action: "show", id: warningRuleInstance.id)
		redirect(action:'list')
    }

	
	private String validateForSave(WarningRule rule) {
		def result  = WarningRule.findByBranchAndTypeAndValue(rule.branch,rule.type,rule.value);
		if(result) {
			return rule.typeName +"类型的警告数据库已经有了，不能再操作！";
		}
		return "";
	}
	
    def show(Long id) {
        def warningRuleInstance = WarningRule.get(id)
        if (!warningRuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningRule.label', default: 'WarningRule'), id])
            redirect(action: "list")
            return
        }

        [warningRuleInstance: warningRuleInstance]
    }

    def edit(Long id) {
        def warningRuleInstance = WarningRule.get(id)
        if (!warningRuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningRule.label', default: 'WarningRule'), id])
            redirect(action: "list")
            return
        }

        [warningRuleInstance: warningRuleInstance]
    }

    def update(Long id, Long version) {
        def warningRuleInstance = WarningRule.get(id)
        if (!warningRuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningRule.label', default: 'WarningRule'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (warningRuleInstance.version > version) {
                warningRuleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'warningRule.label', default: 'WarningRule')] as Object[],
                          "Another user has updated this WarningRule while you were editing")
              //  render(view: "edit", model: [warningRuleInstance: warningRuleInstance])
				redirect(action:'list')
                return
            }
        }

        warningRuleInstance.properties = params

		def msg =validateForSave(warningRuleInstance)
		if(msg){
			flash.message = msg
		//	render(view: "edit", model: [warningRuleInstance: warningRuleInstance])
			redirect(action:'list')
			return
		}
		
        if (!warningRuleInstance.save(flush: true)) {
          //  render(view: "edit", model: [warningRuleInstance: warningRuleInstance])
			redirect(action:'list')
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'warningRule.label', default: 'WarningRule'), warningRuleInstance.id])
      //  redirect(action: "show", id: warningRuleInstance.id)
		redirect(action:'list')
    }

    def delete(Long id) {
        def warningRuleInstance = WarningRule.get(id)
        if (!warningRuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'warningRule.label', default: 'WarningRule'), id])
            redirect(action: "list")
            return
        }

        try {
            warningRuleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'warningRule.label', default: 'WarningRule'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'warningRule.label', default: 'WarningRule'), id])
        //    redirect(action: "show", id: id)
			redirect(action:'list') 
        }
    }
	
	class WarningRuleToBranchCustomerSummary extends WarningRuleToBranchCustomer {
		double baseQty
		double warningQty
		Date lastDate
	}
}