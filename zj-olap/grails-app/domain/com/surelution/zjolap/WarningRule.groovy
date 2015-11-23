package com.surelution.zjolap

class WarningRule {

	
	String type
	Double value
	GasType gasType
	Branch branch

	def getTypeName() {
		if(type =="baseStockQtyWarning") {
			return "保底客存警告"
		}
		if(type =="daysStockQtyWarning") {
			return "超过天数提油警告"
		}
		if(type =="daysSaleOrderQtyWarning") {
			return "超过天数购油警告"
		}
	}
	
	public static List getTypes() {
//		List list = new ArrayList();
//		Map map1 = new HashMap();
//		map1.put("type", "baseStockQtyWarning")
//		map1.put("typeName", "保底客存警告");
//		list.add(map1);
//		
//		Map map2 = new HashMap();
//		map2.put("type", "daysStockQtyWarning")
//		map2.put("typeName", "超过天数提油警告");
//		list.add(map2);
		[[type : "baseStockQtyWarning", typeName : "保底客存警告"], 
			[type : "daysStockQtyWarning", typeName : "超过天数提油警告"], 
			[type : "daysSaleOrderQtyWarning", typeName : "超过天数购油警告"]]
//		return list
		
	}

	public static getListByBranch(branchId) {
		 def result= WarningRule.createCriteria().list(max:1000000000, offset:0) {
			if(branchId&&branchId !="null") {
				branch {
					eq("id",Long.parseLong(branchId))
				}
			}
		}
		 
		return result
	}
}
