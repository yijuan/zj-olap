package com.surelution.zjolap

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.dao.DataIntegrityViolationException

class InitCustomerStockController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[initCustomerStockInstanceList: InitCustomerStock.list(params), initCustomerStockInstanceTotal: InitCustomerStock.count()]
	}

	def create() {
		[initCustomerStockInstance: new InitCustomerStock(params)]
	}

	def save() {
		def initCustomerStockInstance = new InitCustomerStock(params)
		if (!initCustomerStockInstance.save(flush: true)) {
			render(view: "create", model: [initCustomerStockInstance: initCustomerStockInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [
			message(code: 'initCustomerStock.label', default: 'InitCustomerStock'),
			initCustomerStockInstance.id
		])
		redirect(action: "show", id: initCustomerStockInstance.id)
	}

	def show(Long id) {
		def initCustomerStockInstance = InitCustomerStock.get(id)
		if (!initCustomerStockInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'initCustomerStock.label', default: 'InitCustomerStock'),
				id
			])
			redirect(action: "list")
			return
		}

		[initCustomerStockInstance: initCustomerStockInstance]
	}

	def edit(Long id) {
		def initCustomerStockInstance = InitCustomerStock.get(id)
		if (!initCustomerStockInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'initCustomerStock.label', default: 'InitCustomerStock'),
				id
			])
			redirect(action: "list")
			return
		}

		[initCustomerStockInstance: initCustomerStockInstance]
	}

	def update(Long id, Long version) {
		def initCustomerStockInstance = InitCustomerStock.get(id)
		if (!initCustomerStockInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'initCustomerStock.label', default: 'InitCustomerStock'),
				id
			])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (initCustomerStockInstance.version > version) {
				initCustomerStockInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'initCustomerStock.label', default: 'InitCustomerStock')] as Object[],
						"Another user has updated this InitCustomerStock while you were editing")
				render(view: "edit", model: [initCustomerStockInstance: initCustomerStockInstance])
				return
			}
		}

		initCustomerStockInstance.properties = params

		if (!initCustomerStockInstance.save(flush: true)) {
			render(view: "edit", model: [initCustomerStockInstance: initCustomerStockInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'initCustomerStock.label', default: 'InitCustomerStock'),
			initCustomerStockInstance.id
		])
		redirect(action: "show", id: initCustomerStockInstance.id)
	}

	def delete(Long id) {
		def initCustomerStockInstance = InitCustomerStock.get(id)
		if (!initCustomerStockInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'initCustomerStock.label', default: 'InitCustomerStock'),
				id
			])
			redirect(action: "list")
			return
		}

		try {
			initCustomerStockInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'initCustomerStock.label', default: 'InitCustomerStock'),
				id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'initCustomerStock.label', default: 'InitCustomerStock'),
				id
			])
			redirect(action: "show", id: id)
		}
	}


	def uploadExcel() {
		String fileName = "E:\\excel\\initStock.xls";
		def file = new File(fileName)

		def workbook = new HSSFWorkbook(new FileInputStream(file))

		(0 .. workbook.numberOfSheets - 1).each {index->
			def sheet = workbook.getSheetAt(index)
			def firstRowNum = sheet.firstRowNum
			def lastRowNum = sheet.lastRowNum
			def predefinedFirstRowNum = 3
			println sheet.sheetName
			
			//(predefinedFirstRowNum .. lastRowNum).each {num ->
			for(int num=predefinedFirstRowNum;num<=lastRowNum;num++) {
				//if(sheet.sheetName=='绍兴'){
					
				
				
				def row = sheet.getRow(num)
				if(!rowValidate(row)) {
					continue;
				}
				
				
				if(row) {
					CustomerBranch customerBranch
					double stockQty;
					TimeByDay date;
					GasType gasType;
					try {
					 customerBranch= initCustomBranch(row);
					

					//double stockQty
					 stockQty = initStockQty(row);

					//TimeByDay date
					 date = initTimeByDay(row);
					//GasType gasType
					 gasType = initGasType(row);
					
					}catch(Exception e) {
					println "sheetname"+sheet.sheetName
					println "num" +num
					println "lastRowNum" +lastRowNum
					throw new RuntimeException(e);
				  }

					
					def customerStock = new InitCustomerStock()
					customerStock.customerBranch = customerBranch;
					customerStock.stockQty = stockQty;
					customerStock.date = date;
					customerStock.cdate = date.date;
					customerStock.gasType = gasType;
					

					customerStock.save(flush:true);

				}
				
				//}
			} 
		}
		flash.message = "初始数据上传OK";
		redirect(action: "list")
	}

	private def rowValidate(HSSFRow row) {
		HSSFCell cellCustomer= row.getCell(getCellPosition('B'));
		String customerName = cellCustomer?.getStringCellValue()?.trim();
		
		HSSFCell cellBranch= row.getCell(getCellPosition('A'));
		String branchName = cellCustomer?.getStringCellValue()?.trim();
		
		
		if(branchName&&customerName) {
			return true
		}
		
		return false
	}
	
	private def initGasType(HSSFRow row) {
		HSSFCell cellCategory= row.getCell(getCellPosition('C'));
		cellCategory.setCellType(HSSFCell.CELL_TYPE_STRING);
		def categoryStr = cellCategory.getStringCellValue()?.trim();
		
		def category = Category.findByName(categoryStr);
		if(!category) {
			category = new Category();
			category.name = categoryStr;
			category.save(true);
		}
		
		HSSFCell cellgasType= row.getCell(getCellPosition('D'));
		cellgasType.setCellType(HSSFCell.CELL_TYPE_STRING);
		def gasTypeStr = cellgasType.getStringCellValue()?.trim();
		
		if(!gasTypeStr) {
			gasTypeStr = categoryStr
		}
		
		def gasType =GasType.findByName(gasTypeStr); 
		if(!gasType) {
			gasType = new GasType();
			gasType.name = gasTypeStr;
			gasType.category= category;
			gasType.save(true);
		}
		
		return gasType
	}
	
	private def initTimeByDay(HSSFRow row) {
		return TimeByDay.findOrCreate(2012, 11, 31);
	}
	
	private def initStockQty(HSSFRow row) {
		HSSFCell cellQty= row.getCell(getCellPosition('H'));
		cellQty.setCellType(HSSFCell.CELL_TYPE_STRING);
		def qtyStr = cellQty.getStringCellValue()?.trim();
		double qty = 0;
		if(qtyStr&&qtyStr.trim() != "") {
			qty = Double.parseDouble(qtyStr);
		}

		return qty;
	}

	private def initCustomBranch(HSSFRow row) {

		HSSFCell cellBranch= row.getCell(getCellPosition('A'));
		//String branchName = cellBranch.stringCellValue;
		//branchName= branchName.substring(0, 2);
		def branchName = row.getSheet().getSheetName()

		def branch = Branch.findByName(branchName);

		if(!branch) {
			branch = new Branch()
			branch.name = branchName;
			branch.save(flush:true);
		}


		HSSFCell cellCustomer= row.getCell(getCellPosition('B'));
		String customerName = cellCustomer.getStringCellValue()?.trim();
		def customer = Customer.findByNameAndStatus(customerName,"ABLE")

		
		if(!customer) {
			def type3 = CustomerTypeLevel3.findByName("未指定");
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



	private int getCellPosition(String p) {
		def c = p as char
		def start = 'A' as char
		return (int)(c - start)
	}
}
