package co.okmercury

import com.mongodb.DBObject

class MatchController {
	MatchService matchService
	UserService userService
	
	def showMatches() {
		List matches = matchService.getBestMatchesForUser(session.user)
		Map userMap = [:]
		matches.each { DBObject obj ->
			userMap[obj.matchUserId] = userService.getById(obj.matchUserId)
		}
		
		def model = [matches: matches, userMap: userMap]
		render(view: 'list.gsp', model: model)
	}
}
