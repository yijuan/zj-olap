package com.surelution.zjolap

import grails.converters.JSON;

class FinancialMonth {

    static constraints = {
		year unique:['month']
    }
	
	static mapping = {
	}

	Integer year
	Integer quarter
	Integer month
	
	
	static def getDistinctYear() {
		def result = new ArrayList<FinancialMonth>();
		
		def re = FinancialMonth.createCriteria().list(){
			projections {
				distinct("year")
			}
		};
		
		re.each {
			def fm = new FinancialMonth()
			fm.year = it;
			result.add(fm);
		}
		
		
	
		return result;
	}
	
	
	
	static def getFinancialMonthFromSalesOrder(Integer year,Branch branch) {
	
		 def rea = SalesOrder.executeQuery("select distinct so.isClosed, m.month from SalesOrder as so inner join so.month as m with m.year ="+year+" inner join so.branch b with b.id ="+branch.id +" where  so.isVail = true ");
		List list = new ArrayList()
		rea.each {
			def map = new HashMap();
			map.put("isClosed", it[0]);
			map.put("month", it[1]);
			list.add(map);
		 }
	   	list as JSON
	}
}
