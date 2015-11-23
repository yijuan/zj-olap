package com.surelution.zjolap

class ExcelUpload {

    static constraints = {
    }
	
	static mapping = {
		deleted nullable:true
		month nullable:true
	}
	
	User user
	Date uploadedAt
	String originalFileName
	String filePath
	boolean deleted
	
	FinancialMonth month 
	
	
}
