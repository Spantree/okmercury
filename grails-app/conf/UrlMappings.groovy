class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/question/"(controller: 'question') {
			action = [GET: 'list', POST: 'create']
		}
		
		"/question/$id"(controller: 'question') {
			action = [GET: 'edit', PUT: 'save']
		}
		
		"/user/${userId}/question/${questionId}"(controller: 'question', action: 'answer')

		"/login"(controller: 'login', action: 'login')
		"/logout"(controller: 'login', action: 'logout')
		
		"/"(controller: 'home', action: 'index')
		"500"(view:'/error')
		
	}
}
