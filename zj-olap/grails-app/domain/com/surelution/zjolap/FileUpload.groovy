package com.surelution.zjolap

class FileUpload {

	String fileName
	String fileUUID
	String fileUrl
	
	Long fromID 
	String useOpter
	Date uploadDate
    static constraints = {
		fromID nullable:true
    }
}
