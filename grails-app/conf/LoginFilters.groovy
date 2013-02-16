class LoginFilters {
	 def filters = {
        loginCheck(controller: '*', action: '*') {
            before = {
                if (!session.user && !actionName.equals('login')) {
                    redirect(uri: '/login')
                    return false
                }
            }
        }
    }
}