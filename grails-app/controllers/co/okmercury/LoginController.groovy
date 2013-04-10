package co.okmercury

import grails.plugins.springsecurity.Secured
import grails.plugins.springsocial.connect.web.GrailsConnectSupport
import org.springframework.social.connect.ConnectionData
import org.springframework.social.connect.UserProfile
import org.springframework.social.connect.web.ProviderSignInAttempt
import co.okmercury.security.UserAlreadyExistsException
import grails.converters.JSON

import org.apache.commons.lang.RandomStringUtils;


import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.twitter.api.Twitter;

class LoginController {

	/**
	 * Dependency injection for the authenticationTrustResolver.
	 */
	def authenticationTrustResolver

	/**
	 * Dependency injection for the springSecurityService.
	 */
	def springSecurityService
	
	/**
	 * Dependency for created()
	 */
	GravatarService gravatarService
	
	/**
	 * Dependency for oauthCallback()
	 */
	GrailsConnectSupport webSupport = new GrailsConnectSupport(mapping: "springSocialRegister")
	Twitter twitter
	Facebook facebook

	/**
	 * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
	 */
	def index = {
		if (springSecurityService.isLoggedIn()) {
			redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
		}
		else {
			redirect action: 'auth', params: params
		}
	}

	/**
	 * Show the login page.
	 */
	def auth = {
		
		String messageKey = null
		
		if(params.login_error) {
			if(params.login_error == '1') messageKey = 'login.denied'
		}

		
		
		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}

		if(params.j_username) {
			if(User.findByEmail(params.j_username)?.hasPassword) {
				redirect(uri: "${request.contextPath}${config.apf.filterProcessesUrl}", params: params)
			} else {
				if(User.findByEmail(params.j_username).facebookId) {
					messageKey = 'login.social.facebook'
				} else if(User.findByEmail(params.j_username).twitterId) {
					messageKey = 'login.social.twitter'
				} else {
					messageKey = 'login.social'
				}
			} 
		}
		
