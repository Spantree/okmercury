((app, Mercury) ->
	class Mercury.Router extends Backbone.Router
		views: {}

		routes: {}

		initialize: ->
			@views =
				questionEdit: new Mercury.QuestionEdit
				
		@navigate: (url) ->
			window.location.assign(url);
				
)(app, app.module('mercury'))