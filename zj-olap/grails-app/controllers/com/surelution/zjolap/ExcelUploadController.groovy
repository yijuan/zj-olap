package com.surelution.zjolap

import grails.converters.JSON
import groovy.time.TimeCategory

import java.text.SimpleDateFormat

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.dao.DataIntegrityViolationException

class ExcelUploadController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def springSecurityService

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		params.offset = params.offset?params.offset:0
		def user = springSecurityService.currentUser
		def loginBranch = user.branch
		def users =  User.findAllByBranch(loginBranch)

		def branches, branch
		if(!loginBranch) {
			branches = Branch.list()
		}

		def searchClosure =  {
			if(loginBranch) {
				'in'("user",users)
			}
			
			if(params.branch) {
				branch = Branch.get(params.branch)
				if(branch) {
					createAlias('user', 'u')
					eq('u.branch', branch)
				}
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
			
			if(params['sort']) {
				order(params['sort'], params['order']?params['order']:"desc")
			}
			
		}

		def c = ExcelUpload.createCriteria()
		def result = c.list(searchClosure, max:params.max, offset:(params.offset?params.offset:0))

		[excelUploadInstanceList:result, excelUploadInstanceTotal: result.totalCount, branches:branches, branch:branch]
	}

	def createSalesOrder() {
		[excelUploadInstance: new ExcelUpload(params)]
	}

	def createDelivery() {
		[excelUploadInstance: new ExcelUpload(params)]
	}

	def createStockBalance() {
		def file = new File("/Users/johnny/Google Drive/浙江业务分析系统/客存/初始化数据提供表-客存.xls")
		def workbook = new HSSFWorkbook(new FileInputStream(file))
		(0 .. workbook.numberOfSheets - 1).each {index->
			def sheet = workbook.getSheetAt(index)
			def firstRowNum = sheet.firstRowNum
			def lastRowNum = sheet.lastRowNum
			def predefinedFirstRowNum = 3
			println sheet.sheetName
			(predefinedFirstRowNum .. lastRowNum).each {num ->
				def row = sheet.getRow(num)
				if(row) {
					def branchName = row.getCell(getCellPosition("A"))?.getStringCellValue()?.trim()
					def cusomerName = row.getCell(getCellPosition('B'))?.getStringCellValue()?.trim()
					def category = row.getCell(getCellPosition('C'))?.getStringCellValue()?.trim()
					def gasType = getStringValue(row.getCell(getCellPosition('D')))
					def balance2012 = getStringValue(row.getCell(getCellPosition('E')))
					def amount2013 = getStringValue(row.getCell(getCellPosition('F')))
					def take2013 = getStringValue(row.getCell(getCellPosition('G')))
					def sbt = new StockBalanceTemp()
					sbt.branchName = branchName
					sbt.customerName = cusomerName
					sbt.categoryName = category
					sbt.gasTypeName = gasType
					sbt.balance2012 = balance2012
					sbt.salesAmount2013 = amount2013
					sbt.taking2013 = take2013
					sbt.balance2013 = "0"
					sbt.save()
				}
			}
		}
	}

	def saveDelivery() {
		def cal = Calendar.instance
		def sdf = new SimpleDateFormat("yyyy.MM.dd")
		def names = new HashSet<String>()
		def excelMessages = []
		def deliveries = []
		def filePath = grailsApplication.config.excel.importer.file.location
		def destPath = "${filePath}${UUID.randomUUID().toString()}"

		def excelFile = request.getFile('excelFile')
		excelFile.transferTo(new File(destPath))

		def excelUploadInstance = new DeliveryTakingUpload(params)
		excelUploadInstance.deleted = false
		excelUploadInstance.originalFileName = excelFile.originalFilename
		excelUploadInstance.filePath = destPath
		excelUploadInstance.uploadedAt = new Date()
		excelUploadInstance.user = springSecurityService.currentUser
		excelUploadInstance.save(flush:true)
		def CustomerTypeLevel3 level = CustomerTypeLevel3.get(37)

		def fis = new FileInputStream(destPath)
		def isr = new InputStreamReader(fis, "utf-16")
		int found = 0, notFound = 0
		int lineNo = 0
		isr.eachLine {line->
			lineNo++
			if(lineNo < 6) {
				return
			}
			def words = line.split("\t")
			if(words.length > 14) {
				def cn = words[12].trim()
				def gasTypeName = words[5].trim()
				def quantity = words[6]
				def branchName = words[16]
				def cust = Customer.findByName(cn)
				def takingAt = words[11]
				def deliveryNo = words[4]
				def tankFarm = words[8]

				def gta = GasTypeAlias.findOrCreate(gasTypeName)
				def tk = TankFarm.findOrCreate(tankFarm)
				def ba = BranchAlias.findOrCreate(branchName)

				//				println cn
				if(!cust) {
					cust = new Customer()
					cust.name = cn
					cust.customerType = level
					cust.save(flush:true)
				}
				def delivery = new DeliveryTaking()
				delivery.branch = ba.branch
				delivery.customer = cust
				delivery.takingAt = sdf.parse(takingAt)
				delivery.gasType = gta.gasType
				cal.time = delivery.takingAt
				delivery.timeByDay = TimeByDay.findOrCreate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
				delivery.quantity = quantity.replace(",", "") as double
				delivery.deliveryNo = deliveryNo
				delivery.tankFarm = tk
				delivery.upload = excelUploadInstance
				delivery.save(flush:true)

				def dsb = DeliveryStockBalance.createByTaking(delivery)
				dsb.save(flush:true)
			}
		}
		//		println names
		//		println "found:${found},not found:${notFound}"
		//		def workbook = new HSSFWorkbook(fis)
		//		def sheet = workbook.getSheet("1月28号台账.XLS")
		//		def firstRowNum = sheet.firstRowNum
		//		def lastRowNum = sheet.lastRowNum
		//		def predefinedFirstRowNum = 5
		//		if(firstRowNum != 0) {
		//			//第一行为表头，不能为空
		//			def soe = new ExcelInformationVo(row:1, column:0, message:'第一行为表头，不能为空')
		//			excelMessages.add(soe)
		//		}
		//		(predefinedFirstRowNum .. lastRowNum).each {num ->
		//			def row = sheet.getRow(num)
		//			def companyName = row.getCell(getCellPosition('R'))?.getStringCellValue()?.trim()
		//			if(!companyName) {
		//				return
		//			}
		//			def customerName = row.getCell(getCellPosition('N'))?.getStringCellValue()?.trim()
		//			def customer = Customer.findByName(customerName)
		//			if(!customer) {
		//				excelMessages.add(new ExcelInformationVo(row:num, column:'E', message:'客户名称不对，请确认客户名称是否与客户管理系统中的名称完全一致'))
		//			}
		//
		//			def branch = Branch.findByName(companyName)
		//			if(!branch) {
		//				excelMessages.add(new ExcelInformationVo(row:num, column:'A', message:'分公司名称不对'))
		//			}
		////			def salingTypeName = row.getCell(getCellPosition('B'))?.getStringCellValue()?.trim()
		//			int year = 0
		//			def yearCol = row.getCell(getCellPosition('B'))
		//			if(yearCol && yearCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
		//				year = yearCol.getNumericCellValue()
		//			} else {
		//				excelMessages.add(new ExcelInformationVo(row:num, column:'B', message:'年份必须为一个四位的数字，除这4个数字外，单元格内不能包含其他任何内容'))
		//			}
		//			int month = 0
		//			def monthCol = row.getCell(getCellPosition('C'))
		//			if(monthCol && monthCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
		//				month = monthCol.getNumericCellValue()
		//			} else {
		//				excelMessages.add(new ExcelInformationVo(row:num, column:'C', message:'月份必须为一个2为的数字，除这2个数字外，单元格内不能包含其他任何内容'))
		//			}
		//			int day = 0
		//			def dayCol = row.getCell(getCellPosition('D'))
		//			if(dayCol && dayCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
		//				day = dayCol.getNumericCellValue()
		//			} else {
		//				excelMessages.add(new ExcelInformationVo(row:num, column:'D', message:'日期必须为一个2位的数字，除这2个数字外，单元格内不能包含其他任何内容'))
		//			}
		////			def salesName = row.getCell(getCellPosition('F'))?.getStringCellValue()?.trim()
		////			def sales = Sales.findByNameAndBranch(salesName, branch)
		////			if(!sales) {
		////				excelMessages.add(new ExcelInformationVo(row:num, column:'F', message:'客户经理名字必须已经在系统中存在'))
		////			}
		//
		//			def gasTypeName = row.getCell(getCellPosition('F'))?.getStringCellValue()?.trim()
		//			def gasType = GasType.findByName(gasTypeName)
		//			if(!gasType) {
		//				excelMessages.add(new ExcelInformationVo(row:num, column:'F', message:'请确认油品名称是否正确，油品名称必须与在售油品名称保持一致'))
		//			}
		//			def quantity = 0
		//			def colQuantity = row.getCell(getCellPosition('G'))
		//			if(colQuantity && colQuantity.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
		//				quantity = colQuantity.getNumericCellValue()
		//			} else {
		//				excelMessages.add(new ExcelInformationVo(row:num, column:'G', message:'数量必须为一个数字，小数点必须是一个英文的点号。如果没有数量，必须填入0'))
		//			}
		//
		//			String deliveryNo
		//			def deliveryNoCol = row.getCell(getCellPosition('H'))
		//			if(deliveryNoCol && deliveryNoCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
		//				deliveryNo = "${deliveryNoCol.getNumericCellValue()}"
		//			} else {
		//				deliveryNo = deliveryNoCol.getStringCellValue()?.trim()
		//			}
		//
		//			String tankFarmName
		//			def tankFarmNameCol = row.getCell(getCellPosition('I'))
		//			if(tankFarmNameCol && tankFarmNameCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
		//				tankFarmName = "${tankFarmNameCol.getNumericCellValue()}"
		//			} else {
		//				tankFarmName = tankFarmNameCol.getStringCellValue()?.trim()
		//			}
		//			def tankFarm = TankFarm.findByName(tankFarmName)
		//			if(!tankFarm) {
		//				excelMessages.add(new ExcelInformationVo(row:num, column:'I', message:'油库名称不对，请确认填入的油库名称是否与系统中的油库名称完全一致'))
		//			}
		//			Calendar cal = Calendar.instance
		//			cal.set(Calendar.YEAR, year)
		//			cal.set(Calendar.MONTH, month)
		//			cal.set(Calendar.DAY_OF_MONTH, day)
		//			def saleAt = cal.time
		//
		//			def deliveryTaking = new DeliveryTaking()
		//			deliveryTaking.branch = branch
		//			deliveryTaking.customer = customer
		//			deliveryTaking.takingAt = saleAt
		//			deliveryTaking.gasType = gasType
		//			deliveryTaking.timeByDay = TimeByDay.findOrCreate(year, month, day)
		//			deliveryTaking.quantity = quantity
		//			deliveryTaking.deliveryNo = deliveryNo
		//			deliveryTaking.tankFarm = tankFarm
		//			deliveries.add(deliveryTaking)
		//		}
		//
		//        if (!excelMessages.isEmpty() || !excelUploadInstance.save(flush: true)) {
		//            render(view: "createSalesOrder", model: [excelUploadInstance: excelUploadInstance, excelMessages:excelMessages])
		//            return
		//        }

		deliveries.each {
			it.upload = excelUploadInstance
			it.save(flush:true)
		}

		flash.message = message(code: 'default.created.message', args: [
			message(code: 'excelUpload.label', default: 'ExcelUpload'),
			excelUploadInstance.id
		])
		redirect(action: "show", id: excelUploadInstance.id)
	}

	def saveSalesOrder() {
		def user = springSecurityService.currentUser
		def loginBranch = user.branch

		def cal = Calendar.instance
		def sdf = new SimpleDateFormat("yyyy年MM月dd")
		def excelMessages = []
		def salesOrders = []
		def filePath = grailsApplication.config.excel.importer.file.location
		def destPath = "${filePath}${UUID.randomUUID().toString()}"
		println destPath

		def excelFile = request.getFile('excelFile')

		excelFile.transferTo(new File(destPath))
		
		def datamonth = params.excelMonth;
		
		if(!datamonth) {
			flash.message = "请确认结算月是否选择！";
			render(view: "createSalesOrder")
			return
		}

		def excelUploadInstance = new SalesOrderUpload(params)
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
			render(view: "createSalesOrder")
			return

		}

		try {
			def sheet = workbook.getSheetAt(0)
			def firstRowNum = sheet.firstRowNum
			def lastRowNum = sheet.lastRowNum
			def predefinedFirstRowNum = 1

			def validateFMonthSet  = new HashSet<String>();
			if(firstRowNum != 0) {
				//第一行为表头，不能为空
				def soe = new ExcelInformationVo(row:1, column:0, message:'第一行为表头，不能为空')
				excelMessages.add(soe)
			}
			Price.updateCache()
			(predefinedFirstRowNum .. lastRowNum ).each {num ->
				println num
				def row = sheet.getRow(num)
				def companyName = row.getCell(getCellPosition('A'))?.getStringCellValue()?.trim();
				if(!companyName) {
					return
				}
				//TODO
				//validate验证单行数据
				validateSalesOrder(row,excelMessages,num)
			}
			//validate验证单行数据

			if (!excelMessages.isEmpty() ) {
				render(view: "createSalesOrder", model: [excelMessages:excelMessages])
				return
			}

			(predefinedFirstRowNum .. lastRowNum ).each {num ->
				println num
				def row = sheet.getRow(num)
				def companyName = row.getCell(getCellPosition('A'))?.getStringCellValue()?.trim();
				if(!companyName) {
					return
				}
				//TODO



				def branch = Branch.findByName(companyName)

				if(!branch) {
					//				excelMessages.add(new ExcelInformationVo(row:num, column:'A', message:'分公司名称不对'))
					branch = new Branch()
					branch.name = companyName
					branch.save(flush:true)
				}

				if(loginBranch!=null && branch.name != loginBranch.name) {
					excelMessages.add(new ExcelInformationVo(row:num, column:'A', message:'分公司名称,只能录入本公司信息'))
				}

				def salingTypeName = row.getCell(getCellPosition('B'))?.getStringCellValue()?.trim();
				def salingType = SalingType.findByName(salingTypeName)
				if(!salingType) {
					salingType = new SalingType()
					salingType.name = salingTypeName
					salingType.save(flush:true)
					//				excelMessages.add(new ExcelInformationVo(row:num, column:'B', message:'销售环节不对，请确认填入的销售环节是否与销售环节完全一致'))
				}




				//			def yearCol = row.getCell(getCellPosition('C'))
				//			if(yearCol && yearCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				//				year = yearCol.getNumericCellValue()
				//			} else {
				//				excelMessages.add(new ExcelInformationVo(row:num, column:'C', message:'年份必须为一个四位的数字，除这4个数字外，单元格内不能包含其他任何内容'))
				//			}
				int month = 0
				def monthCol = row.getCell(getCellPosition('C'))
				if(monthCol && monthCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					month = monthCol.getNumericCellValue()
				} else {
					try{
						month = Integer.parseInt(monthCol.getStringCellValue()?.trim())
					} catch(Exception e) {
						excelMessages.add(new ExcelInformationVo(row:num, column:'C', message:'月份必须为一个2为的数字，除这2个数字外，单元格内不能包含其他任何内容'))
					}
				}


				def colSaleDate = row.getCell(getCellPosition('F'))
				def saleDate

				if(colSaleDate.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					saleDate = sdf.parse(colSaleDate.getStringCellValue()?.trim())
				} else {
					saleDate = colSaleDate.getDateCellValue()
				}
				println saleDate

				if(!saleDate) {

					def calendarTemp  = Calendar.instance
					calendarTemp.set(Calendar.YEAR,2013)
					calendarTemp.set(Calendar.MONTH,month)
					calendarTemp.set(Calendar.DAY_OF_MONTH,1)
					saleDate= calendarTemp.getTime()
					use(TimeCategory) {
						saleDate = saleDate - 1.day
					}
				}

				def calendar = Calendar.instance
				calendar.setTime(saleDate)

				int orderYear = calendar.get(Calendar.YEAR)
				int orderMoth = calendar.get(Calendar.MONTH)+1
				int orderDay  = calendar.get(Calendar.DAY_OF_MONTH)


				//假如  结算月度为 1（month）     订单开单时间 2013。12.4 （orderYear） 则 结算月份为2014年1月
				int year = month<orderMoth ? orderYear+1:orderYear

				println orderYear+"年"+orderMoth+"月"+orderDay+"日"


				//传入的结算月
				def dataf = new SimpleDateFormat("yyyy-MM");
				def inputDate= dataf.parse(datamonth);
				

				calendar.setTime(inputDate)
				
				def inputYear = calendar.get(Calendar.YEAR)
				def inputMoth = calendar.get(Calendar.MONTH)+1
				def inputDay  = calendar.get(Calendar.DAY_OF_MONTH)
				
				
				if(inputMoth!=month||inputYear!=year) {
					excelMessages.add(new ExcelInformationVo(row:num, column:'C', message:'结算月【'+year+'-'+month+'】与传入的【'+datamonth+'】不一致'))
				}
				

				def fMonth = FinancialMonth.findByYearAndMonth(year, month)
				if(!fMonth) {
					fMonth = new FinancialMonth()
					fMonth.year = year
					fMonth.month = month
					fMonth.quarter = (month - 1) / 3 + 1
					fMonth.save(flush:true)
				}
				
				
				
				//			int day = 0
				//			def dayCol = row.getCell(getCellPosition('E'))
				//			if(dayCol && dayCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				//				day = dayCol.getNumericCellValue()
				//			} else {
				//				excelMessages.add(new ExcelInformationVo(row:num, column:'E', message:'日期必须为一个2位的数字，除这2个数字外，单元格内不能包含其他任何内容'))
				//			}


				//			def salesName = row.getCell(getCellPosition('F'))?.getStringCellValue()?.trim()
				//			def sales = Sales.findByNameAndBranch(salesName, branch)
				//			if(!sales) {
				//				excelMessages.add(new ExcelInformationVo(row:num, column:'F', message:'客户经理名字必须已经在系统中存在'))
				//			}
				def customerName = row.getCell(getCellPosition('D'))?.getStringCellValue()?.trim()
				def customer = Customer.findByNameAndStatus(customerName,"ABLE")
				def colL = getStringValue(row.getCell(getCellPosition('L')))
				def colM = getStringValue(row.getCell(getCellPosition('M')))
				def colN = getStringValue(row.getCell(getCellPosition('N')))

				if(!colL) {
					colL = '未指定'
				}

				boolean isHasChildType1 = true;
				boolean isHasChildType2 = true;
				if(!colM) {
					colM = colL
					isHasChildType1 = false;
				}
				if(!colN) {
					colN = colM
					isHasChildType2 = false;
				}
				if(!customer) {
					//				excelMessages.add(new ExcelInformationVo(row:num, column:'G', message:'客户名称不对，请确认客户名称是否与客户管理系统中的名称完全一致'))
					customer = new Customer()
					customer.name = customerName

					def level3, level2, level1

					level3 = CustomerTypeLevel3.findByName(colN)
					if(!level3) {
						level2 = CustomerTypeLevel2.findByName(colM)
						if(!level2) {
							level1 = CustomerType.findByName(colL)
							if(!level1) {
								level1 = new CustomerType()
								level1.name = colL
								level1.isHasChild = isHasChildType1
								level1.save(flush:true)
							}

							level2 = new CustomerTypeLevel2()
							level2.name = colM
							level2.level1 = level1
							level2.isHasChild = isHasChildType2
							level2.save(flush:true)


							if(!level1.isHasChild) {
								level1.noChildLevel2 = level2
								level1.save(flush:true)
							}
						}

						level3 = new CustomerTypeLevel3()
						level3.name = colN
						level3.level2 = level2
						level3.save(flush:true)


						if(!level2.isHasChild) {
							level2.noChildLevel3 = level3
							level2.save(flush:true)
						}
					}

					customer.customerType = level3
					customer.status = "ABLE"
					customer.save(flush:true)
				}


				String orderFormNo
				def orderFormNoCol = row.getCell(getCellPosition('E'))
				orderFormNoCol.setCellType(HSSFCell.CELL_TYPE_STRING)
				if(orderFormNoCol && orderFormNoCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					orderFormNo = "${orderFormNoCol.getNumericCellValue().intValue()}"
				} else {
					orderFormNo = orderFormNoCol.getStringCellValue()?.trim()
				}
				println "orderFormNo："+orderFormNo
				def gasTypeName
				def colH = row.getCell(getCellPosition('H'))
				colH.setCellType(HSSFCell.CELL_TYPE_STRING)
				if(colH && colH.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					gasTypeName = "${colH.getNumericCellValue()}"
				} else {
					gasTypeName = colH?.getStringCellValue()?.trim()
				}
				
				
				def bigSaling
				def colP = row.getCell(getCellPosition('P'))
//				colP.setCellType(HSSFCell.CELL_TYPE_STRING)
				if(colP && colP.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					bigSaling = "${colP.getNumericCellValue()}"
				} else {
					bigSaling = colP?.getStringCellValue()?.trim()
				}


				def gasCategoryName = row.getCell(getCellPosition('G'))?.getStringCellValue()?.trim()

				//def gasType = GasTypeAlias.findOrCreate(gasTypeName).gasType

				def gasType = GasType.findByName(gasTypeName)
				if(!gasType) {
					//				excelMessages.add(new ExcelInformationVo(row:num, column:'L', message:'请确认油品名称是否正确，油品名称必须与在售油品名称保持一致'))
					def gasCategory = Category.findByName(gasCategoryName)
					if(!gasCategory) {
						gasCategory = new Category()
						gasCategory.name = gasCategoryName
						gasCategory.save(flush:true)
					}
					gasType = new GasType()
					gasType.name = gasTypeName
					gasType.category = gasCategory
					gasType.save(flush:true)
				}

				def colI = row.getCell(getCellPosition('I'))
				def quantity = 0
				if(colI && colI.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					quantity = colI.getNumericCellValue()
				} else {
					//部分数据是用来调账的，跳过着一条数据
					//	return
					//excelMessages.add(new ExcelInformationVo(row:num, column:'I', message:'数量必须为一个数字，小数点必须是一个英文的点号。如果没有数量，必须填入0'))
				}


				//TODO 售价
				def listUnitPrice = 0
				if(gasType) {
					try{
						listUnitPrice = Price.findPrice(gasType, saleDate)
					}catch(LackOfPriceException e) {
						excelMessages.add(new ExcelInformationVo(row:num, column:'X', message:'请先维护有效的${gasType.name}在${saleDate.format("yyyy-MM-dd")}的结算价格'))
					}
				}
//				def colX = row.getCell(getCellPosition('X'))
//				colX.setCellType(HSSFCell.CELL_TYPE_STRING)
//				if(colX && colX.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//					listUnitPrice = colX.getNumericCellValue()
//				} else {
//					try{
//						listUnitPrice = Float.parseFloat(colX.getStringCellValue()?.trim())
//					}catch(Exception e) {
//						excelMessages.add(new ExcelInformationVo(row:num, column:'X', message:'销售单价必须为一个数字，小数点必须是一个英文的点号。'))
//					}
//				}
				def retailPrice = 0
				//			def colAB = row.getCell(getCellPosition('Z') + 2)
				//			if(colAB && colAB.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				//				retailPrice = colAB.getNumericCellValue()
				//			} else {
				//				try{
				//					retailPrice = Float.parseFloat(colAB.getStringCellValue()?.trim())
				//				}catch(Exception e) {
				//					excelMessages.add(new ExcelInformationVo(row:num, column:'AB', message:'销售单价必须为一个数字，小数点必须是一个英文的点号。'))
				//				}
				//			}

				def purchasingUnitPrice = 0
				def colJ = row.getCell(getCellPosition('J'))
				colJ.setCellType(HSSFCell.CELL_TYPE_STRING)
				if(colJ && (colJ.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || colJ.getCellType() == HSSFCell.CELL_TYPE_FORMULA)) {
					purchasingUnitPrice = colJ.getNumericCellValue()
				} else {
					try{
						purchasingUnitPrice = Float.parseFloat(colJ.getStringCellValue()?.trim())
					}catch(Exception e) {
						//部分金额是用来调账的
						//return
						//excelMessages.add(new ExcelInformationVo(row:num, column:'J', message:'销售单价必须为一个数字，小数点必须是一个英文的点号。'))
					}
				}
				//			Calendar cal = Calendar.instance
				//			cal.set(Calendar.YEAR, year)
				//			cal.set(Calendar.MONTH, month)
				//			cal.set(Calendar.DAY_OF_MONTH, day)

				//		def colF = row.getCell(getCellPosition('F'))
				//		def saleAt
				//	if(colF.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				//		saleAt = sdf.parse(colF.getStringCellValue()?.trim())
				//	} else {
				//		saleAt = colF.getDateCellValue()
				//	}
				//	if(!saleAt) {
				//		def c2 = Calendar.instance
				//		c2.clear()
				//		c2.set(Calendar.YEAR, orderYear)
				//		c2.set(Calendar.MONTH, orderMoth - 1)
				//		c2.set(Calendar.DAY_OF_MONTH, orderDay)
				//		saleAt = c2.time
				//	}
				def salesName
				def colSalesName = row.getCell(getCellPosition('Z') + 6)
				if(colSalesName && colSalesName.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					salesName = colSalesName.getStringCellValue()?.trim()
				} else {
					salesName = "营销部"
				}
				def sales = Sales.findByNameAndBranch(salesName, branch)
				if(!sales) {
					sales = new Sales()
					sales.enabled = true
					sales.name = salesName
					sales.branch = branch
					sales.save(flush:true)
				}

				def salesOrder = new SalesOrder()
				salesOrder.orderFormNo = orderFormNo
				salesOrder.branch = branch
				salesOrder.sales = sales
				salesOrder.customer = customer
				salesOrder.salingtype = salingType
				salesOrder.salingAt = saleDate
				salesOrder.gasType = gasType
				cal.time = saleDate
				salesOrder.timeByDay = TimeByDay.findOrCreate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
				salesOrder.quantity = quantity
				salesOrder.month = fMonth
				salesOrder.purchasingUnitPrice = purchasingUnitPrice
				salesOrder.excelRowIndex = num
				salesOrder.listUnitPrice = listUnitPrice
				salesOrder.retailPrice = retailPrice

				salesOrder.purchasingPrice = 0
				salesOrder.purchasingPriceRate = 0
				salesOrder.countPrice = 0
				salesOrder.isVail = true
				salesOrder.optionValue = SalesOrder.OPTION_VALUE_EXCEL_ADD
				salesOrder.status = SalesOrder.STATUS_ABLE
				salesOrder.isClosed  = false
				
				def bigCase = BigCase.findByName(bigSaling)
				if(!bigCase) {
					bigCase = BigCase.findByDefaultOption(true)
				}
				salesOrder.bigCase = bigCase
				
				def customerBranch = CustomerBranch.create(salesOrder.customer, salesOrder.branch, salesOrder.timeByDay)

				salesOrder.customerBranch = customerBranch

				validateFMonthSet.add(salesOrder.month.getYear()+"-"+salesOrder.month.getMonth())
				salesOrders.add(salesOrder)


				if(!excelUploadInstance.month) {
					excelUploadInstance.month = fMonth
				}

			}

			/**
			 * 验证账期，每次上传只能是同一个账期
			 */
			//TODO

			String monthResult = validateFMonth(validateFMonthSet)
			if(monthResult) {
				flash.message = monthResult
				render(view: "createSalesOrder")
				return
			}

			if (!excelMessages.isEmpty() || !excelUploadInstance.save(flush: true)) {
				render(view: "createSalesOrder", model: [excelUploadInstance: excelUploadInstance, excelMessages:excelMessages])
				return
			}

			salesOrders.each {so ->
				so.upload = excelUploadInstance
				so.save()
			}

			flash.message = message(code: 'default.created.message', args: [
				message(code: 'excelUpload.label', default: 'ExcelUpload'),
				excelUploadInstance.id
			])
			//redirect(action: "show", id: excelUploadInstance.id)
			redirect(action:'createSalesOrder')
		}catch(Exception ex) {
			ex.printStackTrace()
			flash.message = "请确认上传的模版正确，上传的必须为销售模版";
			render(view: "createSalesOrder")
			return
		}
	}

	/**
	 * 0.分公司是否存在
	 * 1。账期检查
	 * 2.客户经理检查
	 * 3.客户检查
	 * 4.客户类型检查
	 * 5。销售环节检查
	 * 
	 * @param salesOrder 
	 * @param excelMessages
	 * @return
	 */
	private def validateSalesOrder(HSSFRow row,excelMessages,num) {
		//0.分公司是否存在
		def companyName = row.getCell(getCellPosition('A'))?.getStringCellValue()?.trim()
		def  branch = Branch.findByName(companyName)
		if(!branch) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'A', message:'公司名称不对，请确认公司名称名称是否与系统中的名称完全一致'))
		}
		//1。账期检查 同分公司数据不能是已结算的

		int month = 0
		def monthCol = row.getCell(getCellPosition('C'))
		if(monthCol && monthCol.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			month = monthCol.getNumericCellValue()
		} else {
			try{
				month = Integer.parseInt(monthCol.getStringCellValue()?.trim())
			} catch(Exception e) {
			}
		}


		def sdf = new SimpleDateFormat("yyyy年MM月dd")
		def colSaleDate = row.getCell(getCellPosition('F'))
		def saleDate

		try {
			if(colSaleDate.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				saleDate = sdf.parse(colSaleDate.getStringCellValue()?.trim())
			} else {
				saleDate = colSaleDate.getDateCellValue()
			}

			def calendar = Calendar.instance
			calendar.setTime(saleDate)

			int orderYear = calendar.get(Calendar.YEAR)
			int orderMoth = calendar.get(Calendar.MONTH)+1
			int orderDay  = calendar.get(Calendar.DAY_OF_MONTH)


			//假如  结算月度为 1（month）     订单开单时间 2013。12.4 （orderYear） 则 结算月份为2014年1月
			int year = month <orderMoth ? orderYear+1:orderYear

			println orderYear+"年"+orderMoth+"月"+orderDay+"日"




			def fMonth = FinancialMonth.findByYearAndMonth(year, month)
			if(branch&&fMonth) {
				//如果查询到了已经关账的数据就不能提交此次数据了
				def saleOrder = SalesOrder.findByMonthAndBranchAndIsClosed(fMonth,branch,true)
				if(saleOrder) {
					excelMessages.add(new ExcelInformationVo(row:num, column:'C', message:'该账期已经关闭，不能提交任何数据'))
				}
			}

		}catch(Exception ex) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'F', message:'订单开单日期必须正确输入，不能为空！'))
		}
		//2.客户经理检查
		def salesName
		def colSalesName = row.getCell(getCellPosition('Z') + 6)
		if(colSalesName && colSalesName.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			salesName = colSalesName.getStringCellValue()?.trim()
		} //else {
			//salesName = "营销部"