		String view = 'login'
		String postUrl = ""
		render view: view, model: [postUrl: postUrl,
		                           rememberMeParameter: config.rememberMe.parameter,
								   messageKey: messageKey,
								   created: false]
	}

	/**
	 * The redirect action for Ajax requests.
	 */
	def authAjax = {
		response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
		response.sendError HttpServletResponse.SC_UNAUTHORIZED
	}

	/**
	 * Show denied page.
	 */
	def denied = {
		if (springSecurityService.isLoggedIn() &&
				authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
			// have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
			redirect action: 'full', params: params
		}
	}

	/**
	 * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
	 */
	def full = {
		def config = SpringSecurityUtils.securityConfig
		render view: 'login', params: params,
			model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
			        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
	}

	/**
	 * Callback after a failed login. Redirects to the auth page with a warning message.
	 */
	def authfail = {

		def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
		String msg = ''
		def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
		if (exception) {
			if (exception instanceof AccountExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.expired")
			}
			else if (exception instanceof CredentialsExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.passwordExpired")
			}
			else if (exception instanceof DisabledException) {
				msg = g.message(code: "springSecurity.errors.login.disabled")
			}
			else if (exception instanceof LockedException) {
				msg = g.message(code: "springSecurity.errors.login.locked")
			}
			else {
				msg = g.message(code: "springSecurity.errors.login.fail")
			}
		}

		if (springSecurityService.isAjax(request)) {
			render([error: msg] as JSON)
		}
		else {
			flash.message = msg
			redirect action: 'auth', params: params
		}
	}

	/**
	 * The Ajax success redirect url.
	 */
	def ajaxSuccess = {
		render([success: true, username: springSecurityService.authentication.name] as JSON)
	}

	/**
	 * The Ajax denied redirect url.
	 */
	def ajaxDenied = {
		render([error: 'access denied'] as JSON)
	}
	
	/**
	 * Register a new user
	 */
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
					User user = new User();
					
					// Check if user is in database
					if(User.findByEmail(params.user)) {
						throw new UserAlreadyExistsException(params.user)
					}
					
					// Update user info
					user.username = params.user
					user.password = params.pass
					user.firstName = params.firstName
					user.lastName = params.lastName
					user.email = user.username
					user.authorities = ["ROLE_USER"]
					user.jobTitle = params.jobTitle
					user.companyName = params.companyName
					user.hasPassword = true
					created = user.save(flush:true)
					if(created) {
						springSecurityService.reauthenticate(user.username)
						redirect(uri: "/user/${springSecurityService.getCurrentUser().id}/created")
					} else {
						log.warn "DB Error: " + user.errors.allErrors
						messageKey = 'user.db.error'
						if(user.password.length() < 6) messageKey = 'user.password.invalid'
					}
				} catch(UserAlreadyExistsException e) {
					messageKey = 'user.already.exists'
				}
			}
		}
		[messageKey: messageKey, created: created]
	}
	
	@Secured(['ROLE_USER'])
	def created() {
		User user = springSecurityService.getCurrentUser()
		boolean gravatarExists = gravatarService.existsForEmail(user.email)
		if(!gravatarExists) {
			redirect(uri: "/user/${user.id}/gravatar/prompt")
		}
		[user : user]
	}
	
	/**
	 * Redirect OAuth User to Registration
	 * @return
	 */
	def oauthCallback() {
		ProviderSignInAttempt attempt = session[ProviderSignInAttempt.SESSION_ATTRIBUTE]
		UserProfile profile = attempt.connection.fetchUserProfile()
		ConnectionData data = attempt.connection.createData()
		
		String messageKey = null
		boolean created = false
		
		if(params.user) {
			if(!(params.firstName && params.user)) {
				messageKey = 'user.name.and.pass.required'
			} else {
				try {
					log.info "Creating new ${params.provider} user account for ${params.user}"
					User user = new User();
					
					// Check if user is in database
					if(User.findByEmail(params.user)) {
						throw new UserAlreadyExistsException(params.user)
					}
					
					// Update user info
					user.username = params.user
					user.password = RandomStringUtils.random(32, true, true)
					user.firstName = params.firstName
					user.lastName = params.lastName
					user.email = user.username
					user.authorities = ["ROLE_USER"]
					user.jobTitle = params.jobTitle
					user.companyName = params.companyName
					user.hasPassword = false
					
					if(params.provider == "facebook") {
						user.facebookId = params.username
					} else if(params.provider == "twitter") {
						user.twitterId = params.username
					} else {
						redirect(uri: "/user/register")
					}
					
					created = user.save(flush:true)
					if(created) {
						springSecurityService.reauthenticate(user.username)
						redirect(uri: "/user/${springSecurityService.getCurrentUser().id}/created")
					} else {
						log.warn "DB Error: " + user.errors.allErrors
						messageKey = 'user.db.error'
						if(user.password.length() < 6) messageKey = 'user.password.invalid'
					}
				} catch(UserAlreadyExistsException e) {
					messageKey = 'user.already.exists'
				}
			}
		} else {
			if(data.getProviderId() == "facebook") {
				User user = User.findByEmail(profile.email)
				if(user) {
					springSecurityService.reauthenticate(user.username)
					if(user.facebookId) {
						redirect(uri: "/")
					} else {
						user.facebookId = profile.username
						user.save(flush: true)
						redirect(uri: "/home/facebook")
					}
				} else {
					user = User.findByFacebookId(profile.username)
					if(user) {
						springSecurityService.reauthenticate(user.username)
						redirect(uri: "/")
					} else {
						return [provider: data.getProviderId(), profile: profile]
					}
				}
			} else if(data.getProviderId() == "twitter") {
				render "firstName: ${profile.firstName}, lastName: ${profile.lastName}, \
					email: ${profile.email}, username: ${profile.username}, \
					provider: ${data.getProviderId()}"
			} else {
				redirect(uri: "/user/register")
			}
		}
		[messageKey: messageKey, created: created]
	}

}
