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
				out << "<div class=\"${cssClass}\">"
					if(match.overallScore > 0.5) {
						out << "<a href=\"/user/${matchUser.id}\">"
						String formattedPercentage = df.format(match.overallScore)
						out << "<img src=\"http://gravatar.com/avatar/${matchUser?.gravatarHash}?s=40&d=identicon\" width=\"40\" height=\"40\"/>"
						out << "${prefix} ${matchUser.name} (${formattedPercentage})!"
						out << "</a>"
					} else {
						out << "Answer more questions to find a match!"
					}
				out << "</div>"
				
			}
		}
	}
}
