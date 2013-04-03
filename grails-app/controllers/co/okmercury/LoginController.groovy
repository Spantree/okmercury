package co.okmercury

import grails.plugins.springsecurity.Secured
import grails.plugins.springsocial.connect.web.GrailsConnectSupport
import org.springframework.social.connect.UserProfile
import org.springframework.social.connect.web.ProviderSignInAttempt
import co.okmercury.security.UserAlreadyExistsException
import grails.converters.JSON


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

		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}

		String view = 'login'
		String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
		render view: view, model: [postUrl: postUrl,
		                           rememberMeParameter: config.rememberMe.parameter]
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
					created = user.save(flush:true)
					if(created) {
						springSecurityService.reauthenticate(user.username)
						redirect(uri: "/user/${springSecurityService.getCurrentUser().id}/created")
					} else messageKey = 'user.db.error'
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
	 * Register a new OAuth User
	 * @return
	 */
	def oauthCallback() {
		ProviderSignInAttempt attempt = session[ProviderSignInAttempt.SESSION_ATTRIBUTE]
		UserProfile profile = attempt.connection.fetchUserProfile()
		render "firstName: ${profile.firstName}, lastName: ${profile.lastName}"
	}

}
