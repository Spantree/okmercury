$ ->
	addNewQuestion = (data, status) ->
		$form = $('<form method="POST" action="/question">')
		if(data?.success)
			$status = $("<input type=\"hidden\" name=\"success\" value=\"true\">")
			$form.append($status)
		$form.submit()
		return false

	goToQuestionList = -> window.location = '/question'
	goToNextQuestion = (userId) -> window.location = "/user/#{userId}/question/unanswered"
	goHome = -> window.location = '/'

	showError = (messages) ->
		$errorMessage = $('#error-message')

		if messages
			$errorMessage.html(messages)

		$errorMessage.css
			display: 'inline'

	saveQuestion = (addAnother) ->
		id = $('#question-id').val()

		data =
			questionText: $('.match-question').val()
			possibleAnswers: []

		$('input.possible-answer').each (i, el) ->
			val = $(el).val()
			if val
				data.possibleAnswers.push val

		callback = if addAnother then addNewQuestion else goToQuestionList

		ajaxSettings =
			type: 'PUT'
			data: JSON.stringify(data)
			contextType: 'application/json'
			processData: false
			success: callback
			error: showError

		$.ajax "/question/#{id}", ajaxSettings
		return false

	saveAnswer = ->
		errors = []
		questionId = $('#question-id').val()
		userId = $('#user-id').val()
		$userAnswer = $('.userAnswer').filter(':checked')
		$acceptableAnswers = $('.acceptableAnswer').filter(':checked')
		$importance = $('.importance').filter(':checked')
		importance = $($importance[0]).val()

		if $userAnswer.length == 0
			errors.push "Please select an answer"
		if $acceptableAnswers.length == 0 and importance != 'IRRELEVANT'
			errors.push "Please select at least one acceptable answer or irrelevant"

		if errors.length > 0
			showError errors
		else
			acceptableAnswers = []
			$acceptableAnswers.each (i, el) -> acceptableAnswers.push $(el).val()
			data =
				userAnswer: $($userAnswer[0]).val()
				acceptableOptions: acceptableAnswers
				importance: importance

			ajaxSettings =
				type: 'PUT'
				data: JSON.stringify(data)
				contextType: 'application/json'
				processData: false
				success: -> goToNextQuestion(userId)
				error: showError

			$.ajax "/user/#{userId}/question/#{questionId}", ajaxSettings



	$('#add-question').click addNewQuestion

	$('#save-question-and-add-another').click -> saveQuestion true
	$('#save-question-and-go-home').click -> saveQuestion false

	$('.userAnswer').click ->
		$selectedUserAnswerOption = $('.userAnswer').filter(':checked')
		if $selectedUserAnswerOption.length == 0
			$('#next-question').addClass('disabled')
		else
			$('#next-question').removeClass('disabled')

	$('#next-question').click saveAnswer

