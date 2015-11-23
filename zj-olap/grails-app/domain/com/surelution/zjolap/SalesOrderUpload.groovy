package com.surelution.zjolap

import org.apache.ivy.core.module.descriptor.ExtendsDescriptor;


class SalesOrderUpload extends ExcelUpload {

    static constraints = {
    }
	static mapping = {
		discriminator "salesOrder"
	}
}
