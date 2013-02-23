((app, Mercury) ->
	class Mercury.QuestionEdit extends Backbone.View
		el: "#question-edit"
		events: 
			'click a#save-question-and-add-another': 'saveAndAddAnother'
    
		initialize: (model) ->
			
		saveAndAddAnother: ->
			@saveQuestion true
		
		getQuestionId: ->
			$('#question-id').val()
			
		getQuestionText: ->
			$('.match-question').val()
		
		getPossibleAnswers: ->
			$('input.possible-answer')
			
		goToAddQuestion: (data, status) ->
			if($('#question-id').val())
				Mercury.Router.navigate "/question"
			else
				$('form')[0].reset()
				$('#error-message').hide()
		
		goToQuestionList: (data, status) ->
			Mercury.Router.navigate "/question/list"
			
		saveQuestion: (addAnother) ->
			id = @getQuestionId()
	
			data =
				questionText: @getQuestionText()
				possibleAnswers: []
	
			@getPossibleAnswers().each (i, el) ->
				val = $(el).val()
				if val
					data.possibleAnswers.push val
	
			callback = if addAnother then @goToAddQuestion else @goToQuestionList
	
			ajaxSettings =
				type: 'PUT'
				data: JSON.stringify(data)
				contextType: 'application/json'
				processData: false
				success: callback
				error: @showError
	
			$.ajaxSetup contentType: "application/json; charset=utf-8"
			$.ajax "/question/#{id}", ajaxSettings
			
			return false
		
		hideError: ->
			$('#error-message').hide()
			
		showError: (messages) ->
			
			if messages
				$('#error-message').html(messages)
				$('#error-message').show()
		
		

)(app, app.module('mercury'))