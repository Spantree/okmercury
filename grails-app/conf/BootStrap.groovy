import co.okmercury.User

class BootStrap {

    def init = { servletContext ->
		[
			[firstName: "Cedric", lastName: "Hurst", email: "cedric", gravatarHash: "22c2f4465298a422b5307c0ec5a48442"],
			[firstName: "Gary", lastName: "Turovsky", email: "gary", gravatarHash: "0bdee349222a510f27abd7ebe84c6e00"],
			[firstName: "Jane", lastName: "Smith", email: "jane", gravatarHash: "c87a84cf3b06077036deade1b44aa45e"],
			[firstName: "John", lastName: "Doe", email: "john", gravatarHash: "25c7c18223fb42a4c6ae1c8db6f50f9b"],
			[firstName: "Paul", lastName: "Barry", email: "paul", gravatarHash: "6661ef9d747db3af8896cd94959d717d"],
			[firstName: "Kim", lastName: "Bekkelund", email: "kim", gravatarHash: "6c51c14716e24bc1f1a3fb5ad234e773"],
			[firstName: "Rebecca", lastName: "Murphey", email: "rebecca", gravatarHash: "0177cdce6af15e10db15b6bf5dc4e0b0"]
		].each { Map props ->
			User user = User.findByEmail(props.email)
			if(!user) {
				user = new User(emails: props.email)
			}
			user.firstName = props.firstName
			user.lastName = props.lastName
			user.passwordHash = "password"
			user.gravatarHash = props.gravatarHash
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
