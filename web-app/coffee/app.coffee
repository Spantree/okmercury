$ ->
	addNewQuestion = (data, status) ->
		$form = $('<form method="POST" action="/question">')
		if(data?.success)
			$status = $("<input type=\"hidden\" name=\"success\" value=\"true\">")
			$form.append($status)
		$form.submit()
		return false

	goToQuestionList = ->
		window.location = '/question'

	goHome = ->
		window.location = '/'

	showError = ->
		$('#error-message').css
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


	$('#add-question').click addNewQuestion

	$('#save-question-and-add-another').click -> saveQuestion true
	$('#save-question-and-go-home').click -> saveQuestion false