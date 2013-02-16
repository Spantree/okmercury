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
		
		"/testUserMatch"(controller: 'testUserMatch', action: 'test')

		"/user/${userId}/question/${questionId}"(controller: 'question') {
			action = [GET: 'answer', PUT: 'saveAnswer']
		}

		"/user/${userId}/question/unanswered"(controller: 'question', action: 'nextUnansweredQuestionForUser')

		"/login"(controller: 'login', action: 'login')
		"/logout"(controller: 'login', action: 'logout')
		
		"/"(controller: 'home', action: 'index')
		"500"(view:'/error')
		
	}
}
