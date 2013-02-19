import co.okmercury.User
import co.okmercury.UserService

class userService {
	def grailsApplication

    def init = { servletContext ->
		User user = User.findByEmail('admin@okmercury.co')
		if(!user) {
			log.info "Creating admin user"
			UserService userService = grailsApplication.mainContext.getBean('userService')
			userService.createUser(
				'admin@okmercury.co',
				'password',
				'Administrator',
				'of OKMercury',
				'Administrator',
				'OKMercury'
			)
		}
    }
    def destroy = {
    }
}
