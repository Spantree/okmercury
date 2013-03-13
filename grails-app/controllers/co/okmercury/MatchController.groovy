package co.okmercury

import grails.plugins.springsecurity.Secured
import com.mongodb.DBObject

class MatchController {
	MatchService matchService
	UserService userService
	def springSecurityService
	
	
	@Secured(['ROLE_USER'])
	def showMatches() {
		List matches = matchService.getBestMatchesForUser(springSecurityService.getCurrentUser(), params.sort)
		Map userMap = [:]
		matches.each { DBObject obj ->
			userMap[obj.matchUserId] = userService.getById(obj.matchUserId)
		}
		
		def model = [matches: matches, userMap: userMap]
		render(view: 'list.gsp', model: model)
	}
}
