package co.okmercury

import grails.plugin.cache.Cacheable

class ImportanceService {
	@Cacheable('importanceByName')
	Importance getImportanceByName(String name) {
		for(Importance importance : Importance.class.getEnumConstants()) {
			if(importance.name() == name) {
				return importance
			}
		}
	}
}
