window.app = window.app ? new Object()
app.module = (->
		modules = {}

		(name) ->
			if not modules[name]
				modules[name] = Views: {}
			modules[name]
	)()
	
$ ->
	window.app.vent = _.extend {}, Backbone.Events
	Mercury = app.module('mercury')
	app.router = new Mercury.Router