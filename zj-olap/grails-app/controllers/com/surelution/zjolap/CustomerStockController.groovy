package com.surelution.zjolap

import java.text.SimpleDateFormat

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.dao.DataIntegrityViolationException

class CustomerStockController {

	static allowedMethods = [save: "POST"]


	def customerStockService

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {


		if(params["downExcelFile"]) {
			downExcelFile()
		}

		params.max = Math.min(max ?: 10, 100)


		def sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm")
		def branch,dateBegin,dateEnd

		if(params['branchId']) {
			branch = Branch.get(params['branchId'])
		}

		if(getLogBranch()) {
			branch = getLogBranch()
		}

		if(params.dateBegin) {
			try{
				dateBegin = sdf.parse(params.dateBegin)
			}catch(e){
			}
		}
		if(params.dateEnd) {
			try{
				dateEnd = sdf.parse(params.dateEnd)
			}catch(e){
			}
		}

		def sortBy = params['sort']
		def result = CustomerStock.createCriteria().list(max:10, offset:params.offset) {
			if(branch) {
				customerBranch { eq('branch', branch) }
			}
			if(dateBegin) {
				ge('cdate', dateBegin)
			}
			if(dateEnd) {
				le('cdate', dateEnd)
			}
			eq('isVail',true)
			eq('status',"ABLE")
			
			if(sortBy) {
				if(sortBy == 'branch') {
					createAlias('customerBranch', 'cb')
					order('cb.branch', params['order']?params['order']:"desc")
				} else if(sortBy == 'customer') {
					createAlias('customerBranch', 'cb')
					order('cb.customer', params['order']?params['order']:"desc")
				} else if(sortBy == 'customerType') {
					createAlias('customerBranch', 'cb')
					createAlias('cb.customer', 'c')
					createAlias('c.customerType', 't3')
					createAlias('t3.level2', 't2')
					order('t2.level1', params['order']?params['order']:"desc")
				} else if(sortBy == "category") {
					createAlias('gasType', 'g')
					order('g.category', params['order']?params['order']:"desc")
				} else {
					order(sortBy, params['order']?params['order']:"desc")
				}
			}
		}

		[branchStr:getNotUploadBranch(),customerStockInstanceList: result, customerStockInstanceTotal: result.totalCount]
	}

	def create() {
		[customerStockInstance: new CustomerStock(params)]
	}



	private def getCustomerBranch() {
		def branch
		def customer


		//如果是分公司登录直接去分公司信息。
		if(getLogBranch()) {
			branch = getLogBranch()
		}else {
			branch = Branch.get(params.branchId)
		}

		customer = Customer.get(params.customerId)


		return CustomerBranch.findOrcreate(customer, branch);


	}


	private def validateSave(date,branch) {
		def msg = "";
		//账期有关闭不能新增
		if(!validateDate(date, branch)) {
			msg = "该账期已经关闭，不能编辑数据";
		}

		return msg;
	}


	private def validateStatus(CustomerStock stock) {
		def msg = "";
		def haveNoApproved = CustomerStock.findByUpdateFrom(stock.id)

		println "haveNoApproved" +haveNoApproved
		println "stockId" + stock.id
		println "status" + stock.status

		if(haveNoApproved||stock.status == CustomerStock.STATUS_ADD || stock.status == CustomerStock.STATUS_UPDATE ||stock.status == CustomerStock.STATUS_DELETE ) {
			msg = "这条信息还未审批，不能操作！";
		}

		return msg
	}


