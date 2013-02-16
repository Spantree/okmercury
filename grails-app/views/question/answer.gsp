<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>okmercury</title>
		<r:script>
			
		</r:script>
	</head>
	<body>
		<div class="row-fluid">
			<div>
				<fieldset>
					<legend>${question.question}</legend>
					<g:each var="option" in="${question.options}">
						<label class="radio">
							<input type="radio" class="userAnswer" name="userAnswer" id="user-answer-${option.id}" value="${option.id}">
	  						${option.answer}
	  					</label>
					</g:each>
					<legend>Answer I'll accept...</legend>
					<g:each var="option" in="${question.options}">
						<label class="checkbox">
							<input type="checkbox" class="acceptableAnswer" name="acceptableAnswer" id="acceptable-answer-${option.id }" value="${option.id}">
	  						${option.answer}
	  					</label>
					</g:each>
					<legend>This question is...</legend>
					<g:each var="importance" in="${importanceOptions}">
						<label class="radio">
							<input type="radio" class="importance" name="importance" id="importance-${importance.ordinal()}" value="${importance.name()}">
	  						${importance.label}
	  					</label>
					</g:each>
				</fieldset>
			</div>
		</div>
		<div class="row-fluid">
			<div>
				<a class="btn btn-success">Next Question &gt;</a>
			</div>
		</div>
	</body>
</html>
