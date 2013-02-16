package co.okmercury

class HomeController {
	def index() {
		if(!session.userEmail) {
			redirect(uri: '/login')
		}
		[:]
	}
}
