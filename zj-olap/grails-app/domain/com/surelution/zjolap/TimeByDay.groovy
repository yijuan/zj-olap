package com.surelution.zjolap

import grails.converters.JSON

/**
 * 财务时间表
 * 为了方便根据维度时间查询相关销售数据、支持财务月度功能。
 * 财务月度有别于日历月度，如2013-9-29日的日历月度为9月，财务月度可能为10月。
 * 为销售表等事实表提供时间
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class TimeByDay {

    static constraints = {
		year(unique:['month', 'day'])
    }
    
    static mapping = {
        date column:'c_date'
    }
    
    /**
     * 日历日期，精度到‘天’，这个‘天’可能与事实表的‘天’有所不同，比如事实表中每天下午4点钟，
     * 当天结束，到下午4点后，算到第二天，那么2013-09-11 17:32:43可能对应的时间是2013-09-12
     */
    Date date
    Integer year
    Integer month
    Integer day
    Integer quarter
	
	public static TimeByDay findOrCreate(int year, int month, int day) {
		def cal = Calendar.instance
//		cal.setTime(date)
//		int year = cal.get(Calendar.YEAR)
//		int month = cal.get(Calendar.MONTH)
//		int day = cal.get(Calendar.DAY_OF_MONTH)
		def d = findByYearAndMonthAndDay(year, month+1, day)
		if(!d) {
			d = new TimeByDay()
			d.year = year
			d.month = month +1
			d.day = day
			d.quarter = (int)((month)/3 + 1)
//			cal.clear()
			cal.set(Calendar.YEAR, year)
			cal.set(Calendar.MONTH, month)
			cal.set(Calendar.DAY_OF_MONTH, day)
			d.date = cal.getTime()
			d.save(flush:true)
		}
		
		return d
	}
	
	
	
	
	static def getDistinctYear() {
		def result = new ArrayList<FinancialMonth>();
		
		def re = TimeByDay.createCriteria().list(){
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
	
	
	
	static def getMonthCustomerStock(Integer year,Branch branch) {
		def rea = SalesOrder.executeQuery("select distinct so.isClosed, m.month from CustomerStock as so inner join so.date as m with m.year =? inner join so.customerBranch as cb inner join cb.branch as b with b.id =? where  so.isVail = true", [year, branch.id]);
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
