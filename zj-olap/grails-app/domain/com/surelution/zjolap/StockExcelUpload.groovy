package com.surelution.zjolap

class StockExcelUpload {

    static constraints = {
    }
	
	static mapping = {
		deleted nullable:true
		
	}
	
	User user
	Date uploadedAt
	String originalFileName
	String filePath
	boolean deleted

	
}
