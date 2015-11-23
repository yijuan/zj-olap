package com.surelution.zjolap

class GasTypeAlias {

    static constraints = {
		gasType nullable:true
		aliasName unique:true 
    }
	
	GasType gasType
	String aliasName
	
	public static GasTypeAlias findOrCreate(String name) {
		def gta = GasTypeAlias.findByAliasName(name)
		if(!gta) {
			gta = new GasTypeAlias()
			gta.aliasName = name
			gta.save(flush:true)
		}
		gta
	}
}
