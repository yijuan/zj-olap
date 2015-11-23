package com.surelution.zjolap

import java.util.Date;

class CustomerStock {
	CustomerBranch customerBranch
	double stockQty
	TimeByDay date
	GasType gasType
	long excelRowIndex
	boolean isClosed;
	Date cdate
	StockExcelUpload upload;
	
	Long updateFrom

    static constraints = {
		isClosed default:false
		excelRowIndex nullable:true
		updateFrom nullable:true
		upload nullable:true
    }
	
	boolean isVail
	String status	
	
	
	final static	String STATUS_ADD ='ADD'
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
		}
	}
}