	private def getTimeByDay(date) {
		def cal = Calendar.instance;
		cal.setTime(date);
		return TimeByDay.findOrCreate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) , cal.get(Calendar.DAY_OF_MONTH))
	}


	private def getStatusForSave() {
		def status = "";
		if(getLogBranch()) {
			status = CustomerStock.STATUS_ADD
		}else {
			status = CustomerStock.STATUS_ABLE
		}
		return status;
	}



	private def getStatusForUpdate() {
		def status = "";
		if(getLogBranch()) {
			status = CustomerStock.STATUS_UPDATE
		}else {
			status = CustomerStock.STATUS_ABLE
		}
		return status;
	}

	def save() {

		/*
		 * 界面应该提供的元素
		 * 1。分公司(省公司可见)
		 * 2.客户（如果是分公司登录只显示该该分公司客户）
		 * 3.时间
		 * 4.gasType
		 * 5.stockQty
		 * 
		 */

		def customerStockInstance = new CustomerStock(params)

		def customerBranch = getCustomerBranch()
		def stockQty = customerStockInstance.stockQty
		def date = getTimeByDay(customerStockInstance?.cdate);
		def gasType = customerStockInstance.gasType
		def status = getStatusForSave();
		def isVail = true;




		//账期有关闭不能新增
		def msg = validateSave(date?.date,customerBranch?.branch);
		if(msg) {
			flash.message = msg;
			//render(view: "create", model: [customerStockInstance: customerStockInstance])
			redirect(action:'list')
			return
		}

		customerStockInstance.customerBranch = customerBranch;
		customerStockInstance.stockQty = stockQty;
		customerStockInstance.date = date;
		customerStockInstance.cdate = date?.date;
		customerStockInstance.gasType = gasType;
		customerStockInstance.status = status;
		customerStockInstance.isVail = isVail;




		//如果是分公司登录，插CustomerStock status = 'DISABLE',isVail = true

		def customerStockReult
		if(getLogBranch()) {
			customerStockReult = customerStockInstance.save(flush:true)
		}else {
			//如果是省公司登录，1.插入CustomerStock status = 'ABLE',isVail = true 2.插入CustomerStockLog表
			customerStockReult = customerStockInstance.save(flush:true)

			if(customerStockReult) {
				insertCustomerBranchLog(customerStockInstance,CustomerStockLog.OPTION_VALUE_ADD)
			}

		}




		if (!customerStockReult) {
			//render(view: "create", model: [customerStockInstance: customerStockInstance])
			redirect(action:'list')
			return
		}

		flash.message = message(code: 'default.created.message', args: [
			message(code: 'customerStock.label', default: 'CustomerStock'),
			customerStockInstance.id
		])
		//redirect(action: "show", id: customerStockInstance.id)
		redirect(action:'list')
	}

	def insertCustomerBranchLog(CustomerStock customerStockInstance,String optionType) {
		CustomerStockLog csLog = new CustomerStockLog()

		csLog.customerBranch = customerStockInstance.customerBranch;
		csLog.stockQty = customerStockInstance.stockQty;
		csLog.date = customerStockInstance.date;
		csLog.gasType = customerStockInstance.gasType;
		csLog.status = customerStockInstance.status;
		csLog.updateFrom = customerStockInstance;
		csLog.optionType = optionType;

		csLog.save(flush:true);
	}
	def show(Long id) {
		def customerStockInstance = CustomerStock.get(id)
		if (!customerStockInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'customerStock.label', default: 'CustomerStock'),
				id
			])
			redirect(action: "list")
			return
		}

		[customerStockInstance: customerStockInstance]
	}

	def edit(Long id) {
		def customerStockInstance = CustomerStock.get(id)
		if (!customerStockInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'customerStock.label', default: 'CustomerStock'),
				id
			])
			redirect(action: "list")
			return
		}

		//有审批记录的数据不让修改
		def msg = validateStatus(customerStockInstance)
		if(msg) {
			flash.message = msg
			//redirect(action: "show", id: customerStockInstance.id)
			//redirect(action:'list')
			redirect(action:'listApprove')
			return
		}

		[customerStockInstance: customerStockInstance]
	}

	def update(Long id, Long version) {
		def customerStockInstance = CustomerStock.get(id)

		customerStockInstance.discard()
		if (!customerStockInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'customerStock.label', default: 'CustomerStock'),
				id
			])
			redirect(action: "list",params:[max:params.max,offset:params.offset])
			return
		}

		if (version != null) {
			if (customerStockInstance.version > version) {
				customerStockInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'customerStock.label', default: 'CustomerStock')] as Object[],
						"Another user has updated this CustomerStock while you were editing")
			//	render(view: "edit", model: [customerStockInstance: customerStockInstance])
				redirect(action:'list',params:[max:params.max,offset:params.offset])
				return
			}
		}

		//有审批记录的数据不让修改
		def msg = validateStatus(customerStockInstance)


		if(msg) {
			flash.message = msg
		//	redirect(action: "show", id: customerStockInstance.id)
			redirect(action:'list',params:[max:params.max,offset:params.offset])
			return
		}



		customerStockInstance.properties = params;

		String oldStatus = customerStockInstance.status

		def customerBranch = getCustomerBranch()
		def stockQty = customerStockInstance.stockQty
		def date = getTimeByDay(customerStockInstance?.cdate);
		def gasType = customerStockInstance.gasType
		def status = getStatusForUpdate();
		def isVail = true;



		customerStockInstance.customerBranch = customerBranch;
		customerStockInstance.stockQty = stockQty;
		customerStockInstance.date = date;
		customerStockInstance.cdate = date?.date;
		customerStockInstance.gasType = gasType;
		customerStockInstance.status = status;
		customerStockInstance.isVail = isVail;


		//主要验证账期
		msg = validateSave(date?.date,customerBranch?.branch);

		println "msg" +msg
		if(msg) {
			flash.message = msg;
		//	render(view: "edit", model: [customerStockInstance: customerStockInstance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
			return
		}




		//如果是分公司登录
		//1.原来数据不
		//2.新增一条状态为Disable的数据

		def customerStockResult
		if(getLogBranch()) {
			//注意：如果状态本来就是disable直接在上面修改
			if(oldStatus == CustomerStock.STATUS_DISABLE)  {
				customerStockResult = customerStockInstance.save(flush: true)
			} else {
				def disableCustomerStock = new  CustomerStock();
				disableCustomerStock.customerBranch = customerStockInstance.customerBranch;
				disableCustomerStock.stockQty = customerStockInstance.stockQty;
				disableCustomerStock.date = customerStockInstance.date ;
				disableCustomerStock.cdate = customerStockInstance.cdate ;
				disableCustomerStock.gasType = customerStockInstance.gasType ;
				disableCustomerStock.status = CustomerStock.STATUS_UPDATE;
				disableCustomerStock.isVail = customerStockInstance.isVail ;

				println "customerStockInstance.id" +customerStockInstance.id
				disableCustomerStock.updateFrom = customerStockInstance.id;

				customerStockResult = disableCustomerStock.save(flush:true);

				//只为后面统一
				customerStockInstance = disableCustomerStock
			}
		}else {
			//如果是省公司登录
			//1直接修改数据
			//2.记录日志表
			customerStockResult = customerStockInstance.save(flush: true)
			insertCustomerBranchLog(customerStockInstance, CustomerStockLog.OPTION_VALUE_UPDATE)

		}




		if (!customerStockResult) {
			//render(view: "edit", model: [customerStockInstance: customerStockInstance])
			redirect(action:'list',params:[max:params.max,offset:params.offset])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'customerStock.label', default: 'CustomerStock'),
			customerStockInstance.id
		])
		//redirect(action: "show", id: customerStockInstance.id)
		redirect(action:'list',params:[max:params.max,offset:params.offset])
	}

	def delete(Long id) {

		def customerStockInstance = CustomerStock.get(id)
		if (!customerStockInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'customerStock.label', default: 'CustomerStock'),
				id
			])
			redirect(action: "list")
			return
		}


		//有审批记录的数据不让修改
		def msg = validateStatus(customerStockInstance)
		if(msg) {
			flash.message = msg
			//redirect(action: "show", id: customerStockInstance.id)
			redirect(action:'listApprove')
			return
		}

		//账期有关闭不能新增
		msg = validateSave(customerStockInstance?.date?.date,customerStockInstance?.customerBranch?.branch);
		if(msg) {
			flash.message = msg;
		//	render(view: "show", model: [customerStockInstance: customerStockInstance])
			redirect(action:'listApprove')
			return
		}



		//删除
		//如果是分公司，1.原来数据不变，2.新增一条CustomerStock 状态为DELETE isVail = true

		//如果是省公司   1。原来数据状态改成isVail = false,2新增CustomerStockLog表

		if(getLogBranch()) {
			//如果状态本来就是Disable 就直接改变状态为delete

			if(customerStockInstance.status == CustomerStock.STATUS_DISABLE) {
				customerStockInstance.status = CustomerStock.STATUS_DELETE;
				customerStockInstance.save(flush:true);
			} else {

				def disableCustomerStock = new  CustomerStock();
				disableCustomerStock.customerBranch = customerStockInstance.customerBranch;
				disableCustomerStock.stockQty = customerStockInstance.stockQty;
				disableCustomerStock.date = customerStockInstance.date ;
				disableCustomerStock.cdate = customerStockInstance.cdate ;
				disableCustomerStock.gasType = customerStockInstance.gasType ;
				disableCustomerStock.status = CustomerStock.STATUS_DELETE;
				disableCustomerStock.isVail = customerStockInstance.isVail ;
				disableCustomerStock.updateFrom = customerStockInstance.id;
				disableCustomerStock.save(flush:true);
			}


		}else {
			customerStockInstance.isVail = false;
			customerStockInstance.save(flush:true);
			insertCustomerBranchLog(customerStockInstance, CustomerStockLog.OPTION_VALUE_DELETE);
		}


        flash.message="提油记录已经删除"

		redirect(action: "list")
	}
	def springSecurityService


	private def getLogBranch() {
		def user = springSecurityService.currentUser
		def loginBranch = user?.branch
		return loginBranch
	}


	def initUploadExcel() {

	}

	def uploadExcel() {



		def cal = Calendar.instance

		def excelMessages = []

		def filePath = grailsApplication.config.excel.importer.file.location
		def destPath = "${filePath}${UUID.randomUUID().toString()}"
		println destPath

		def excelFile = request.getFile('excelFile')

		excelFile.transferTo(new File(destPath))

		def excelUploadInstance = new StockExcelUpload()
		excelUploadInstance.deleted = false
		excelUploadInstance.originalFileName = excelFile.originalFilename
		excelUploadInstance.filePath = destPath
		excelUploadInstance.uploadedAt = new Date()
		excelUploadInstance.user = springSecurityService.currentUser



		def fis = new FileInputStream(destPath)
		def workbook = null;
		try {
			workbook= new HSSFWorkbook(fis)
		}catch(Exception e) {
			flash.message = "请确认Xls文件存在，且为office excel 2003的格式！";
			render(view: "initUploadExcel")
			return

		}

		def sheet = workbook.getSheetAt(0)
		def firstRowNum = sheet.firstRowNum
		def lastRowNum = sheet.lastRowNum
		def predefinedFirstRowNum = 6


		def customerstocks = new ArrayList<CustomerStock>();

		//(predefinedFirstRowNum .. lastRowNum ).each {num ->
		try{
			for(int num = predefinedFirstRowNum; num <= lastRowNum; num++){

				//
				HSSFRow row = sheet.getRow(num);

				//验证客户，分公司，gasType是否存在,如果是分公司登录只能录入分公司信息
				//TODO
				validateBefore(row,excelMessages)


				def customerStock = new CustomerStock();


				CustomerBranch customerBranch = initCustomBranch(row)
				double stockQty = initStockQty(row)
				TimeByDay date = initTimeByDay(row)

				GasType gasType = initGasType(row)


				String status	= 'ABLE'
				boolean isVal  = true


				customerStock.customerBranch = customerBranch;
				customerStock.stockQty = stockQty;
				customerStock.date = date;
				customerStock.cdate = date?.date;
				customerStock.gasType = gasType;
				customerStock.status = status;
				customerStock.isVail = isVal;
				customerStock.excelRowIndex = num;



				customerstocks.add(customerStock);

				//1.插入 CustomerStock

				//2.插入CustomerStockLog

			}
		}catch(Exception ex) {
			flash.message = "请确认上传的模版正确，上传的必须为提货模版";
			render(view: "initUploadExcel")
			return


		}
		if (!excelMessages.isEmpty() ) {
			render(view: "initUploadExcel", model: [excelMessages:excelMessages])
			return
		}


		customerStockService.serviceUploadCustomerStocks(customerstocks, excelUploadInstance);


		flash.message = "数据上传成功";
		redirect(action: "initUploadExcel")

	}

	private def initGasType(HSSFRow row) {
		def gasTypeSName = getGasTypeName(row)
		def gasType = GasType.findByName(gasTypeSName)
		return gasType
	}

	private def initTimeByDay(HSSFRow row) {
		HSSFCell cellDate= row.getCell(getCellPosition('L'));
		cellDate.setCellType(HSSFCell.CELL_TYPE_STRING);
		def dateStr = cellDate.getStringCellValue()?.trim();
		def sdf = new SimpleDateFormat('yyyy.MM.dd')
		def date = sdf.parse(dateStr);
		def cal = Calendar.instance;
		cal.setTime(date);
		return TimeByDay.findOrCreate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) , cal.get(Calendar.DAY_OF_MONTH))
	}

	private def initStockQty(HSSFRow row) {
		HSSFCell cellQty= row.getCell(getCellPosition('P'));
		cellQty.setCellType(HSSFCell.CELL_TYPE_STRING);
		def qtyStr = cellQty.getStringCellValue()?.trim();
		double qty = 0;
		if(qtyStr&&qtyStr.trim() != "") {
			qty = Double.parseDouble(qtyStr);
		}

		return qty;
	}

	private def initCustomBranch(HSSFRow row) {

		//HSSFCell cellBranch= row.getCell(getCellPosition('A'));
		//String branchName = cellBranch.stringCellValue;
		//branchName= branchName.substring(0, 2);
		def branchName = getBranchName(row);

		def branch = Branch.findByName(branchName);

		if(!branch) {
			branch = new Branch()
			branch.name = branchName;
			branch.save(flush:true);
		}


		HSSFCell cellCustomer= row.getCell(getCellPosition('M'));
		String customerName = cellCustomer.getStringCellValue()?.trim();
		def customer = Customer.findByNameAndStatus(customerName,"ABLE")

		def type3 = CustomerTypeLevel3.findByName("未指定");

		if(!customer) {
			customer = new 	Customer()
			customer.name = customerName
			customer.customerType = type3;
			customer.status = "ABLE";
			customer.save(flush:true);
		}


		def customerBranch =  CustomerBranch.findByCustomerAndBranch(customer,branch);
		if(!customerBranch) {
			customerBranch = CustomerBranch.create(customer, branch, null);
		}


		return customerBranch;
	}


	private String[] branchs;

	private String getBranchName(HSSFRow row) {
		if(!branchs) {
			def allBranchs = Branch.list();
			branchs = new String[allBranchs.size()];
			for(int i=0 ; i<allBranchs.size() ; i++) {
				branchs[i] = allBranchs.get(i).name
			}
		}


		if(row) {
			HSSFCell cell =  row.getCell(getCellPosition("Q"))
			cell?.setCellType(HSSFCell.CELL_TYPE_STRING);
			def cellStr= cell?.getStringCellValue()?.trim();

			if(cellStr&&cellStr.contains('浙江销售分公司')) {
				return "本部";
			}

			if(cellStr&&cellStr.contains('海华')) {
				return "杭州";
			}

			for(int i=0;i<branchs.length;i++) {

				if(cellStr&&cellStr.contains(branchs[i])) {
					return branchs[i]
				}

			}

		}

		return "";

	}

	private def validateDate(Date vdate,Branch branch) {
		def calendar = Calendar.instance
		calendar.setTime(vdate);

		def year = calendar.get(calendar.YEAR)
		def month = calendar.get(calendar.MONTH)+1
		//	def minDate = 1
		//	def maxDate = calendar.getActualMaximum(Calendar.DATE)


		/*def result= CustomerStock.createCriteria().list {
		 date{
		 eq('year',year)
		 eq('month',month)
		 between('day',minDate,maxDate)
		 }
		 customerBranch { eq('branch',branch) }
		 eq('isClosed',true)
		 eq('isVail',true)
		 }
		 */

		def hql = " select so from CustomerStock as so  "
		hql +=" inner join so.date as m with m.year ="+year
		hql +=" and m.month =" + month;
		hql += " inner join so.customerBranch as cb inner join  cb.branch as b with b.id ="+branch.id;
		hql += " where  so.isVail = true  and so.isClosed = true";

		def result = CustomerStock.executeQuery(hql);

		//如果查询到了，表示这个月账期已经关闭
		if(result) {
			return false
		}
		return true

	}

	private def validateBefore(HSSFRow row,excelMessages ) {

		if(!row) {
			return;
		}

		def num = row.getRowNum();
		//分公司，验证客户，gasType是否存在,如果是分公司登录只能录入分公司信息、账期验证  、



		//1分公司（a.分公司是否存在，b.如果是分公司登录，只能录入本公司的）
		def branchName  = getBranchName(row)
		def branch= Branch.findByName(branchName)
		if(!branch) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'Q', message:'分公司名称不存在,不能提交数据'))
		}


		//只能录入本公司的
		Branch lgBranch= getLogBranch();
		if(lgBranch&&branch) {
			if(lgBranch.name != branch.name) {
				excelMessages.add(new ExcelInformationVo(row:num, column:'Q', message:'只能录入本公司的数据,其他公司的数据不能提交'))
			}
		}


		//2验证客 客户是否存在
		HSSFCell cellCustomr= row.getCell(getCellPosition("M"))
		cellCustomr?.setCellType(HSSFCell.CELL_TYPE_STRING);
		def customerStr = cellCustomr?.getStringCellValue()?.trim()
		def customer= Customer.findByName(customerStr)
		if(!customer) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'M', message:'客户名称不存在,不能提交数据'))
		}

		//3gasType是否存在
		def gasTypeSName = getGasTypeName(row)
		def gasType = GasType.findByName(gasTypeSName)
		if(!gasType) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'F', message:'该油品本号不存在,不能提交数据'))
		}

		//4账期验证
		def sdf = new SimpleDateFormat('yyyy.MM.dd')
		HSSFCell cellDate= row.getCell(getCellPosition("L"))
		cellDate?.setCellType(HSSFCell.CELL_TYPE_STRING);
		def dateStr = cellDate.getStringCellValue()?.trim()
		def date = sdf.parse(dateStr)
		if(!validateDate(date,branch)) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'L', message:'此账期已经关闭，不能提交数据'))
		}

		//5数量验证
		try {
			HSSFCell cellQty= row.getCell(getCellPosition('P'));
			cellQty.setCellType(HSSFCell.CELL_TYPE_STRING);
			def qtyStr = cellQty.getStringCellValue()?.trim();
			double qty = 0;
			if(qtyStr&&qtyStr.trim() != "") {
				qty = Double.parseDouble(qtyStr);
			}
		}catch(Exception ex) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'P', message:'数量必须为一个数字，小数点必须是一个英文的点号'))
		}
	}


	private def getGasTypeName(HSSFRow row) {
		HSSFCell gasTypeCell= row.getCell(getCellPosition('F'));
		gasTypeCell?.setCellType(HSSFCell.CELL_TYPE_STRING);
		def gasTypeStr = gasTypeCell.getStringCellValue()?.trim();

		//1.如果包含‘号’   0号 普通柴油  0号 车用柴油(Ⅲ) 直接去 ‘号’前面的字符
		if(gasTypeStr&&gasTypeStr.contains("号")) {
			gasTypeStr = gasTypeStr.substring(0,gasTypeStr.indexOf("号"));

			return gasTypeStr
		}

		//2.如果包含煤油，那么就是煤油
		if(gasTypeStr&&gasTypeStr.contains("煤油")) {
			return "煤油";
		}

		//3。其他的直接返回

		return gasTypeStr;
	}

	private int getCellPosition(String p) {
		def c = p as char
		def start = 'A' as char
		return (int)(c - start)
	}


	def saveDisApprove(Long id) {
		def customerStock =CustomerStock.get(id);
		if(customerStock) {
			customerStock.status = CustomerStock.STATUS_DISABLE
			customerStock.save(flush:true)
		}
		redirect(action: "listApprove" )
	}


	def listApprove(Integer max) {
		params.max = Math.min(max ?: 10, 100)


		def sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm")
		def branch,dateBegin,dateEnd

		if(params['branchId']) {
			branch = Branch.get(params['branchId'])
		}

		if(getLogBranch()) {
			branch = getLogBranch()
		}

		if(params.dateBegin) {
			try{
				dateBegin = sdf.parse(params.dateBegin)
			}catch(e){
			}
		}
		if(params.dateEnd) {
			try{
				dateEnd = sdf.parse(params.dateEnd)
			}catch(e){
			}
		}



		def result = CustomerStock.createCriteria().list(max:10, offset:params.offset) {

			if(branch) {
				customerBranch { eq('branch', branch) }
			}
			if(dateBegin) {
				ge('cdate', dateBegin)
			}
			if(dateEnd) {
				le('cdate', dateEnd)
			}
			eq('isVail',true)
			ne('status',"ABLE")
		}

		[customerStockInstanceList: result, customerStockInstanceTotal: result.totalCount]
	}

	def saveApprove(Long id) {
		def customerStock =CustomerStock.get(id);

		if(customerStock) {
			//如果事故新增审批
			if(customerStock.status == CustomerStock.STATUS_ADD) {
				approveAdd(customerStock)
			}else if(customerStock.status == CustomerStock.STATUS_UPDATE)  {
				approveUpdate(customerStock);

			}else if(customerStock.status == CustomerStock.STATUS_DELETE) {
				//删除审批
				approveDelete(customerStock);
			}

			redirect(action: "listApprove" )
		}
	}

	private def approveDelete(CustomerStock customerStock) {

		def oldId= customerStock.updateFrom
		CustomerStock customerStockOld =  CustomerStock.get(oldId);

		customerStockOld.status = CustomerStock.STATUS_ABLE
		customerStockOld.isVail = false
		customerStockOld.save(flush: true)

		customerStock.delete(flush:true)


		insertCustomerBranchLog(customerStockOld, CustomerStockLog.OPTION_VALUE_DELETE);
	}


	private def approveUpdate(CustomerStock customerStock) {
		/* //让新的可见，老的那一条不可见
		 def oldId= customerStock.updateFrom
		 CustomerStock customerStockOld =  CustomerStock.get(oldId);
		 customerStock.status = CustomerStock.STATUS_ABLE;
		 customerStock.updateFrom = null;
		 customerStockOld.isVail = false;
		 */

		//新的一条不可见，老的可见
		def oldId= customerStock.updateFrom

		//如果oldId存在 为修改
		if(oldId) {
			CustomerStock customerStockOld =  CustomerStock.get(oldId);




			customerStockOld.customerBranch = customerStock.customerBranch;
			customerStockOld.stockQty = customerStock.stockQty;
			customerStockOld.date = customerStock.date ;
			customerStockOld.cdate = customerStock.cdate ;
			customerStockOld.gasType = customerStock.gasType ;
			customerStockOld.status = CustomerStock.STATUS_ABLE;
			customerStockOld.isVail = true


			//customerStock.updateFrom = null;
			//customerStock.isVail = false;

			customerStock.delete(flush:true);
			customerStockOld.save(flush:true);



			insertCustomerBranchLog(customerStockOld, CustomerStockLog.OPTION_VALUE_UPDATE);
		}else {
			//如果oldId不存在，为新增驳回再修改，此时没有原始ID
			//应该走新增审批流程
			approveAdd(customerStock);

		}
	}

	private def approveAdd(CustomerStock customerStock) {
		//设置状态为有效
		customerStock.status = CustomerStock.STATUS_ABLE
		customerStock.save(flush:true);

		//记录日志表
		insertCustomerBranchLog(customerStock, CustomerStockLog.OPTION_VALUE_ADD);

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
			customerStockService.changeCustomerStockIsClose(closeMonth,branchId,fmYear,true)
		}else {
			//重启账期
			customerStockService.changeCustomerStockIsClose(openMonth,branchId,fmYear,false)
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


		def hql = " select so from CustomerStock as so  "
		if(fmYear) {
			hql +=" inner join so.date as m with m.year ="+fmYear

			if(operMonth) {
				hql +=" and m.month =" + operMonth;
			}
		}
		if(branchId) {
			hql += " inner join so.customerBranch as cb inner join  cb.branch as b with b.id ="+branchId
		}

		hql += " where  so.isVail = true  and so.status ='DISABLE'";

		def result = CustomerStock.executeQuery(hql);

		if(result) {

			return false;
		}

		return true;

	}


	private def changeCustomerStockIsClose(operMonth,branchId,fmYear,option) {
		def hql = " select so from CustomerStock as so  "
		if(fmYear) {
			hql +=" inner join so.date as m with m.year ="+fmYear

			if(operMonth) {
				hql +=" and m.month =" + operMonth;
			}
		}
		if(branchId) {
			hql += " inner join so.customerBranch as cb inner join  cb.branch as b with b.id ="+branchId
		}

		hql += " where  so.isVail = true ";

		def result = CustomerStock.executeQuery(hql);

		result.each {
			it.isClosed =option
			//it.save(true);
		}
		CustomerStock.saveAll(result)

	}


	def findFM() {
		def res = TimeByDay.getMonthCustomerStock(Integer.parseInt(params.year), Branch.get(params.branchId))
		render res
	}


	def changeFMstatusOpen() {

	}

	def changeFMstatusClose() {

	}


	def downExcelFile() {

		def sdf = new SimpleDateFormat("yyyy-MM-dd")
		def branch,dateBegin,dateEnd

		if(params['branchId']) {
			branch = Branch.get(params['branchId'])
		}

		if(getLogBranch()) {
			branch = getLogBranch()
		}

		if(params.dateBegin) {
			try{
				dateBegin = sdf.parse(params.dateBegin)
			}catch(e){
			}
		}
		if(params.dateEnd) {
			try{
				dateEnd = sdf.parse(params.dateEnd)
			}catch(e){
			}
		}



		def result = CustomerStock.createCriteria().list() {

			if(branch) {
				customerBranch { eq('branch', branch) }
			}
			if(dateBegin) {
				ge('cdate', dateBegin)
			}
			if(dateEnd) {
				le('cdate', dateEnd)
			}
			eq('isVail',true)
			eq('status',"ABLE")
		}

		downExcel(result)
	}

	private void downExcel(stocks) {


		def head =[
			"分公司",
			"客户",
			"客户性质",
			"机构用户分类",
			"工业分类",
			"提油时间",
			"油品类型",
			"油品品号",
			"提油量"
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

		stocks.each { customerStockInstance->
			def rowbody= sheet.createRow(idx);
			//"分公司"
			createCell(0,getStringValue(customerStockInstance?.customerBranch?.branch?.name),rowbody);
			//"客户"
			createCell(1,getStringValue(customerStockInstance?.customerBranch?.customer?.name),rowbody);
			//"客户性质"
			createCell(2,getStringValue(customerStockInstance?.customerBranch?.customer?.customerType?.level2?.level1?.name),rowbody);
			//"机构用户分类"
			createCell(3,getStringValue(customerStockInstance?.customerBranch?.customer?.customerType?.level2?.displayName),rowbody);
			//"工业分类"
			createCell(4,getStringValue(customerStockInstance?.customerBranch?.customer?.customerType?.displayName),rowbody);
			//"提油时间"
			createCell(5,getDateStringValue(customerStockInstance?.date?.date),rowbody);
			//"油品类型"
			createCell(6,getStringValue(customerStockInstance?.gasType?.category?.name),rowbody);
			//"油品品号"
			createCell(7,getStringValue(customerStockInstance?.gasType.name),rowbody);
			//"提油量"
			createCell(8,getStringValue(customerStockInstance?.stockQty),rowbody);

			idx++;
		}





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


	private String  getNotUploadBranch() {
		String result = "昨天没有提交提货数据的分公司 ：STR";

		def str = "";

		def branchs= Branch.list();


		branchs.each {
			if(noUploadData(it)) {
				str+=it.name+","
			}
		}

		result = result.replace("STR", str)
		result = result.substring(0, result.length()-1);
		result = "<marquee  onMouseOut='this.start()' onMouseOver='this.stop()' ><font size=4 color=#ffffff>"+ result +"</font></marquee>"
		if(!str) {
			result="";
		}
		return result;

	}


	private boolean noUploadData(Branch branch) {

		def cal = Calendar.instance;

		cal.set(Calendar.DATE, -1)
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);

		//def date  = new Date();

		println "cal.time" + cal.time

		def result = CustomerStock.createCriteria().list(max:2, offset:params.offset) {
			if(branch) {
				customerBranch { eq('branch', branch) }
			}
			gt("cdate", cal.time)
			eq('isVail',true)
			eq('status',"ABLE")
		}


		if(result) {
			return false
		}

		return true
	}

}
