package com.surelution.zjolap

class BranchAlias {

    static constraints = {
		branch nullable:true
		aliasName unique:true 
    }
	
	Branch branch
	String aliasName
	
	static BranchAlias findOrCreate(String name) {
		def alias = BranchAlias.findByAliasName(name)
		if(!alias) {
			alias = new BranchAlias()
			alias.aliasName = name
			alias.save(flush:true)
		}
		alias
	}
}
