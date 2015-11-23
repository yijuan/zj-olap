package com.surelution.zjolap


class CustomerStockService {
	boolean transactional = true
	
    def serviceUploadCustomerStocks(List<CustomerStock> cs,StockExcelUpload upload) {
		
		upload.save(flush:true)
		
		for(int i=0;i<cs.size();i++) {
			//1.插入 CustomerStock
			CustomerStock customerstock= cs.get(i);
			customerstock.upload = upload;
			customerstock.save(flush:true);
			
			
		
			//2.插入CustomerStockLog
			CustomerStockLog csLog = new CustomerStockLog()
			//csLog.properties = customerstock.properties;
			
			csLog.customerBranch = customerstock.customerBranch;
			csLog.stockQty = customerstock.stockQty;
			csLog.date = customerstock.date;
			csLog.gasType = customerstock.gasType;
			csLog.status = customerstock.status;
			
			csLog.updateFrom = customerstock;
			csLog.optionType = CustomerStockLog.OPTION_VALUE_EXCEL_ADD;
			
			csLog.save(flush:true);
			
			println "size:" +cs.size();
			println "currentNum:" +i;
		}
		
    }
	
	
	public def changeCustomerStockIsClose(operMonth,branchId,fmYear,option) {
		def hql = " select so from CustomerStock as so  "
		if(fmYear) {
			hql +=" inner join so.date as m with m.year ="+fmYear

			if(operMonth) {
				hql +=" and m.month =" + operMonth;
			}
		}
		if(branchId) {
			hql += " inner join so.customerBranch as cb inner join  cb.branch as b with b.id ="+branchId
		}

		hql += " where  so.isVail = true ";

		def result = CustomerStock.executeQuery(hql);

		result.each {
			it.isClosed =option
			//it.save(true);
		}
		CustomerStock.saveAll(result)

	}
	
}