//		}

		def sales = Sales.findByNameAndBranch(salesName, branch)
		if(!sales) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'AF', message:'经理人名称不对，请确认经理人名称是否与系统中的名称完全一致'))
		}

		//3.客户检查
		def customerName = row.getCell(getCellPosition('D'))?.getStringCellValue()?.trim()
		def customer = Customer.findByNameAndStatus(customerName,"ABLE")

		if(!customer) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'D', message:'客户名称不对，请确认客户名称是否与系统中的名称完全一致'))
		}

		//4.客户类型检查
//		def colL = getStringValue(row.getCell(getCellPosition('L')))
//		def colM = getStringValue(row.getCell(getCellPosition('M')))
//		def colN = getStringValue(row.getCell(getCellPosition('N')))
//
//		if(colL) {
//			def type1 = CustomerType.findByName(colL);
//			if(!type1) {
//				excelMessages.add(new ExcelInformationVo(row:num, column:'L', message:'销售类别名称不对，请确认销售类别是否与系统中的名称完全一致'))
//			}
//		}
//
//		if(colM) {
//			def type2 = CustomerTypeLevel2.findByName(colM);
//			if(!type2) {
//				excelMessages.add(new ExcelInformationVo(row:num, column:'M', message:'机构用户分类名称不对，请确认机构用户分类是否与系统中的名称完全一致'))
//			}
//		}
//
//
//		if(colN) {
//			def type3 = CustomerTypeLevel3.findByName(colN);
//			if(!type3) {
//				excelMessages.add(new ExcelInformationVo(row:num, column:'N', message:'工业分类名称不对，请确认工业分类是否与系统中的名称完全一致'))
//			}
//		}

		//5。销售环节检查
		row.getCell(getCellPosition('B'))?.setCellType(HSSFCell.CELL_TYPE_STRING)
		def salingTypeName = row.getCell(getCellPosition('B'))?.getStringCellValue()?.trim()
		def salingType = SalingType.findByName(salingTypeName)
		if(!salingType) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'B', message:'销售环节不对，请确认填入的销售环节是否与销售环节管理完全一致'))
		}



		//6 油品类型验证
		row.getCell(getCellPosition('G'))?.setCellType(HSSFCell.CELL_TYPE_STRING)
		def gasCategoryName = row.getCell(getCellPosition('G'))?.getStringCellValue()?.trim()
		def category = Category.findByName(gasCategoryName);
		if(!category) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'G', message:'油品类型不对，请确认填入的油品类型是否与油品类型管理完全一致'))
		}

		//7.油品品号

		def colH = row.getCell(getCellPosition('H'))
		colH.setCellType(HSSFCell.CELL_TYPE_STRING)
		def	gasTypeName = colH?.getStringCellValue()?.trim()
		def gasType = GasType.findByName(gasTypeName);

		if(!gasType) {
			excelMessages.add(new ExcelInformationVo(row:num, column:'H', message:'油品品号不对，请确认填入的油品品号是否与油品品号管理完全一致'))
		}


		//8.数量
		def colI = row.getCell(getCellPosition('I'))
		def quantity = 0
		if(colI && colI.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			quantity = colI.getNumericCellValue()
		} else {
			//部分数据是用来调账的，跳过着一条数据
			//	return
			excelMessages.add(new ExcelInformationVo(row:num, column:'I', message:'数量不能为空且必须为一个数字，小数点必须是一个英文的点号。如果没有数量，必须填入0'))
		}


		//销售价格
		def purchasingUnitPrice = 0
		def colJ = row.getCell(getCellPosition('J'))
		colJ.setCellType(HSSFCell.CELL_TYPE_STRING)
		if(colJ && (colJ.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || colJ.getCellType() == HSSFCell.CELL_TYPE_FORMULA)) {
			purchasingUnitPrice = colJ.getNumericCellValue()
		} else {
			try{
				purchasingUnitPrice = Float.parseFloat(colJ.getStringCellValue()?.trim())
			}catch(Exception e) {

				excelMessages.add(new ExcelInformationVo(row:num, column:'J', message:'销售单价不能为空且必须为一个数字，小数点必须是一个英文的点号。'))
			}
		}


		
		//TODO 售价
		def listUnitPrice = 0
		if(gasType && saleDate) {
			try{
				listUnitPrice = Price.findPrice(gasType, saleDate)
			}catch(LackOfPriceException e) {
				excelMessages.add(new ExcelInformationVo(row:num, column:'X', message:"请先维护有效的${gasType.name}在${saleDate.format('yyyy-MM-dd')}的结算价格"))
			}
		}
