package co.okmercury

import grails.plugins.springsecurity.SpringSecurityService

import org.bson.types.ObjectId
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserDetailsService
import org.springframework.dao.DataAccessException
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

import co.okmercury.security.UserAlreadyExistsException

import com.synergyj.grails.plugins.avatar.util.MD5Util

class UserService implements GrailsUserDetailsService {
	SpringSecurityService springSecurityService
	
	// @Cacheable('email')
	User getUserByEmail(String email) {
		User.findByEmail(email)
	}
	
	// @Cacheable('email')
	User getById(ObjectId id) {
		User.get(id)
	}
	
	User createUser(
		String email, String password,
		String firstName, String lastName,
		String jobTitle, String companyName,
		String gravatarHash = null,
		List<String> roles = ['user']
	) throws UserAlreadyExistsException {
		if(User.findByEmail(email)) {
			throw new UserAlreadyExistsException(email)
		}
		
		if(!gravatarHash) {
			gravatarHash = MD5Util.md5Hex(email)
		}
		
		String encodedPassword = springSecurityService.encodePassword(password, email)
		
		User user = new User(
			email: email,
			password: encodedPassword,
			firstName: firstName,
			lastName: lastName,
			jobTitle: jobTitle,
			companyName: companyName,
			gravatarHash: gravatarHash,
			roles: roles
		)
		user.save(flush: true)
		
		return user
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
		User user = User.findByEmail(email)
		if(!user) {
			throw new UsernameNotFoundException(email)
		}
		return createUserDetails(user)
	}

	@Override
	public UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException, DataAccessException {
		User user = User.findByEmail(email)
		if(!user) {
			throw new UsernameNotFoundException(email)
		}
		return createUserDetails(user)
	}
	
	public UserDetails createUserDetails(User user) {
		return new GrailsUser(
			user.email,
			user.password,
			true,
			false,
			false,
			false,
			user.roles?.collect { new GrantedAuthorityImpl(it) } ?: [new GrantedAuthorityImpl('user')]
		)
	}
}
