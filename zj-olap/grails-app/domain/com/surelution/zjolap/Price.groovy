package com.surelution.zjolap

class Price {
	
	private static HashMap<GasType, ArrayList<Price>> prices

    static constraints = {
    }
	
	static mapping = {
		from column:'_from'
	}
	
	GasType type
	Date from
	Date dateCreated
	Float price
	
	public static void updateCache() {
		prices = new ArrayList<GasType, ArrayList<Price>>()
		GasType.list().each {type->
			def ps = Price.createCriteria().list { 
				eq('type', type)
				order('from', 'desc')
			}
			if(ps) {
				prices.put(type.id, ps)
			}
		}
	}

	public static Float findPrice(GasType type, Date date) throws LackOfPriceException {
		if(prices) {
			def ps = prices[type.id]
			def p = ps?.find {
				it.from < date
			}
			if(p) {
				return p.price
			}
		}
		throw new LackOfPriceException()
	}
}
