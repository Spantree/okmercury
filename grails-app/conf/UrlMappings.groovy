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

		"/user/${userId}/question/${questionId}/skip"(controller: 'question') {
			action = [GET: 'answer', PUT: 'skipAnswer']
		}
		
		"/user/${userId}/question/${questionId}"(controller: 'question') {
			action = [GET: 'answer', PUT: 'saveAnswer']
		}

		"/user/${userId}/question/${questionId}/skip"(controller: 'question') {
			action = [GET: 'answer', PUT: 'skipAnswer']
		}

		"/user/${userId}/matches"(controller: 'match', action: 'showMatches')

		"/user/${userId}/question/unanswered"(controller: 'question', action: 'nextUnansweredQuestionForUser')
		"/user/${userId}/question/done"(controller: 'question', action: 'done')

		"/reset"(controller: 'reset', action: 'reset')
		
		"/login"(controller: 'login', action: 'login')
		"/logout"(controller: 'login', action: 'logout')
		"/user/register"(controller: 'login', action: 'register')
		"/user/${userId}/created"(controller: 'login', action: 'created')
		"/user/${userId}/gravatar/prompt"(controller: 'gravatar', action: 'prompt')
		"/user/${userId}/gravatar/signup"(controller: 'gravatar', action: 'signup')

		"/"(controller: 'home', action: 'index')
		"500"(view:'/error')
	}
}
