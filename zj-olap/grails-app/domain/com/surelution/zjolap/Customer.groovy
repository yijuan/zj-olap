package com.surelution.zjolap

class Customer {

	static constraints = {
		name (unique:['status'])
		address nullable:true
		tel nullable:true
		license nullable:true
		licensePicUrl nullable:true
		customerBranch nullable:true
		//approve nullable:true
		updateFrom nullable:true
		customerType nullable:true
		updateByBranch nullable:true
		addByBranch nullable:true
		reason nullable:true
		dateCreated nullable:true
	}

	String name
	CustomerTypeLevel3 customerType
	String address
	String tel
	String license //工商执照
	Branch updateByBranch
	Branch addByBranch
	
	String reason
	
	Long updateFrom 
	

	FileUpload licensePicUrl

	//ADD DELETE ABLE UPDATE VABLE(临时保存)
	String status


	CustomerBranch customerBranch
	
	Date dateCreated

	
	final static	String STATUS_ADD ='ADD'
	final static String STATUS_ADD_D ='ADDD'
	final static	String STATUS_DELETE = 'DELETE'
	final static	String STATUS_UPDATE ='UPDATE'
	final static  String STATUS_ABLE = 'ABLE'
	final static  String STATUS_DISABLE = 'DISABLE'
	
	def getStatusName() {
		if(status == STATUS_ADD) {
			return "新增待审批";
		}else if(status == STATUS_DELETE) {
			return "删除待审批";
		}else if(status == STATUS_UPDATE) {
			return "修改待审批";
		}else if(status == STATUS_ABLE) {
			return "可用";
		}else if(status == STATUS_DISABLE) {
			return "审批驳回";
		}else if(status == STATUS_ADD_D) {
			return "临时保存";
		}
	}
	
	
	

}