//		
//		//批发到位价
//		def listUnitPrice = 0
//		def colX = row.getCell(getCellPosition('X'))
//		colX.setCellType(HSSFCell.CELL_TYPE_STRING)
//		if(colX && colX.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//			listUnitPrice = colX.getNumericCellValue()
//		} else {
//			try{
//				listUnitPrice = Float.parseFloat(colX.getStringCellValue()?.trim())
//			}catch(Exception e) {
//				excelMessages.add(new ExcelInformationVo(row:num, column:'X', message:'批发到位价不能为空且必须为一个数字，小数点必须是一个英文的点号。'))
//			}
//		}
//
//
//		println excelMessages;



	}


	//每次上传的数据必须是同一个账期
	private def validateFMonth(HashSet fmset) {
		if(fmset.size()==1) {
			return null
		}

		String message = "上传数据包含账期(FMS)，批量上传数据只能上传一个账期的数据";

		String fm = "";

		for(String f:fmset) {
			fm +=" "+f
		}

		return message.replace("FMS",fm)
	}


	def show(Long id) {
		def excelUploadInstance = ExcelUpload.get(id)
		if (!excelUploadInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'excelUpload.label', default: 'ExcelUpload'),
				id
			])
			redirect(action: "list")
			return
		}

		[excelUploadInstance: excelUploadInstance]
	}

	def edit(Long id) {
		def excelUploadInstance = ExcelUpload.get(id)
		if (!excelUploadInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'excelUpload.label', default: 'ExcelUpload'),
				id
			])
			redirect(action: "list")
			return
		}

		[excelUploadInstance: excelUploadInstance]
	}

	def update(Long id, Long version) {
		def excelUploadInstance = ExcelUpload.get(id)
		if (!excelUploadInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'excelUpload.label', default: 'ExcelUpload'),
				id
			])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (excelUploadInstance.version > version) {
				excelUploadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'excelUpload.label', default: 'ExcelUpload')] as Object[],
						"Another user has updated this ExcelUpload while you were editing")
				render(view: "edit", model: [excelUploadInstance: excelUploadInstance])
				return
			}
		}

		excelUploadInstance.properties = params

		if (!excelUploadInstance.save(flush: true)) {
			render(view: "edit", model: [excelUploadInstance: excelUploadInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'excelUpload.label', default: 'ExcelUpload'),
			excelUploadInstance.id
		])
		redirect(action: "show", id: excelUploadInstance.id)
	}

	def delete(Long id) {
		def msg
		def succeed = true
		def excelUploadInstance = SalesOrderUpload.get(id)
		if (!excelUploadInstance) {
//			flash.message = message(code: 'default.not.found.message', args: [
//				message(code: 'excelUpload.label', default: 'ExcelUpload'),
//				id
//			])
//			redirect(action: "list")
//			return
			msg = "没有找到相关文件，可能是其他用户已经删除了该文件，请刷新再试！"
			succeed = false
		}

		def fileName = excelUploadInstance?.originalFileName

		try {
			List<SalesOrder>  salesOrders = SalesOrder.findAllByUpload(excelUploadInstance)
			salesOrders.each{
				//it.isVail = false
				//it.optionValue = SalesOrder.OPTION_VALUE_DELETE
				it.delete()
			}

			excelUploadInstance.deleted = true
			excelUploadInstance.save(flush: true)
//			flash.message = message(code: 'default.deleted.message', args: [fileName, ""])
//			redirect(action: "list")
			msg = "删除成功"
		} catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [fileName, ""])
			redirect(action: "show", id: id)
		}
		render([succeed:succeed, msg:msg] as JSON)
	}

	private int getCellPosition(String p) {
		def c = p as char
		def start = 'A' as char
		return (int)(c - start)
	}

	private String getStringValue(def col) {
		if(!col) {
			return "0"
		}
		try{
			if(col && col.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				return "${col.getNumericCellValue().intValue()}"
			} else if(col && col.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
				return "${col.getNumericCellValue().intValue()}"
			} else {
				return  col.getStringCellValue()?.trim()
			}
		}catch(Exception e) {
			println "error:row${col.rowIndex}, column${col.columnIndex}"
		}
		return ""
	}



	def downFile(Long id) {
		def fileUpload= ExcelUpload.get(id)
		def filePath = fileUpload .filePath;

		File file = new File(filePath)

		response.setContentType("application/msexcel")
		response.setHeader("Content-disposition", "attachment;filename=${fileUpload.originalFileName}")


		def os = response.outputStream
		os << file.bytes
		os.flush()
		return


	}
}