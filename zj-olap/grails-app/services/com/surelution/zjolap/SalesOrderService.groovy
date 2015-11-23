package com.surelution.zjolap

class SalesOrderService {

	boolean transactional = true
    public def changeSalesOrderIsClose(operMonth,branchId,fmYear,option) {
		def hql = " select so from SalesOrder as so  "
		if(fmYear) {
			hql +=" inner join so.month as m with m.year ="+fmYear

			if(operMonth) {
				hql +=" and m.month =" + operMonth;
			}
		}
		if(branchId) {
			hql += " inner join so.branch b with b.id ="+branchId
		}

		hql += " where  so.isVail = true ";

		def result = SalesOrder.executeQuery(hql);

		result.each {
			it.isClosed =option
			//it.save(true);
		}
		
		SalesOrder.saveAll(result)

	}
}
