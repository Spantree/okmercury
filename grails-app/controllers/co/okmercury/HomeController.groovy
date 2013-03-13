package co.okmercury

import grails.plugins.springsecurity.Secured

class HomeController {
	
	def springSecurityService
	
	@Secured(['ROLE_USER'])
	def index() {
		[user : springSecurityService.getCurrentUser()]
	}
}
