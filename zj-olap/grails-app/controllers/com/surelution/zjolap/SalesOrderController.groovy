package com.surelution.zjolap

import grails.converters.JSON

import java.text.SimpleDateFormat

import org.apache.jasper.compiler.Node.ParamsAction;
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.dao.DataIntegrityViolationException


class SalesOrderController {

	static allowedMethods = [save: "POST"]

	def springSecurityService
	
	def salesOrderService

	def index() {
		redirect(action: "list", params: params)
	}
         
	def list(Integer max) {
		
		if(params["downExcelFile"]) {
			downExcelFile()
		}
		
		def user = springSecurityService.currentUser
		def loginBranch = user.branch

		params.max = Math.min(max ?: 10, 100)
		def branch, salingAtFrom, salingAtTo, orderFormNo = params.orderFormNo

		def sdf = new SimpleDateFormat("yyyy.MM.dd")

		if(params['branch.id']) {
			branch = Branch.get(params['branch.id'])
		}

		if(loginBranch) {
			branch  = loginBranch
		}

		if(params.salingAtFrom) {
			try{
				salingAtFrom = sdf.parse(params.salingAtFrom)
			}catch(e){
			}
		}
		if(params.salingAtTo) {
			try{
				salingAtTo = sdf.parse(params.salingAtTo)
			}catch(e){
			}
		}

		def sales = SalesOrder.createCriteria().list(max:10, offset:params.offset) {
			def sortBy = params['sort']
			if(branch) {
				eq('branch', branch)
			}
			if(salingAtFrom) {
				ge('salingAt', salingAtFrom)
			}
			if(salingAtTo) {
				le('salingAt', salingAtTo)
			}
			if(orderFormNo) {
				eq('orderFormNo', orderFormNo)
			}
			if(sortBy == 'customerType') {
				createAlias('customer', 'c')
				createAlias('c.customerType', 't3')
				createAlias('t3.level2', 't2')
			}
			
			if(params.financialMonth_Year) {
				month{
					eq('year', Integer.parseInt(params.financialMonth_Year))
				}
			}
			
			if(params.financialMonth_Month) {
				month{
					eq('month', Integer.parseInt(params.financialMonth_Month))
				}
			}
			
			eq('isVail',true)
			eq('status',"ABLE")
			
			if(sortBy) {
				if(sortBy == 'customerType') {
					order('t2.level1', params['order']?params['order']:"desc")
				} else {
					order(sortBy, params['order']?params['order']:"desc")
				}
			}
		}
		[salesOrderInstanceList: sales, salesOrderInstanceTotal: sales.totalCount]
	}



	def downExcelFile() {
		
		def user = springSecurityService.currentUser
		def loginBranch = user.branch

		def branch, salingAtFrom, salingAtTo, orderFormNo = params.orderFormNo

		def sdf = new SimpleDateFormat("yyyy-MM-dd")

		if(params['branch.id']) {
			branch = Branch.get(params['branch.id'])
		}

		if(loginBranch) {
			branch  = loginBranch
		}

		if(params.salingAtFrom) {
			try{
				salingAtFrom = sdf.parse(params.salingAtFrom)
			}catch(e){
			}
		}
		if(params.salingAtTo) {
			try{
				salingAtTo = sdf.parse(params.salingAtTo)
			}catch(e){
			}
		}

		def sales = SalesOrder.createCriteria().list() {
			if(branch) {
				eq('branch', branch)
			}
			if(salingAtFrom) {
				ge('salingAt', salingAtFrom)
			}
			if(salingAtTo) {
				le('salingAt', salingAtTo)
			}
			if(orderFormNo) {
				eq('orderFormNo', orderFormNo)
			}
			
			if(params.financialMonth_Year) {
				month{
					eq('year', Integer.parseInt(params.financialMonth_Year))
				}
			}
			
			if(params.financialMonth_Month) {
				month{
					eq('month', Integer.parseInt(params.financialMonth_Month))
				}
			}
			eq('isVail',true)
			eq('status',"ABLE")
		}

		downExcel(sales)
	}

