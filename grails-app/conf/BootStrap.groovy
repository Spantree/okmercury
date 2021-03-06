import co.okmercury.Role
import co.okmercury.User

class BootStrap {
	def grailsApplication

    def init = { servletContext ->
		
			log.info "Creating admin user"
			def userRole = new Role(authority:"ROLE_USER").save(flush:true)
			String email = "admin@okmercury.co"
			def user = User.findByEmail(email) ?: new User()
			user.username = email
			user.plainTextPassword = "password"
			user.email = user.username
			user.hasPassword = true
			user.authorities = ["ROLE_USER", "ROLE_ADMIN"]
			user.save(flush:true)
			println user.errors.allErrors
    }
    def destroy = {
    }
}
