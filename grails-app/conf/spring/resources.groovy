// Place your Spring DSL code here
beans = {
	userDetailsService(co.okmercury.UserService)
	
	usersConnectionRepository(co.okmercury.security.MongoGormUsersConnectionRepository) {
		connectionFactoryLocator = ref('connectionFactoryLocator')
		textEncryptor = ref('textEncryptor')
//		connectionSignUp = ref('connectionSignUp')
	}
}