	private void downExcel(sales) {
		def head =[
			"分公司",
			"销售环节",
			"结算月",
			"客户名称",
			"订单号",
			"订单开单日期",
			"油品品种",
			"油品品号",
			"数量",
			"销售单价",
			"销售收入",
			"客户性质",
			"机构用户分类",
			"工业分类",
			"批发到位价",
			"客户经理",
			"是否大单",
			"是否小额配送"
		];

		HSSFWorkbook wb = new HSSFWorkbook();
		def sheet = wb.createSheet();
		
		def row= sheet.createRow(0);

		//设置头
		for(int i=0;i<head.size();i++) {
			 def cell= row.createCell(i);
			// cell.setCellStyle(null)
			 cell.setCellValue(head.get(i));
		}
		
		def idx = 1;
		
		
		
		//设置主体内容
		sales.each {salesOrderInstance ->
			def rowbody= sheet.createRow(idx);
			//分公司
			createCell(0,getStringValue(salesOrderInstance?.branch?.name),rowbody);
			//销售环节
			createCell(1,getStringValue(salesOrderInstance?.salingtype?.name),rowbody);
			//结算月
			def month = salesOrderInstance?.month?.year+"-"+salesOrderInstance?.month?.month;
			createCell(2,getStringValue(month),rowbody);
			//客户名称
			createCell(3,getStringValue(salesOrderInstance?.customer?.name),rowbody);
			//订单号
			createCell(4,getStringValue(salesOrderInstance?.orderFormNo),rowbody);
			//订单开单日期
			createCell(5,getDateStringValue(salesOrderInstance?.timeByDay?.date),rowbody);
			//油品品种
			createCell(6,getStringValue(salesOrderInstance?.gasType?.category?.name),rowbody);
			//油品品号
			createCell(7,getStringValue(salesOrderInstance?.gasType?.name),rowbody);
			//数量
			createCell(8,salesOrderInstance?.quantity,rowbody);
			//销售单价
			createCell(9,salesOrderInstance?.purchasingUnitPrice,rowbody);
			//销售收入
			def value10 = salesOrderInstance.quantity * salesOrderInstance.purchasingUnitPrice;
			createCell(10,value10,rowbody);
			//客户性质
			
			createCell(11,getStringValue(salesOrderInstance?.customer?.customerType?.level2?.level1?.name),rowbody);
			//机构用户分类
			createCell(12,getStringValue(salesOrderInstance?.customer?.customerType?.level2?.displayName),rowbody);
			//工业分类
			createCell(13,getStringValue(salesOrderInstance?.customer?.customerType?.displayName),rowbody);
			//批发到位价
			createCell(14,salesOrderInstance?.listUnitPrice,rowbody);
			//客户经理
			createCell(15,getStringValue(salesOrderInstance?.sales?.name),rowbody);
			//是否大单
			createCell(16,getStringValue(salesOrderInstance?.bigCase?.name),rowbody);
			//是否小额配送
			createCell(17,salesOrderInstance?.salingtype?.id?.intValue() == 1?"是":"否",rowbody);
			
			idx++;
		}
		
		/*File f = new File("D:\\file.xls");
		
		FileOutputStream fos = new FileOutputStream(f);
		wb.write(fos);
		fos.flush();*/
		
		
		//下载
		//response.setContentType("application/octet-stream")
		response.setContentType("application/vnd.ms-excel")
		response.setHeader("Content-disposition", "attachment;filename=file.xls")
		def os = response.outputStream;
		wb.write(os)
		//os << wb.getBytes()
		//os.write(os);
		os.flush()
		return
	
	}
	private def getDateStringValue(value) {
		def sdf = new SimpleDateFormat("yyyy-MM-dd");
		def result = "";
		try {
			result = sdf.format(value);
		}catch(Exception) {
		}
		
		return result
	}
	private def getStringValue(value) {
		if(value) {
			return value+"";
		}
		
		return "";
	}

