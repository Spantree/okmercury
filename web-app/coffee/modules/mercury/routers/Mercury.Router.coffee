((app, Mercury) ->
	class Mercury.Router extends Backbone.Router
		views: {}

		routes: {}

		initialize: ->
			@views =
				questionEdit: new Mercury.QuestionEditView
				questionAnswer: new Mercury.QuestionAnswerView
				
		@navigate: (url) ->
			window.location.assign(url);
				
)(app, app.module('mercury'))