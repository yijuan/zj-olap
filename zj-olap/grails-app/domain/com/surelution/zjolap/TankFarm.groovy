package com.surelution.zjolap

class TankFarm {

    static constraints = {
    }
	
	String name
	
	public static TankFarm findOrCreate(String name) {
		def farm = TankFarm.findByName(name)
		if(!farm) {
			farm = new TankFarm()
			farm.name = name
			farm.save(flush:true)
		}
		farm
	} 
}
