package com.surelution.zjolap

import grails.converters.JSON
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.commons.CommonsMultipartFile

class CustomerController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def springSecurityService

	def index() {
		redirect(action: "list", params: params)
	}
	
	def downloadExcel() {
		def head =[
			"名称",
			"电话",
			"销售类别",
			"机构用户分类",
			"工业分类",
			"提报分公司",
			"地址"
		];
	
	
	def user = springSecurityService.currentUser
	def loginBranch = user.branch

//		def customerBranchs
//		def ids = new ArrayList();
	def sortBy = params['sort']
	def cvResult = CustomerBranch.createCriteria().list() {
		createAlias('customer', 'c')
		eq("c.status", "ABLE")
		if(loginBranch) {
			eq('branch', loginBranch)
		}

	}
//		def customers = Customer.findAllByStatus(Customer.STATUS_ABLE)

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
		cvResult.each {c ->
			def customer = c.customer
			def rowbody= sheet.createRow(idx);
			createCell(0,getStringValue(customer?.name),rowbody);
			createCell(1,getStringValue(customer?.tel),rowbody);
			def typeLevel3 = customer.getCustomerType()
			def typeLevel2 = typeLevel3.level2
			def typeLevel = typeLevel2.level1
			createCell(2,getStringValue(typeLevel?.name),rowbody);
			createCell(3,getStringValue(typeLevel?.isHasChild?typeLevel2?.name:""),rowbody);
			createCell(4,getStringValue(typeLevel2?.isHasChild?typeLevel3?.name:""),rowbody);
			createCell(5,getStringValue(c.branch?.name),rowbody);
			createCell(6,getStringValue(customer?.address),rowbody);
			
			idx++;
		}
		
		//下载
		response.setContentType("application/vnd.ms-excel")
		response.setHeader("Content-disposition", "attachment;filename=file.xls")
		def os = response.outputStream;
		wb.write(os)
		os.flush()
		return
	}
	
	def selector() {
		def customerName = params.customerName
		def branchId = params.branchId
		def max = Math.min(params.max ?: 20, 100)
		def result = CustomerBranch.createCriteria().list(max:max, offset:params.offset) {
			createAlias("customer", "c")
			if(branchId) {
				createAlias("branch", "b")
				eq('b.id', branchId as long)
			}
			like('c.name', "%${customerName}%")
		}

		[customerInstanceList: result, customerInstanceTotal: result.totalCount]
	
	}
	
	def customerBranchSelector(){
		        def customerName = params.term
		//		def branchId = params.branchId
				
				def currBranch = springSecurityService.currentUser.branch
				
		//		def max = Math.min(params.max ?: 20, 100)
				def result = CustomerBranch.createCriteria().list() {
					createAlias("customer", "c")
					if(currBranch) {
		//			createAlias("branch", "b")
						eq('branch', currBranch)
					}
					like('c.name', "%${customerName}%")
				}
				
				render result.collect(){['id' : it.id,'label':it.customer.name+">>"+it.branch.name, 'value':it.customer.name+">>"+it.branch.name]} as JSON
				
			
			
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)

		def user = springSecurityService.currentUser
		def loginBranch = user.branch

		params.max = Math.min(max ?: 10, 100)
		def customerName = params.customerName

//		def customerBranchs
//		def ids = new ArrayList();
		def sortBy = params['sort']
		def cvResult = CustomerBranch.createCriteria().list(max:params.max, offset:params.offset) {
			createAlias('customer', 'c')
			eq("c.status", "ABLE")
			if(customerName) {
				like('c.name', "%${customerName}%")
			}
			if(loginBranch) {
				eq('branch', loginBranch)
			}

			if(sortBy) {
				if(sortBy == "branch") {
					order("branch", params['order']?params['order']:"desc")
				} else {
					order("c.${sortBy}", params['order']?params['order']:"desc")
				}
			}
		}
//		def result = cvResult.collect() {
//			it.customer
//		}
//		customerBranchs = CustomerBranch.findAllByBranch(loginBranch)

//		if(customerBranchs) {
//			customerBranchs.each {
//				if(it.customer) {
//					ids.add(it.customer.id)
//				}
//			}
//		}
	
