package com.surelution.zjolap

class DeliveryTakingUpload extends ExcelUpload {

    static constraints = {
    }
	static mapping = {
		discriminator "delivery"
	}
}
