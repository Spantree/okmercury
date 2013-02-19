package co.okmercury

class UserController {
	def profile() {
		User user = User.get(params.userId)
		
		List<Answer> answers = Answer.where {
			user == user
			skipped != true
		}.list()
		
		int numberSkipped = Answer.where {
			user == user
			skipped == true
		}.count()
		
		[
			user: user,
			answers: answers,
			numberSkipped: numberSkipped
		]
	}
}