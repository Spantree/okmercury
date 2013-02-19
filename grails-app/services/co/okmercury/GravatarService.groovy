package co.okmercury

import groovyx.net.http.HTTPBuilder

import com.synergyj.grails.plugins.avatar.util.MD5Util

class GravatarService {
	HTTPBuilder avatarHttp = new HTTPBuilder("http://www.gravatar.com/")
	HTTPBuilder signupHttp = new HTTPBuilder("https://en.gravatar.com/accounts/signup")
	
	boolean existsForEmail(String email) {
		String hash = MD5Util.md5Hex(email.trim())
		return existsForHash(hash)
	}

	boolean existsForHash(String hash) {
		log.info "Checking validity of gravatar hash ${hash}"
		avatarHttp.request(groovyx.net.http.Method.GET) { req ->
			uri.path = "/avatar/${hash}"
			uri.query = [d: '404']

			response.success = { resp, reader ->
				return true
			}

			response.'404' = {
				return false
			}
		}
	}

	boolean signup(String email) {
		log.info "Signup ${email} up for gravatar"
		signupHttp.request(groovyx.net.http.Method.POST) { req ->
			uri.path = "/accounts/signup"
			uri.query = [email: email, commit: 'Signup']

			response.success = { resp, reader ->
				return true
			}

			response.failure = {
				return false
			}
		}
	}
}
