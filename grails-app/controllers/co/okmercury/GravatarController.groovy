package co.okmercury

import grails.plugins.springsecurity.Secured

class GravatarController {
	GravatarService gravatarService
	def springSecurityService

	def prompt() {
		[user : springSecurityService.getCurrentUser()]
	}

	@Secured(['ROLE_USER'])
	def signup() {
		User user = springSecurityService.getCurrentUser()
		boolean success = gravatarService.signup(user.email)
		[success: success, user: user]
	}
}