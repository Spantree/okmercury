<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>okmercury</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="answer">
				<fieldset>
					<input type="hidden" id="user-id" name="userId" value="${user.id}"/>
					<input type="hidden" id="question-id" name="questionId" value="${question.id}"/>
					
					<legend>${question.question}</legend>
					<g:each var="option" in="${options}">
						<label class="radio">
							<input type="radio" class="userAnswer" name="userAnswer" id="user-answer-${option.id}" value="${option.id}">
	  						${option.answer}
	  					</label>
					</g:each>
					
					<legend>Explain your answer (optional)</legend>
					<textarea name="userAnswerExplanation" class="userAnswerExplanation"></textarea>
					
					<legend>Answer I'll accept...</legend>
					<g:each var="option" in="${options}">
						<label class="checkbox">
							<input type="checkbox" class="acceptableAnswer" name="acceptableAnswer"
								data-ordinal="${id}" id="acceptable-answer-${option.id }" value="${option.id}"
							>
	  						${option.answer}
	  					</label>
					</g:each>
					
					<legend>This question is...</legend>
					<g:each var="importance" in="${importanceOptions}">
						<label class="radio">
							<input type="radio" class="importance" name="importance"
								id="importance-${importance.ordinal()}"
								value="${importance.name()}"
								${importance.ordinal() == 0?'checked':''}>
	  						${importance.label}
	  					</label>
					</g:each>
					
				</fieldset>
			</div>
		</div>
		<div class="row-fluid">
			<div>
				<a id="next-question" class="btn btn-success disabled">Save Answer &gt;</a>
				<a id="skip-question" class="btn btn-warning">Skip &gt;</a>
			</div>
		</div>
	</body>
</html>
