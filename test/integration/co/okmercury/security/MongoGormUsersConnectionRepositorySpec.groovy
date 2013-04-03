package co.okmercury.security

import grails.plugin.spock.IntegrationSpec

import org.apache.commons.lang.RandomStringUtils
import org.springframework.social.connect.UsersConnectionRepository

import co.okmercury.User

class MongoGormUsersConnectionRepositorySpec extends IntegrationSpec {
	String username = RandomStringUtils.random(10)
	String twitterId = RandomStringUtils.random(10)
	UsersConnectionRepository usersConnectionRepository
	
	User user
	
	def setup() {
		user = new User(username: username)
		user.socialConnections.twitterId = twitterId
		user.save(flush: true)
	}
	
	def cleanup() {
		user.delete(flush: true)
	}
	
	def "should insert social connections into database"() {
		when:
			User newUser = User.findByUsername(username)
		then:
			println newUser
	}
	
	def "should find user by twitterId"() {
		when:
			Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo('twitter', [twitterId] as Set)
		then:
			userIds
	}
}
