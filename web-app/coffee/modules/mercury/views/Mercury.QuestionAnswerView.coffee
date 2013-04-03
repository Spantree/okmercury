((app, Mercury) ->
	class Mercury.QuestionAnswerView extends Backbone.View
		el: "#question-answer"
		events: 
			'click a#next-question': 'saveAnswer'
			'click a#skip-question': 'skipQuestion'
			
    
		initialize: (model) ->
			@errors = []
			
		clearErrors: ->
			@errors = []
			@hideErrors()
			
		getAnswer: ->
			$userAnswer = $('.userAnswer').filter(':checked')
			$($userAnswer[0]).val()
		
		getAnswerExplanation: ->
			$('.userAnswerExplanation').val()
			
		getAcceptableAnswers: ->
			acceptableAnswers = []
			$acceptableAnswers = $('.acceptableAnswer').filter(':checked')
			$acceptableAnswers.each (i, ansEl) -> acceptableAnswers.push $(ansEl).val()
			
			acceptableAnswers
		
		getImportance: ->
			$importance = $('.importance').filter(':checked')
			$($importance[0]).val()
		
		getQuestionId: ->
			$('#question-id').val()
			
		getUserId: ->
			$('#user-id').val()
		
		goToNextQuestion: (userId) ->
			Mercury.Router.navigate "/user/#{userId}/question/unanswered"
		
		hideErrors: ->
			$('#error-message').hide()
		
		isValid: ->
			@validate()
			@errors.length == 0
			
		saveAnswer: ->
			if @isValid()
				questionId = @getQuestionId()
				userId = @getUserId()
				
				data =
					userAnswer: @getAnswer()
					acceptableOptions: @getAcceptableAnswers()
					importance:  @getImportance()
					userAnswerExplanation: @getAnswerExplanation()
			
				ajaxSettings =
					type: 'PUT'
					data: JSON.stringify(data)
					contextType: 'application/json'
					processData: false
					success: => @goToNextQuestion(userId)
					error: @showError
					
				$.ajaxSetup contentType: "application/json; charset=utf-8"
				$.ajax "/user/#{userId}/question/#{questionId}", ajaxSettings
			else
				@showError @errors
		
		showError: (messages) ->
			console.log messages
			if messages
				$('#error-message').html(messages)
				$('#error-message').show()
				
		skipQuestion: ->
			@clearErrors()
			
			questionId = @getQuestionId()
			userId = @getUserId()
	
			data = {}
			ajaxSettings =
				type: 'PUT'
				data: JSON.stringify(data)
				contextType: 'application/json'
				processData: false
				success: => @goToNextQuestion(userId)
				error: @showError
	
			$.ajaxSetup contentType: "application/json; charset=utf-8"
			$.ajax "/user/#{userId}/question/#{questionId}/skip", ajaxSettings
		
		validate: ->
			@errors = []
			if !@getAnswer() || @getAnswer().length == 0
				@errors.push "Please select an answer"
			if @getAcceptableAnswers().length == 0 && @getImportance() != 'IRRELEVANT'
				@errors.push "Please select at least one acceptable answer or irrelevant"
				
		
)(app, app.module('mercury'))