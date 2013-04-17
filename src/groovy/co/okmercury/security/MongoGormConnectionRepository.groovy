package co.okmercury.security

import co.okmercury.MongoUserConnection
import java.util.List
import java.util.Map.Entry;

import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException
import org.springframework.social.connect.NoSuchConnectionException
import org.springframework.social.connect.NotConnectedException
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap;

class MongoGormConnectionRepository implements ConnectionRepository {
	
	String userId
	ConnectionFactoryLocator connectionFactoryLocator
	TextEncryptor textEncryptor
	
	public MongoGormConnectionRepository(String newUserID, 
		ConnectionFactoryLocator newConnectionFactoryLocator,
		TextEncryptor newTextEncryptor) {
		userId = newUserID
		connectionFactoryLocator = newConnectionFactoryLocator
		textEncryptor = newTextEncryptor
	}

	@Override
	public void addConnection(Connection<?> connection) {
		ConnectionData data = connection.createData()
		List multi_connections = MongoUserConnection.findAllByUserIdAndProviderId(userId, data.providerId)
		int rank = multi_connections.size() + 1
		
		MongoUserConnection newConnection = createUserConnection(rank, data);
		
		// Validate and Save User Connection
		if(!newConnection.validate()) throw new DuplicateConnectionException(connection.getKey())
		newConnection.save(flush: true)
	}

	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		List results = MongoUserConnection.findAllByUserId(userId)
		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>()
		
		Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
		for (String registeredProviderId : registeredProviderIds) {
			connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
		}
		
		for (MongoUserConnection connection : results) {
			String providerId = connection.providerId;
			if (connections.get(providerId).size() == 0) {
				connections.put(providerId, new LinkedList<Connection<?>>());
			}
			connections.add(providerId, connection);
		}
		
		return connections
	}

	@Override
	public List<Connection<?>> findConnections(String providerId) {
		List results = MongoUserConnection.findAllByUserIdAndProviderId(userId, providerId)
		List<Connection<?>> connections = new LinkedList<Connection<?>>()
		for(MongoUserConnection muc : results) {
			connections.add(this.connectionFromUserConnection(muc))
		}
		return connections
	}

	@Override
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		List<?> connections = findConnections(getProviderId(apiType));
 		return (List<Connection<A>>) connections;
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(
			MultiValueMap<String, String> providerUsers) {
		
			List<MongoUserConnection> mucs = new LinkedList<MongoUserConnection>()
			for (Iterator<Entry<String, List<String>>> it = providerUsers.entrySet().iterator(); it.hasNext();) {
				Entry<String, List<String>> entry = it.next();
				String providerId = entry.getKey();
				String providerUserId = entry.getValue();
				mucs.addAll(MongoUserConnection.findAllByUserIdAndProviderIdAndProviderUserId(userId, providerId, providerUserId))
			}

			List<Connection<?>> resultList = new LinkedList<Connection<?>>();
			for(MongoUserConnection muc : mucs) {
				resultList.add(this.connectionFromUserConnection(muc))
			}
			
			// Organize Multi Value Map
			MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<String, Connection<?>>();
			for (Connection<?> connection : resultList) {
				String providerId = connection.getKey().getProviderId();
				List<String> userIds = providerUsers.get(providerId);
				List<Connection<?>> connections = connectionsForUsers.get(providerId);
				if (connections == null) {
					connections = new ArrayList<Connection<?>>(userIds.size());
					for (int i = 0; i < userIds.size(); i++) {
						connections.add(null);
					}
					connectionsForUsers.put(providerId, connections);
				}
				String providerUserId = connection.getKey().getProviderUserId();
				int connectionIndex = userIds.indexOf(providerUserId);
				connections.set(connectionIndex, connection);
			}
			return connectionsForUsers;
	}

	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) findPrimeConnection(providerId);
	}

	@Override
	public Connection<?> getConnection(ConnectionKey connectionKey) {
		try {
			return this.connectionFromUserConnection(
				MongoUserConnection.findByUserIdAndProviderIdAndProviderUserId(userId, 
					connectionKey.getProviderId(), connectionKey.getProviderUserId()))
		} catch(NullPointerException e) {
			throw new NoSuchConnectionException(connectionKey);
		}
		
	}

	@Override
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
	}

	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		Connection<A> connection = (Connection<A>) findPrimeConnection(providerId);
		if (connection == null) {
			throw new NotConnectedException(providerId);
		}
		return connection;
	}

	@Override
	public void removeConnection(ConnectionKey connectionKey) {
		// TODO Auto-generated method stub
		def query = MongoUserConnection.where {
			userId == this.userId
			providerId == connectionKey.providerId
			providerUserId == connectionKey.providerUserId
		}
		query.deleteAll()
	}

	@Override
	public void removeConnections(String provId) {
		def usersId = this.userId;
		def query = MongoUserConnection.where {
			userId == usersId
			providerId == provId
		}
		query.deleteAll()

	}

	@Override
	public void updateConnection(Connection<?> connection) {
		ConnectionData data = connection.createData();
		MongoUserConnection newConnection = 
			MongoUserConnection.findByUserIdAndProviderIdAndProviderUserId(userId, data.providerId, data.providerUserId)
		newConnection.userId = userId
		newConnection.providerId = data.providerId
		newConnection.providerUserId = data.providerUserId
		newConnection.rank = rank
		newConnection.displayName = data.displayName
		newConnection.profileUrl = data.profileUrl
		newConnection.imageUrl = data.imageUrl
		newConnection.accessToken = encrypt(data.accessToken)
		newConnection.secret = encrypt(data.secret)
		newConnection.refreshToken = encrypt(data.refreshToken)
		newConnection.expireTime = data.expireTime
		newConnection.save(flush: true)
	}
	
	///////////// Utility Functions
	
	private MongoUserConnection createUserConnection(int rank, ConnectionData data) {
		MongoUserConnection newConnection = new MongoUserConnection()
		newConnection.userId = userId
		newConnection.providerId = data.providerId
		newConnection.providerUserId = data.providerUserId
		newConnection.rank = rank
		newConnection.displayName = data.displayName
		newConnection.profileUrl = data.profileUrl
		newConnection.imageUrl = data.imageUrl
		newConnection.accessToken = encrypt(data.accessToken)
		newConnection.secret = encrypt(data.secret)
		newConnection.refreshToken = encrypt(data.refreshToken)
		newConnection.expireTime = data.expireTime
		return newConnection
	}
	
	private Connection<?> connectionFromUserConnection(MongoUserConnection muc) {
		return new ConnectionData(
			muc.providerId,
			muc.providerUserId,
			muc.displayName,
			muc.profileUrl,
			muc.imageUrl,
			decrypt(muc.accessToken),
			decrypt(muc.secret),
			decrypt(muc.refreshToken),
			expireTime(muc.expireTime)
			)
		
	}
	private String decrypt(String encryptedText) {
		return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
	}
	private Long expireTime(long expireTime) {
		return expireTime == 0 ? null : expireTime;
	}
	private String encrypt(String text) {
		return text != null ? textEncryptor.encrypt(text) : text;
	}
	private <A> String getProviderId(Class<A> apiType) {
		return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
	}
	
	private Connection<?> findPrimeConnection(String providerId) {
		return this.connectionFromUserConnection(MongoUserConnection.findByUserIdAndProviderIdAndRank(userId, providerId, 1))
	}

}
