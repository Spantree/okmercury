package co.okmercury

import grails.plugins.springsecurity.SpringSecurityService
import co.okmercury.security.UserAlreadyExistsException

class LoginController {
	SpringSecurityService springSecurityService
	UserService userService
	GravatarService gravatarService
	
	def login() {
		String messageKey = null
		log.info "Attempting to log in user"
		if(params.user && params.pass) {
			User user = User.findByEmail(params.user)
			if(!user) {
				messageKey = 'user.email.not.found'
			} else {
				String passwordHash = springSecurityService.encodePassword(params.pass, user.email)
				if(false || passwordHash != user.password) {
					messageKey = 'user.password.not.valid'
				} else {
					session.user = user
					redirect(uri: '/')
				}
			}
		}
		[messageKey: messageKey]
	}
	
	def logout() {
		session.removeAttribute('user')
		redirect(uri: '/login')
	}
	
	def register() {
		String messageKey = null
		boolean created = false
		if(params.user) {
			if(!(params.firstName && params.user && params.pass)) {
				messageKey = 'user.name.and.pass.required'
			} else if(!params.confirmPass || params.pass != params.confirmPass) {
				messageKey = 'user.password.not.match'
			} else {
				try {
					log.info "Creating new user account for ${params.user}"
					User user = userService.createUser(
						params.user,
						params.pass,
						params.firstName,
						params.lastName,
						params.jobTitle,
						params.companyName
					)
					session.user = user
					redirect(uri: "/user/${session.user.id}/created")
				} catch(UserAlreadyExistsException e) {
					messageKey = 'user.already.exists'
				}
			}
		}
		[messageKey: messageKey, created: created]
	}

	def created() {
		boolean gravatarExists = gravatarService.existsForEmail(session.user.email)
		if(!gravatarExists) {
			redirect(uri: "/user/${session.user.id}/gravatar/prompt")
		}
		[:]
	}

	def bcryptTest() {
		StringBuffer sb = new StringBuffer()
		20.times {
			sb.append "${springSecurityService.encodePassword('hello world', 'foo')}<br/>"
		}
		20.times {
			sb.append "${springSecurityService.encodePassword('hello world', 'bar')}<br/>"
		}
		render sb.toString()
	}
}