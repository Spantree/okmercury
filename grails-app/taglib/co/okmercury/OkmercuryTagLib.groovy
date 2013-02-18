package co.okmercury

import java.text.DecimalFormat

import com.mongodb.DBObject

class OkmercuryTagLib {
	static namespace = 'okm'
	
	MatchService matchService
	UserService userService
	DecimalFormat df = new DecimalFormat("###,##%");
	
	def bestMatch = { attrs, body ->
		User user = session.user
		String prefix = attrs.prefix ?: 'You should meet'
		String cssClass = attrs.cssClass ?: ''
		if(user) {
			DBObject match = matchService.getBestMatchForUser(user)
			
			if(match) {
				User matchUser = User.get(match.matchUserId)
				String matchName = matchUser.firstName ?: matchUser.email
				if(match.overallScore > 0.5) {
					String formattedPercentage = df.format(match.overallScore)
					out << "<span class=\"${cssClass}\">${prefix} ${matchName} (${formattedPercentage})!</span>"
				}
			}
		}
	}
}
