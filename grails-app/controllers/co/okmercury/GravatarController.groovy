package co.okmercury

class GravatarController {
	GravatarService gravatarService

	def prompt() {
		[:]
	}

	def signup() {
		boolean success = gravatarService.signup(session.user.email)
		[success: success]
	}
}