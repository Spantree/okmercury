package co.okmercury.security

import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionData
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.ConnectionSignUp
import org.springframework.social.connect.UsersConnectionRepository

import co.okmercury.User
import co.okmercury.MongoUserConnection

class MongoGormUsersConnectionRepository implements UsersConnectionRepository {
	ConnectionFactoryLocator connectionFactoryLocator
	TextEncryptor textEncryptor
	ConnectionSignUp connectionSignUp
	
	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		if(!userId) throw new IllegalArgumentException("userId cannot be null");
		return new MongoGormConnectionRepository(userId, connectionFactoryLocator, textEncryptor)
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String provId, Set<String> provUserIds) {
		Set<String> returnVal = new HashSet<String>()
		for(String providerUserId : provUserIds) {
			returnVal.add(MongoUserConnection.findByProviderIdAndProviderUserId(provId, provUserIds).userId)
		}
		return returnVal
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		ConnectionData data = connection.createData()
		List mucs = MongoUserConnection.findAllbyProviderIdAndProviderUserId(data.providerId, data.providerUserId)
		List<String> returnVal = new LinkedList<String>()
		for(MongoUserConnection muc : mucs) {
			returnVal.add(muc.userId)
		}
		return returnVal
	}

}
