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
		"/question/$id"(controller: 'question', action: 'edit')
		
		"/"(view:"/index")
		"500"(view:'/error')
		
	}
}
