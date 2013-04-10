package co.okmercury

import grails.plugins.springsecurity.Secured

class HomeController {
	
	def springSecurityService
	
	@Secured(['ROLE_USER'])
	def index() {
		if(!params.provider) {
			[user : springSecurityService.getCurrentUser()]
		} else {
			
			[user : springSecurityService.getCurrentUser(), oauth: params.provider]
		}
	}
}
