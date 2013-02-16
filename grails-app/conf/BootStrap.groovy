import co.okmercury.User

class BootStrap {

    def init = { servletContext ->
		[
			[firstName: "Cedric", lastName: "Hurst", email: "cedric"],
			[firstName: "Gary", lastName: "Turovsky", email: "gary"],
			[firstName: "Jane", lastName: "Smith", email: "jane"],
			[firstName: "John", lastName: "Doe", email: "john"]
		].each { Map props ->
			User user = User.findByEmail(props.email)
			if(!user) {
				user = new User(emails: props.email)
			}
			user.firstName = props.firstName
			user.lastName = props.lastName
			user.passwordHash = "password"
			user.save(flush: true)
			if(user.errors.hasErrors()) {
				log.error "Error occurred saving user ${props.email}: ${user.errors.allErrors}"
			} else {
				log.error "Successfully updates user ${props.email}: ${user.errors.allErrors}"
			}
		}
    }
    def destroy = {
    }
}