//		if(loginBranch) {}
//		 else {
//			result = Customer.createCriteria().list(max:params.max, offset:params.offset) {
//				eq("status", "ABLE")
//				if(customerName) {
//					like('name', "%"+customerName+"%")
//				}
//	
//				if(sortBy) {
//					order(sortBy, params['order']?params['order']:"desc")
//				}
//			}
//		}

		[customerInstanceList: cvResult, customerInstanceTotal: cvResult.totalCount]
	}



	def listApprove(Integer max) {

		params.max = Math.min(max ?: 10, 100)

		def user = springSecurityService.currentUser
		def loginBranch = user.branch


		params.max = Math.min(max ?: 10, 100)
		def customerName =params.customerName

		def customerBranchs

		def ids = new ArrayList();
		if(loginBranch) {
			customerBranchs = CustomerBranch.findAllByBranch(loginBranch)

			if(customerBranchs) {
				customerBranchs.each {
					if(it.customer) {
						ids.add(it.customer.id)
					}
				}
			}
		}


		if(loginBranch) {
			customerBranchs = CustomerBranch.findAllByBranch(loginBranch)
		}

		def result = Customer.createCriteria().list(max:params.max, offset:params.offset) {
			if(customerName) {
				like('name', "%"+customerName+"%")
			}

			if(loginBranch) {
				'in'("id",ids)
			}

			
			
			
			if(!loginBranch) {
				'in'("status",["ADD","DELETE","UPDATE","DISABLE"])
			}else {
				ne('status',"ABLE")
			}
			
			
			
			order("status", "asc")
			order("updateByBranch", "desc")
			
		}

		[customerInstanceList: result, customerInstanceTotal: result.totalCount]
	}

	def create() {
		[customerInstance: new Customer(params)]
	}

	def saveApprove() {
		def id = params.id
		def reason = params.reason
		
		def custAppor =  Customer.get(id)

		def oldCust
		def user = springSecurityService.currentUser
		if(custAppor){

			def oldCustId = custAppor.updateFrom
			def operMethod = custAppor.status
			if(oldCustId) {
				oldCust = Customer.get(oldCustId)
			}


			if(Customer.STATUS_ADD == operMethod) {
				//老数据状态为有效
				custAppor.status = Customer.STATUS_ABLE
				custAppor.reason = reason
				custAppor.save(flush:true)
				
			}else if (Customer.STATUS_UPDATE == operMethod) {


			
				//进这方法可能老客户为空（分公司新增，审批驳回，再修改的时候），这样只要改变新的数据就好，而没有老的。
			
				if(oldCust) {
					//删除老数据，新数据为有效
					Long oldId = oldCust.id
					Long newId = custAppor.id
					deleteFileUpdate(oldId)
					//oldCust.properties = custAppor.properties
	
					oldCust.name = custAppor.name
					oldCust.customerType = custAppor.customerType
					oldCust.address= custAppor.address
					oldCust.tel= custAppor.tel
					oldCust.license= custAppor.license
					oldCust.licensePicUrl= custAppor.licensePicUrl
					oldCust.id = oldId
					oldCust.updateFrom =null
					oldCust.reason = reason
					oldCust.save(flush:true)
	
					//把修改的文件列表指向老的客户
					updateFile(newId,oldId)
	
					updateCustomBranch(custAppor,oldCust);
	
					Customer.withTransaction {
						custAppor.delete(flush:true)
					}
				}else{
					custAppor.status = Customer.STATUS_ABLE;
					custAppor.reason = reason
					custAppor.save(flush:true);
				}

			}else if (Customer.STATUS_DELETE == operMethod) {
				//老数据  新数据都删除

				deleteFileUpdate(oldCust.id)
				deleteFileUpdate(custAppor.id)

				def loginBranch = user.branch
				def oldCustomerBranch= CustomerBranch.findAllByCustomer(oldCust);
				
				if(oldCustomerBranch){
					oldCustomerBranch.each {
						it.delete(flush:true)
					}
				}


				def newCustomerBranch = CustomerBranch.findAllByCustomer(custAppor);
				if(newCustomerBranch){
					newCustomerBranch.each {
						it.delete(flush:true)
					}
				}

				oldCust.delete(flush:true)
				custAppor.delete(flush:true)

			}
		}
		redirect(action: "listApprove" )
	}

	def updateCustomBranch(newCustomer,oldCustomer) {
		def user = springSecurityService.currentUser
		def loginBranch = user.branch
		def newCustomerBranchs= CustomerBranch.findAllByCustomer(newCustomer);

		/*if(newCustomerBranch) {
		 println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"+newCustomerBranch.id);
		 newCustomerBranch.delete(flush:true)
		 }*/

		if(newCustomerBranchs){
			newCustomerBranchs.each {
				CustomerBranch.create(oldCustomer, it.branch, null);
				it.delete(flush:true)
			}	
			
		}


		//def oldCustomer =  Customer.get(oldId);



	}

	def deleteFileUpdate(Long customId) {
		def files = FileUpload.findAllByFromIDAndUseOpter(customId,"CUSTOMER")
		if(files) {
			files.each { file->
				file.delete(flush:true)
			}
		}
	}

	def updateFile(newId,oldId) {
		def files = FileUpload.findAllByFromIDAndUseOpter(newId,"CUSTOMER")
		if(files) {
			files.each { file->
				file.fromID = oldId
				file.save(flush:true)
			}
		}
	}

	def saveDisApprove() {
		def id = params.id
		def reason = params.reason
		def custAppor =  Customer.get(id)
		if(custAppor) {
			custAppor.status = Customer.STATUS_DISABLE
			custAppor.reason = reason
			custAppor.save(flush:true)
		}
		redirect(action: "listApprove" )
	}
	
	def validateBrancLogin(loginBranch,customerInstance) {
		def validateStr = null;
		
		def tel = customerInstance.tel;
		if(!tel) {
			return  "电话号码不能为空。";
		}
		
		def address = customerInstance.address;
		if(!tel) {
			return  "地址不能为空。";
		}
		
		
		def customerName = customerInstance?.name;
		
		def  customer= Customer.findByName(customerName);
		if(customer) {
			return "已经有这客户，不能新增";
		}
		
		/*def branch = Branch.get(loginBranch.id);
		
		def cb = CustomerBranch.findByCustomerAndBranch(customer,branch);
		if(cb) {
			validateStr ="已经有这客户，不能新增";
		}*/
		return validateStr
	}
	
	
	def validateBrancLoginD(loginBranch,customerInstance) {
		def validateStr = null;
		
	
		
		def customerName = customerInstance?.name;
		
		def  customer= Customer.findByName(customerName);
		if(customer) {
			return "已经有这客户，不能新增";
		}
		
		/*def branch = Branch.get(loginBranch.id);
		
		def cb = CustomerBranch.findByCustomerAndBranch(customer,branch);
		if(cb) {
			validateStr ="已经有这客户，不能新增";
		}*/
		return validateStr
	}

	def save() {

		def customerInstance = new Customer(params)
		
		
		
		def user = springSecurityService.currentUser
		def loginBranch = user.branch
		def msg = null;
		if(loginBranch) {
			  msg = validateBrancLogin(loginBranch,customerInstance);
		}else {
		   	 msg = validateCustomer(customerInstance)
		}
		
		if(msg) {
			flash.message = msg;
		//	render(view: "create", model: [customerInstance: customerInstance])
			redirect(action:'list')
			return ;
		}
		
		
		def file = request.getFile('licenseFile')

		def filePath = grailsApplication.config.file.importer.file.location
		def uuid = UUID.randomUUID().toString()
		def destPath = "${filePath}${uuid}"


		file.transferTo(new File(destPath))


		def fileUpload = new FileUpload()
		fileUpload.fileName   = file.originalFilename
		fileUpload.fileUUID =uuid
		fileUpload.fileUrl = destPath
		fileUpload.uploadDate = new Date()
		fileUpload.useOpter = "CUSTOMER"
		fileUpload.save(flush: true)

		//def customerInstance = new Customer(params)
		customerInstance.licensePicUrl = fileUpload
		
		customerInstance.customerType = getCustomerTypeLevel3();

		//def user = springSecurityService.currentUser
		//def loginBranch = user.branch

		if(loginBranch) {
			customerInstance.status = Customer.STATUS_ADD
			customerInstance.updateByBranch = loginBranch
			customerInstance.addByBranch = loginBranch
		} else {
			customerInstance.status = Customer.STATUS_ABLE
		}

		if (!customerInstance.save(flush: true)) {
			render(view: "create", model: [customerInstance: customerInstance])
			return
		}

		if(loginBranch) {
			CustomerBranch.create(customerInstance, loginBranch, null);
		}


		Map<String,CommonsMultipartFile> files = request.multipartFiles
		files.remove("licenseFile")

		List<FileUpload> filesList = new ArrayList<FileUpload>()
		Set<String> keys= files.keySet()
		for(String key:keys) {
			def fileItem = request.getFile(key)

			def itemUuid = UUID.randomUUID().toString()
			def itemDestPath = "${filePath}${itemUuid}"

			fileItem.transferTo(new File(itemDestPath))

			def fileUploadItem = new FileUpload()
			fileUploadItem.fileName   = fileItem.originalFilename
			fileUploadItem.fileUUID =itemUuid
			fileUploadItem.fileUrl = itemDestPath
			fileUploadItem.fromID = customerInstance.id
			fileUploadItem.useOpter = "CUSTOMER"
			fileUploadItem.uploadDate = new Date()
			fileUploadItem.save()
			filesList.add(fileUploadItem)
		}



		flash.message = message(code: 'default.created.message', args: [
			message(code: 'customer.label', default: 'Customer'),
			customerInstance.id
		])
		redirect(action: "show", id: customerInstance.id)
	}
	
	
	def saveD() {		
				def customerInstance = new Customer(params)
						
				def user = springSecurityService.currentUser
				def loginBranch = user.branch
				def msg = null;
				if(loginBranch) {
					  msg = validateBrancLoginD(loginBranch,customerInstance);
				}else {
						msg = validateCustomerD(customerInstance)
				}
				
				if(msg) {
					flash.message = msg;
					render(view: "create", model: [customerInstance: customerInstance])
					return ;
				}
				
				
				def file = request.getFile('licenseFile')
		
				def filePath = grailsApplication.config.file.importer.file.location
				def uuid = UUID.randomUUID().toString()
				def destPath = "${filePath}${uuid}"
		
		
				file.transferTo(new File(destPath))
		
		
				def fileUpload = new FileUpload()
				fileUpload.fileName   = file.originalFilename
				fileUpload.fileUUID =uuid
				fileUpload.fileUrl = destPath
				fileUpload.uploadDate = new Date()
				fileUpload.useOpter = "CUSTOMER"
				fileUpload.save(flush: true)
		
				//def customerInstance = new Customer(params)
				customerInstance.licensePicUrl = fileUpload
				
				customerInstance.customerType = getCustomerTypeLevel3();
		
				//def user = springSecurityService.currentUser
				//def loginBranch = user.branch
		
				if(loginBranch) {
					customerInstance.status = Customer.STATUS_ADD_D
					customerInstance.updateByBranch = loginBranch
					customerInstance.addByBranch = loginBranch
				} else {
					customerInstance.status = Customer.STATUS_ADD_D
				}
		
				
				
		
		
				if (!customerInstance.save(flush: true)) {
					render(view: "create", model: [customerInstance: customerInstance])
					return
				}
		
		
		
				if(loginBranch) {
					CustomerBranch.create(customerInstance, loginBranch, null);
				}
		
		
				Map<String,CommonsMultipartFile> files = request.multipartFiles
				files.remove("licenseFile")
		
				List<FileUpload> filesList = new ArrayList<FileUpload>()
				Set<String> keys= files.keySet()
				for(String key:keys) {
					def fileItem = request.getFile(key)
		
					def itemUuid = UUID.randomUUID().toString()
					def itemDestPath = "${filePath}${itemUuid}"
		
					fileItem.transferTo(new File(itemDestPath))
		
					def fileUploadItem = new FileUpload()
					fileUploadItem.fileName   = fileItem.originalFilename
					fileUploadItem.fileUUID =itemUuid
					fileUploadItem.fileUrl = itemDestPath
					fileUploadItem.fromID = customerInstance.id
					fileUploadItem.useOpter = "CUSTOMER"
					fileUploadItem.uploadDate = new Date()
					fileUploadItem.save()
					filesList.add(fileUploadItem)
				}
		
		
		
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'customer.label', default: 'Customer'),
					customerInstance.id
				])
				redirect(action: "show", id: customerInstance.id)
			}
	
	private def validateCustomer(customerInstance) {
		def tel = customerInstance.tel;
		if(!tel) {
			return  "电话号码不能为空。";
		}
		
		def address = customerInstance.address;
		if(!tel) {
			return  "地址不能为空。";
		}
		
		if(!customerInstance?.name) {
			return "客户名称不能为空！";
		}
		
		 def customer = Customer.findByNameAndStatus(customerInstance?.name,"ABLE");
		 if(customer) {
			 return "已经存在这客户不能新增";
		 }
	}

	private def validateCustomerD(customerInstance) {
		def tel = customerInstance.tel;
		
		if(!customerInstance?.name) {
			return "客户名称不能为空！";
		}
		
		 def customer = Customer.findByNameAndStatus(customerInstance?.name,"ABLE");
		 if(customer) {
			 return "已经存在这客户不能新增";
		 }
	}

	
	def show(Long id) {
		def customerInstance = Customer.get(id)
		if (!customerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'customer.label', default: 'Customer'),
				id
			])
			redirect(action: "list")
			return
		}

		def result = FileUpload.createCriteria().list(max:100, offset:params.offset) {
			eq('fromID', id)
			eq('useOpter', "CUSTOMER")
		}
		
		def user = springSecurityService.currentUser
		def loginBranch = user.branch
		def editable = !loginBranch || customerInstance.status != 'ABLE'

		[customerInstance: customerInstance,files:result, editable:editable]
	}

	
	
	
	def edit(Long id) {
		def customerInstance = Customer.get(id)
		if (!customerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'customer.label', default: 'Customer'),
				id
			])
			redirect(action: "list" )
			return
		}

		def haveNoApproved = Customer.findByUpdateFrom(customerInstance.id)

		if(haveNoApproved||customerInstance.status == Customer.STATUS_ADD || customerInstance.status == Customer.STATUS_UPDATE ||customerInstance.status == Customer.STATUS_DELETE ) {
			flash.message = "这条信息还未审批，不能操作！"
			redirect(action: "show", id: customerInstance.id)
			return
		}


		def user = springSecurityService.currentUser
		def loginBranch = user.branch

		//分公司才进来，并且状态为没删除的
		if(loginBranch && customerInstance.status != Customer.STATUS_DISABLE && customerInstance.status != Customer.STATUS_ADD_D ) {
			def customerNew = new Customer()
			customerNew.properties = customerInstance.properties
			customerNew.id =null;
			customerNew.status = Customer.STATUS_UPDATE
			customerNew.version = 0
			customerNew.updateFrom = customerInstance.id
			customerNew.updateByBranch = loginBranch
			customerInstance = customerNew
			customerInstance.save(flush:true)

			CustomerBranch.create(customerInstance,loginBranch,null)

			//查询所有附件
			def files = FileUpload.findAllByFromIDAndUseOpter(customerNew.updateFrom,"CUSTOMER")
			if(files) {
				files.each { file->
					def newFile =  new FileUpload()
					newFile.properties =  file.properties
					newFile.fromID = customerInstance.id
					newFile.save(flush:true)
				}
			}
		}

		if(customerInstance.status == Customer.STATUS_DISABLE) {
			customerInstance.status = Customer.STATUS_UPDATE
		}else if(customerInstance.status == Customer.STATUS_ADD_D && customerInstance.updateFrom) {
				customerInstance.status = Customer.STATUS_UPDATE
		}else if(customerInstance.status == Customer.STATUS_ADD_D) {
				customerInstance.status = Customer.STATUS_ADD
		}

		

		[customerInstance: customerInstance]
	}


	def update(Long id, Long version) {
		def customerInstance = Customer.get(id)
		if (!customerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'customer.label', default: 'Customer'),
				id
			])
			redirect(action: "list")
			return
		}


		def file = request.getFile('licenseFile')


		if(file && !file.empty) {
			def filePath = grailsApplication.config.file.importer.file.location
			def uuid = UUID.randomUUID().toString()
			def destPath = "${filePath}${uuid}"
			file.transferTo(new File(destPath))

			def fileUpload = customerInstance.licensePicUrl
			if(!fileUpload) {
				fileUpload = new FileUpload()
				fileUpload.fileName   = file.originalFilename
				fileUpload.fileUUID =uuid
				fileUpload.fileUrl = destPath
				fileUpload.uploadDate = new Date()
				fileUpload.useOpter = "CUSTOMER"
				fileUpload.save(flush: true)
				customerInstance.licensePicUrl = fileUpload
			}else {
				fileUpload.fileName   = file.originalFilename
				fileUpload.fileUUID =uuid
				fileUpload.fileUrl = destPath
				fileUpload.uploadDate = new Date()
				fileUpload.useOpter = "CUSTOMER"
				fileUpload.save(flush: true)
			}
		}


		if (version != null) {
			if (customerInstance.version > version) {
				customerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'customer.label', default: 'Customer')] as Object[],
						"Another user has updated this Customer while you were editing")
				//render(view: "edit", model: [customerInstance: customerInstance])
				redirect(action:'show',id:customerInstance.id)
				return
			}
		}

		customerInstance.properties = params
		customerInstance.customerType = getCustomerTypeLevel3();

		def msg = null;
		def tel = customerInstance.tel;
		if(!tel) {
			msg =  "电话号码不能为空。";
		}
		//验证这客户是否已经在数据库存在，并且状态是有效的
		def cstm = Customer.findByNameAndStatus(customerInstance.name,"ABLE");
		def oldCustomerId = cstm?.updateFrom;
		def oldCstm;
		if(oldCustomerId) {
			 oldCstm =Customer.get(oldCustomerId);
		}
		
		if(cstm&&oldCstm&&oldCstm.name!=cstm.name) {
			msg =  "已经有这客户，不能修改这名称。";
		}
		
		//新增需求，以下字段不允许修改
		//license
		//tel
		//address
		//name
		//customerType.id

		
		
		if(msg) {
			flash.message = msg;
		//	render(view: "edit", model: [customerInstance: customerInstance])
			redirect(action:'show',id:customerInstance.id)
			return ;
		}
		
		
		if (!customerInstance.save(flush: true)) {
		//	render(view: "edit", model: [customerInstance: customerInstance])
			redirect(action:'show',id:customerInstance.id)
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'customer.label', default: 'Customer'),
			customerInstance.id
		])
		redirect(action: "show", id: customerInstance.id)
	}

	
	
	def updateD(Long id, Long version) {
		def customerInstance = Customer.get(id)
		if (!customerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'customer.label', default: 'Customer'),
				id
			])
			redirect(action: "list")
			return
		}


		def file = request.getFile('licenseFile')


		if(file && !file.empty) {
			def filePath = grailsApplication.config.file.importer.file.location
			def uuid = UUID.randomUUID().toString()
			def destPath = "${filePath}${uuid}"
			file.transferTo(new File(destPath))

			def fileUpload = customerInstance.licensePicUrl
			if(!fileUpload) {
				fileUpload = new FileUpload()
				fileUpload.fileName   = file.originalFilename
				fileUpload.fileUUID =uuid
				fileUpload.fileUrl = destPath
				fileUpload.uploadDate = new Date()
				fileUpload.useOpter = "CUSTOMER"
				fileUpload.save(flush: true)
				customerInstance.licensePicUrl = fileUpload
			}else {
				fileUpload.fileName   = file.originalFilename
				fileUpload.fileUUID =uuid
				fileUpload.fileUrl = destPath
				fileUpload.uploadDate = new Date()
				fileUpload.useOpter = "CUSTOMER"
				fileUpload.save(flush: true)
			}
		}


		if (version != null) {
			if (customerInstance.version > version) {
				customerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'customer.label', default: 'Customer')] as Object[],
						"Another user has updated this Customer while you were editing")
				render(view: "edit", model: [customerInstance: customerInstance])
				return
			}
		}

		customerInstance.properties = params
		customerInstance.customerType = getCustomerTypeLevel3();
		customerInstance.status = Customer.STATUS_ADD_D;

		def msg = null;
		def tel = customerInstance.tel;
		
		//验证这客户是否已经在数据库存在，并且状态是有效的
		def cstm = Customer.findByNameAndStatus(customerInstance.name,"ABLE");
		if(cstm) {
			msg =  "已经有这客户，不能修改这名称。";
		}
		
		if(msg) {
			flash.message = msg;
			render(view: "edit", model: [customerInstance: customerInstance])
			return ;
		}
		
		
		if (!customerInstance.save(flush: true)) {
			render(view: "edit", model: [customerInstance: customerInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'customer.label', default: 'Customer'),
			customerInstance.id
		])
		redirect(action: "show", id: customerInstance.id)
	}
	
	
	def delete(Long id) {
		def customerInstance = Customer.get(id)


		if (!customerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'customer.label', default: 'Customer'),
				id
			])
			redirect(action: "list")
			return
		}
		
		
		def  salesOrder= SalesOrder.findAllByCustomerAndIsVailAndStatus(customerInstance,true,"ABLE")
		
		if(salesOrder) {
			flash.message = "系统中已经存在该客户的订单信息，不能删除该客户！"
			redirect(action: "show", id: customerInstance.id)
			return
		}
		

		def haveNoApproved = Customer.findByUpdateFrom(customerInstance.id)

		if(haveNoApproved||customerInstance.status == Customer.STATUS_ADD || customerInstance.status == Customer.STATUS_UPDATE ||customerInstance.status == Customer.STATUS_DELETE ) {
			flash.message = "这条信息还未审批，不能操作！"
			redirect(action: "show", id: customerInstance.id)
			return
		}
		
		
		def user = springSecurityService.currentUser
		def loginBranch = user.branch
		
		def customerBranchs= CustomerBranch.findAllByCustomer(customerInstance);
		if(loginBranch && customerBranchs&&  customerBranchs.size>1) {
			
			flash.message = "这个客户还有其他公司在用，不能删除！"
			redirect(action: "show", id: customerInstance.id)
			return
			
		}
		
		

		

		if(loginBranch) {
			
			def customerNew = new Customer()
			customerNew.properties = customerInstance.properties
			customerNew.id =null;
			customerNew.status = Customer.STATUS_DELETE
			customerNew.version = 0
			customerNew.updateFrom = customerInstance.id
			customerNew.updateByBranch = loginBranch;
			customerInstance = customerNew
			customerInstance.save(flush:true)
			
			CustomerBranch.create(customerInstance, loginBranch, null);
			redirect(action: "list")
		} else {

			try {
				CustomerBranch.deleteByCustomer(customerInstance)
				customerInstance.delete(flush: true)
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'customer.label', default: 'Customer'),
					id
				])
				redirect(action: "list")
			}
			catch (DataIntegrityViolationException e) {
				flash.message = message(code: 'default.not.deleted.message', args: [
					message(code: 'customer.label', default: 'Customer'),
					id
				])
				redirect(action: "show", id: id)
			}

		}


	}
	
	
	private def getCustomerTypeLevel3() {
		def type3= params["customerType.id"]
		def type2= params["customerType2ID"]
		def type1= params["customerTypeID"]
		
		if(type3) {
			return CustomerTypeLevel3.get(Long.parseLong(type3));
		}
		
		if(type2) {
			return CustomerTypeLevel2.get(Long.parseLong(type2))?.getNoChildLevel3();
		}
		
		if(type1) {
			return CustomerType.get(Long.parseLong(type1))?.getNoChildLevel2()?.getNoChildLevel3();
		}
	}

	private def createCell(index,value,row) {
		def cell= row.createCell(index, HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}

	private def getStringValue(value) {
		if(value) {
			return value+"";
		}
		
		return "";
	}
}