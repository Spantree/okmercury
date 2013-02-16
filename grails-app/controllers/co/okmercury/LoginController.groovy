package co.okmercury

class LoginController {
	def login() {
		if(params.user) {
			User user = User.findByEmail(params.user)
			if(!user) {
				user = new User(email: params.user, password: params.password ?: 'password')
			}
			session.user = user
			redirect(uri: '/')
		}
	}
	
	def logout() {
		session.removeAttribute('user')
		redirect(uri: '/login')
	}
}