	private def createCell(index,value,row) {
		def cell= row.createCell(index, HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}
	
	
	def listApprove(Integer max) {
		def user = springSecurityService.currentUser
		def loginBranch = user.branch

		params.max = Math.min(max ?: 10, 100)
		def branch, salingAtFrom, salingAtTo, orderFormNo = params.orderFormNo

		def sdf = new SimpleDateFormat("yyyy.MM.dd")

		if(params['branch.id']) {
			branch = Branch.get(params['branch.id'])
		}

		if(loginBranch) {
			branch  = loginBranch
		}

		if(params.salingAtFrom) {
			try{
				salingAtFrom = sdf.parse(params.salingAtFrom)
			}catch(e){
			}
		}
		if(params.salingAtTo) {
			try{
				salingAtTo = sdf.parse(params.salingAtTo)
			}catch(e){
			}
		}

		def sales = SalesOrder.createCriteria().list(max:10, offset:params.offset) {
			if(branch) {
				eq('branch', branch)
			}
			if(salingAtFrom) {
				ge('salingAt', salingAtFrom)
			}
			if(salingAtTo) {
				le('salingAt', salingAtTo)
			}
			if(orderFormNo) {
				eq('orderFormNo', orderFormNo)
			}
			eq('isVail',true)
			ne('status',"ABLE")
		}
		[salesOrderInstanceList: sales, salesOrderInstanceTotal: sales.totalCount]
	}

	def create() {
		[salesOrderInstance: new SalesOrder(params)]
	}

	def save() {
		def user = springSecurityService.currentUser
		def loginBranch = user.branch

		def salesOrderInstance = new SalesOrder(params)
		
		println params
		
		salesOrderInstance.optionValue = SalesOrder.OPTION_VALUE_ADD
		salesOrderInstance.isVail = true
		salesOrderInstance.countPrice = 0
		salesOrderInstance.retailPrice=0
		salesOrderInstance.status = SalesOrder.STATUS_ABLE
		salesOrderInstance.bigCase = BigCase.get(1)   //修改后的bigcase,新增的

		if(salesOrderInstance.quantity && salesOrderInstance.purchasingUnitPrice) {
			salesOrderInstance.purchasingPrice = salesOrderInstance.quantity * salesOrderInstance.purchasingUnitPrice
		}

		if(salesOrderInstance.purchasingUnitPrice && salesOrderInstance.listUnitPrice) {
			salesOrderInstance.purchasingPriceRate =salesOrderInstance.purchasingUnitPrice/salesOrderInstance.listUnitPrice * 100
		}

		def cal = Calendar.instance
		cal.time = params.salingAt
		if(loginBranch) {
			salesOrderInstance.branch = loginBranch
			salesOrderInstance.status = SalesOrder.STATUS_ADD
		}
		salesOrderInstance.timeByDay = TimeByDay.findOrCreate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

		def year = Integer.parseInt(params.financialMonth_Year)
		def month = Integer.parseInt(params.financialMonth_Month)

		def fMonth = FinancialMonth.findByYearAndMonth(year, month)
		if(!fMonth) {
			fMonth = new FinancialMonth()
			fMonth.year = year
			fMonth.month = month
			fMonth.quarter = (month - 1) / 3 + 1
			fMonth.save(flush:true)
		}

		salesOrderInstance.month = fMonth

		def customerBranch = CustomerBranch.create(salesOrderInstance.customer, salesOrderInstance.branch, salesOrderInstance.timeByDay)
		salesOrderInstance.customerBranch = customerBranch

		if(!validateFinancialMonthIsClosed(salesOrderInstance)) {
			flash.message = "该账期已经关闭，不能做任何改动";
			//render(view: "create", model: [salesOrderInstance: salesOrderInstance])
			redirect(action: "list")
			return
		}

		if (!salesOrderInstance.save(flush: true)) {
			println salesOrderInstance.errors
			flash.message="销售台账创建不成功，请重新操作"
			//render(view: "create", model: [salesOrderInstance: salesOrderInstance])
			redirect(action: "list")
			return
		}


		flash.message = message(code: 'default.created.message', args: [
			message(code: 'salesOrder.label', default: 'SalesOrder'),
			salesOrderInstance.id
		])
	//	redirect(action: "show", id: salesOrderInstance.id)
		redirect(action: "list")
	}

	def show(Long id) {
		def salesOrderInstance = SalesOrder.get(id)
		if (!salesOrderInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'salesOrder.label', default: 'SalesOrder'),
				id
			])
			redirect(action: "list")
			return
		}

		[salesOrderInstance: salesOrderInstance]
	}

	def edit(Long id) {
		def salesOrderInstance = SalesOrder.get(id)
        
		println salesOrderInstance.customer
		
		if (!salesOrderInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'salesOrder.label', default: 'SalesOrder'),
				id
			])
			redirect(action: "list")
			return
		}

		if(!validateFinancialMonthIsClosed(salesOrderInstance)) {
			flash.message = "该账期已经关闭，不能做任何改动";
			//redirect(action: "show", id: salesOrderInstance.id)
			redirect(action: "list")
			return
		}

		def haveNoApproved = SalesOrder.findByUpdateFrom(salesOrderInstance.id)

		if(haveNoApproved||salesOrderInstance.status == Customer.STATUS_ADD || salesOrderInstance.status == Customer.STATUS_UPDATE ||salesOrderInstance.status == Customer.STATUS_DELETE ) {
			flash.message = "这条信息还未审批，不能操作！"
			//redirect(action: "show", id: salesOrderInstance.id)
			redirect(action: "list")
			return
		}


		[salesOrderInstance: salesOrderInstance]
	}

	/**
	 * 修改逻辑
	 admin公司
	 1.原来的无效
	 2，新增一条修改的（原来那条做备份）
	 分公司
	 1.原来的那条不修改
	 2.新增一条待审批的 （审批用）
	 * @param id
	 * @param version
	 * @return
	 */

	def update(Long id, Long version) {
		def salesOrderInstance = SalesOrder.get(id)

		def user = springSecurityService.currentUser
		def loginBranch = user.branch
		//salesOrderInstance.updateFrom = salesOrderInstance.id
		

		if (!salesOrderInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'salesOrder.label', default: 'SalesOrder'),
				id
			])
			redirect(action: "list",params:[max:params.max,offset:params.offset])
			return
		}
		if(!validateFinancialMonthIsClosed(salesOrderInstance)) {
			flash.message = "该账期已经关闭，不能做任何改动";
		//	render(view: "edit", model: [salesOrderInstance: salesOrderInstance])
			redirect(action: "list",params:[max:params.max,offset:params.offset])
			return
		}

		if (version != null) {
			if (salesOrderInstance.version > version) {
				salesOrderInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'salesOrder.label', default: 'SalesOrder')] as Object[],
						"Another user has updated this SalesOrder while you were editing")
				//render(view: "edit", model: [salesOrderInstance: salesOrderInstance])
				redirect(action: "list",params:[max:params.max,offset:params.offset])
				return
			}
		}

		/****/


		/**
		 * 如果是总公司
		 原来数据无效  isVable = false   status=able
		 新增一条新记录 isVable = true ,option = update   status=able 
		 */
		if(!loginBranch) {
			salesOrderInstance.isVail = false

			def order = new SalesOrder()
			order.properties = salesOrderInstance.properties
			order.properties = params
			order.optionValue = SalesOrder.OPTION_VALUE_UPDATE
			order.status = SalesOrder.STATUS_ABLE
			order.isVail = true
			order.id = null
			order.version = 0

			def cal = Calendar.instance
			cal.time = params.salingAt
			order.timeByDay = TimeByDay.findOrCreate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

			def year = Integer.parseInt(params.financialMonth_Year)
			def month = Integer.parseInt(params.financialMonth_Month)

			def fMonth = FinancialMonth.findByYearAndMonth(year, month)
			if(!fMonth) {
				fMonth = new FinancialMonth()
				fMonth.year = year
				fMonth.month = month
				fMonth.quarter = (month - 1) / 3 + 1
				fMonth.save(flush:true)
			}

			order.month = fMonth

			def customerBranch= CustomerBranch.create(order.customer, order.branch, order.timeByDay)
			order.customerBranch = customerBranch
			if (!salesOrderInstance.save(flush: true)) {
			//	render(view: "edit", model: [salesOrderInstance: salesOrderInstance])
				redirect(action: "list",params:[max:params.max,offset:params.offset])
				return
			}

			order.save(flush: true)

			flash.message = message(code: 'default.updated.message', args: [
				message(code: 'salesOrder.label', default: 'SalesOrder'),
				order.id
			])
			//redirect(action: "show", id: order.id)
			redirect(action: "list",params:[max:params.max,offset:params.offset])
		} else {

			//否则就是分公司登录
			//原来记录不改
			//新增一条审批记录 isVable = true option=update status = update


			//如果是审批未通过的 直接在这上面改
			if(salesOrderInstance.status == SalesOrder.STATUS_DISABLE ) {
				salesOrderInstance.properties = params
				salesOrderInstance.optionValue = SalesOrder.OPTION_VALUE_UPDATE
				salesOrderInstance.status = SalesOrder.STATUS_UPDATE
                salesOrderInstance.isVail = true

				def cal = Calendar.instance
				cal.time = params.salingAt
				salesOrderInstance.timeByDay = TimeByDay.findOrCreate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

				def year = Integer.parseInt(params.financialMonth_Year)
				def month = Integer.parseInt(params.financialMonth_Month)

				def fMonth = FinancialMonth.findByYearAndMonth(year, month)
				if(!fMonth) {
					fMonth = new FinancialMonth()
					fMonth.year = year
					fMonth.month = month
					fMonth.quarter = (month - 1) / 3 + 1
					fMonth.save(flush:true)
				}

				salesOrderInstance.month = fMonth

				def customerBranch= CustomerBranch.create(salesOrderInstance.customer, salesOrderInstance.branch, salesOrderInstance.timeByDay)
				salesOrderInstance.customerBranch = customerBranch
                //salesOrderInstance.updateFrom = id
				
                /*salesOrderInstance.salingtype = SalingType.get(params.salingtype.id)
				salesOrderInstance.customer= Customer.get(params.customer.id)
				salesOrderInstance.orderFormNo= params.orderFormNo
				salesOrderInstance.purchasingUnitPrice = params.purchasingUnitPrice
				salesOrderInstance.quantity=params.quantity
				salesOrderInstance.sales = Sales.get(params.sales.id)
				salesOrderInstance.gasType = GasType.get(params.gasType.id)*/
				
				
				
				//salesOrderInstance.updateFrom = salesOrderInstance.id
				
				
				salesOrderInstance.save(flush: true)
                  
                 
				flash.message = message(code: 'default.updated.message', args: [
					message(code: 'salesOrder.label', default: 'SalesOrder'),
					salesOrderInstance.id
				])
				//redirect(action: "show", id: salesOrderInstance.id)
				redirect(action: "list")

			} else {
				def order = new SalesOrder()
				order.properties = salesOrderInstance.properties
				order.properties = params
				order.optionValue = SalesOrder.OPTION_VALUE_UPDATE
				order.status = SalesOrder.STATUS_UPDATE
				order.isVail = true
				order.id = null
				order.version = 0

				def cal = Calendar.instance
				cal.time = params.salingAt
				order.timeByDay = TimeByDay.findOrCreate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

				def year = Integer.parseInt(params.financialMonth_Year)
				def month = Integer.parseInt(params.financialMonth_Month)

				def fMonth = FinancialMonth.findByYearAndMonth(year, month)
				if(!fMonth) {
					fMonth = new FinancialMonth()
					fMonth.year = year
					fMonth.month = month
					fMonth.quarter = (month - 1) / 3 + 1
					fMonth.save(flush:true)
				}

				order.month = fMonth

				def customerBranch= CustomerBranch.create(order.customer, order.branch, order.timeByDay)
				order.customerBranch = customerBranch
				order.updateFrom = salesOrderInstance.id
				/*if(salesOrderInstance.status == SalesOrder.STATUS_DISABLE ){
				 order.updateFrom = salesOrderInstance.id
				 //如果是审批后再修改  原来的数据只是审批前的数据  而不是最开始的数据 所以也要设置为无效
				 salesOrderInstance.isVail = false
				 salesOrderInstance.save(flush: true)
				 }*/
				order.save(flush: true)

				flash.message = message(code: 'default.updated.message', args: [
					message(code: 'salesOrder.label', default: 'SalesOrder'),
					order.id
				])
			//	redirect(action: "show", id: order.id)
				redirect(action: "list",params:[max:params.max,offset:params.offset])
			}
		}


		/****/


	}

	def delete(Long id) {
		def salesOrderInstance = SalesOrder.get(id)
		if (!salesOrderInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'salesOrder.label', default: 'SalesOrder'),
				id
			])
			redirect(action: "list")
			return
		}

		def haveNoApproved = SalesOrder.findByUpdateFrom(salesOrderInstance.id)

		if(haveNoApproved||salesOrderInstance.status == Customer.STATUS_ADD || salesOrderInstance.status == Customer.STATUS_UPDATE ||salesOrderInstance.status == Customer.STATUS_DELETE ) {
			flash.message = "这条信息还未审批，不能操作！"
		//	redirect(action: "show", id: salesOrderInstance.id)
			redirect(action: "listApprove")
			return
		}
		
		if(!validateFinancialMonthIsClosed(salesOrderInstance)) {
			flash.message = "该账期已经关闭，不能做任何改动";
			//render(view: "show", model: [salesOrderInstance: salesOrderInstance])
			redirect(action: "listApprove")
			return
		}

		try {
			//salesOrderInstance.delete(flush: true)
			//只做物理删除不做逻辑删除

			def user = springSecurityService.currentUser
			def loginBranch = user.branch

			if(loginBranch) {

				if(salesOrderInstance.status == SalesOrder.STATUS_DISABLE ) {
					salesOrderInstance.status = Customer.STATUS_DELETE
					salesOrderInstance.isVail = true
					salesOrderInstance.save(flush: true)
				}else {
					def so = new SalesOrder()
					so.properties = salesOrderInstance.properties
					so.id = null
					so.status = Customer.STATUS_DELETE
					so.version = 0
					so.isVail = true
					so.updateFrom = salesOrderInstance.id
					so.save(flush: true)
				}

			}else {
				salesOrderInstance.optionValue = SalesOrder.OPTION_VALUE_DELETE
				salesOrderInstance.isVail = false
				salesOrderInstance.save(flush: true)
			}



			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'salesOrder.label', default: 'SalesOrder'),
				id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'salesOrder.label', default: 'SalesOrder'),
				id
			])
			//redirect(action: "show", id: id)
			redirect(action: "list")
		}
	}

    
	/**
	 * 审批同意的时候，是新创建的数据还是旧数据修改
	 * @param id
	 * @return
	 */
	def saveApprove(Long id) {
		def orderAppor =  SalesOrder.get(id)

		def oldOrder
		if(orderAppor.updateFrom!=null || orderAppor.updateFrom!=""){
			//updateFrom 为空值
			def oldId = orderAppor.updateFrom
			//def oldId = orderAppor.id
			
			/*if(!oldId){
				oldId = id
			}*/
			
			def operMethod = orderAppor.status

			if(oldId) {
				oldOrder = SalesOrder.get(oldId)
			}
			if(Customer.STATUS_ADD == operMethod) {
				orderAppor.status = SalesOrder.STATUS_ABLE
				def customerBranch = CustomerBranch.create(orderAppor.customer, orderAppor.branch, orderAppor.timeByDay)
				orderAppor.customerBranch =  customerBranch
				orderAppor.save(flush:true);
			}else if (Customer.STATUS_UPDATE == operMethod) {
				orderAppor.status = SalesOrder.STATUS_ABLE
				//orderAppor.updateFrom = null
				orderAppor.isVail = true;

				def customerB = CustomerBranch.create(orderAppor.customer, orderAppor.branch, orderAppor.timeByDay)
				orderAppor.customerBranch =  customerB
				orderAppor.save(flush:true);
                 
				if(oldOrder){
         		oldOrder.isVail =  false
				oldOrder.updateFrom = null

				def customerBranch = CustomerBranch.create(oldOrder.customer, oldOrder.branch, oldOrder.timeByDay)
				oldOrder.customerBranch =  customerBranch

				oldOrder.save(flush: true)}
			}else if (Customer.STATUS_DELETE == operMethod) {
				oldOrder.status = SalesOrder.STATUS_ABLE
				oldOrder.optionValue = SalesOrder.OPTION_VALUE_DELETE
				oldOrder.isVail = false
				oldOrder.save(flush: true)

				orderAppor.delete(flush:true)
			}

		}
		flash.message="销售台账审批通过！"
		redirect(action: "listApprove" )
	}


	def findFM() {
		def res = FinancialMonth.getFinancialMonthFromSalesOrder(Integer.parseInt(params.year), Branch.get(params.branchId))
		render res
	}


	def saveDisApprove(Long id) {
		def custAppor =  SalesOrder.get(id)
		if(custAppor) {
			custAppor.status = SalesOrder.STATUS_DISABLE
			custAppor.save(flush:true)
		}
		redirect(action: "listApprove" )
	}


	def validateFinancialMonthIsClosed(SalesOrder salesOrder) {
		def result = SalesOrder.createCriteria().list () {
			eq("branch",salesOrder.branch)
			eq("month",salesOrder.month)
			eq("isClosed",true)
			eq("isVail",true)
		}

		if(result) {
			return false
		}
		return true;
	}


	def changeFMstatus(){
		//开启了的账期月份，用于关闭
		def closeMonth = params.fmOpened
		//关闭了的账期月份，用于关闭
		def openMonth  = params.fmClosed
		//分公司ID
		def branchId = params.fmBranch
		//账期年份
		def fmYear = params.fmYear
		def isClose = false;

		def actionPage = "changeFMstatusOpen";
		if(params.closeBtn) {
			isClose = true;
			actionPage = "changeFMstatusClose";
		}else {
			isClose = false;
			actionPage  = "changeFMstatusOpen";
		}


		/**
		 * 操作的数据如果有为审批的数据不让修改isClose状态
		 */
		if(!validateChangeFMstatus(closeMonth,openMonth,branchId,fmYear,isClose)) {

			flash.message ="操作的数据里面有未审批的账单，不能开启、关闭账期。。"
			redirect(action: actionPage )
			return
		}

		if(isClose) {
			//关闭账期
			salesOrderService.changeSalesOrderIsClose(closeMonth,branchId,fmYear,true)
		}else {
			//重启账期
			salesOrderService.changeSalesOrderIsClose(openMonth,branchId,fmYear,false)
		}

		flash.message ="账期修改成功！"
		redirect(action: actionPage )
	}


	private def validateChangeFMstatus(closeMonth,openMonth,branchId,fmYear,isClose) {

		def operMonth = "";
		if(isClose) {
			operMonth = closeMonth
		}else {
			operMonth = openMonth
		}


		def hql = " select so from SalesOrder as so  "
		if(fmYear) {
			hql +=" inner join so.month as m with m.year ="+fmYear

			if(operMonth) {
				hql +=" and m.month =" + operMonth;
			}
		}
		if(branchId) {
			hql += " inner join so.branch b with b.id ="+branchId
		}

		hql += " where  so.isVail = true  and so.status ='DISABLE'";

		def result = SalesOrder.executeQuery(hql);

		if(result) {

			return false;
		}

		return true;

	}

	/**
	 * 
	 * @param operMonth  操作的月份
	 * @param branchId   
	 * @param fmYear    年份
	 * @param option   true：关闭账期， false:重启账期
	 * @return
	 */
	private def changeSalesOrderIsClose(operMonth,branchId,fmYear,option) {
		def hql = " select so from SalesOrder as so  "
		if(fmYear) {
			hql +=" inner join so.month as m with m.year ="+fmYear

			if(operMonth) {
				hql +=" and m.month =" + operMonth;
			}
		}
		if(branchId) {
			hql += " inner join so.branch b with b.id ="+branchId
		}

		hql += " where  so.isVail = true ";

		def result = SalesOrder.executeQuery(hql);

		result.each {
			it.isClosed =option
			it.save(true);
		}

		/*def result= SalesOrder.createCriteria().list(){
		 if(branchId) {
		 branch {
		 eq("id",Long.parseLong(branchId))
		 }
		 }
		 if(fmYear) {
		 month {
		 eq("year",Integer.parseInt(year))
		 }
		 }
		 if(operMonth) {
		 month {
		 eq("month",month)
		 }
		 }
		 }*/


	}


	def changeFMstatusOpen() {

	}

	def changeFMstatusClose() {

	}


	def listNewCustomerCount() {
		def sdf = new SimpleDateFormat("yyyy-MM-dd")
		def timeFrom
		def timeTo

		if(params.timeFrom) {
			try{
				timeFrom = sdf.parse(params.timeFrom)
			}catch(e){
			}
		}
		if(params.timeTo) {
			try{
				timeTo = sdf.parse(params.timeTo)
			}catch(e){
			}
		}

		def isMonth = params.isMonth;
		def isBranch = params.isBranch;
		def branchId = params.branchId;

		def monthIndex ;
		def branchIndex ;
		if(isMonth&&isBranch) {
			monthIndex =2;
			branchIndex =3;
		}else if(isMonth){
			monthIndex =2;
		}else if(isBranch) {
			branchIndex =2;
		}

		def user = springSecurityService.currentUser
		def loginBranch = user.branch

		def branch
		if(branchId&&isBranch) {
			branch = Branch.get(Long.parseLong(branchId));
		}
		if(loginBranch){
			branch = loginBranch
		}


		def cusore = {
			createAlias('date','date')

			if(branch) {
				eq("branch",branch)
			}
			if(timeFrom) {
				ge('date.date', timeFrom)
			}
			if(timeTo) {
				le('date.date', timeTo)
			}

			projections {

				groupProperty("date.year")
				count("date.id")
				if(isMonth) {
					groupProperty("date.month")
				}

				if(isBranch) {
					groupProperty("branch")
				}
			}


		}

		def resultData = CustomerBranch.createCriteria().list(max:10, offset:params.offset,cusore);
		def total = CustomerBranch.createCriteria().list(cusore)
		def result = new ArrayList();
		resultData.each {
			def map = new HashMap();

			map["year"] =it[0];
			map["count"] = it[1];

			if(isMonth) {
				map["month"] = it[monthIndex];
			}
			if(isBranch) {
				map["branch"] = it[branchIndex].name;
			}else {
				map["branch"] = branch ==null?'全公司':branch.name;
			}



			result.add(map)
		}



		println result
		[reesultList: result, resultCount: total.size()]

	}
	
	
	def findSales() {
		
		def user = springSecurityService.currentUser
		def loginBranch = user.branch
		
		List list = new ArrayList()
		def branchId = params.branchId;
		if(loginBranch) {
			branchId = loginBranch.id
		}
		
		if(branchId) {
			def rea =Sales.executeQuery('select distinct s.id, s.name from Sales as s inner join s.branch b with b.id ='+branchId);
			println "rea" +rea;
			
			rea.each {
				def map = new HashMap();
				map.put("id", it[0]);
				map.put("name", it[1]);
				list.add(map);
			 }
			
		}
		render list as JSON
	}
}