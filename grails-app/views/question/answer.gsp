<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>okmercury</title>
	</head>
	<body>
		<div class="row-fluid">
			<div class="span2 submitted-by">
				<h5>Question submitted on <g:formatDate format="MMM d yyyy" date="${question.createdDate}"/> by</h5>
				<h4><a href="/user/${question.createdBy.id}"/>${question.createdBy.name}</a></h4>
				<img src="http://gravatar.com/avatar/${question.createdBy?.gravatarHash}?s=60&d=identicon"/>
			</div>
			<div class="span10">
				<div class="row-fluid">
					<div class="answer">
						<fieldset>
							<input type="hidden" id="user-id" name="userId" value="${user.id}"/>
							<input type="hidden" id="question-id" name="questionId" value="${question.id}"/>

							<legend>${question.question}</legend>
							<g:each var="option" in="${options}">
								<label class="radio">
									<input type="radio" class="userAnswer" name="userAnswer" id="user-answer-${option.id}" value="${option.id}"
									${previousAnswer && !previousAnswer.skipped && previousAnswer.userAnswer?.id == option.id ?'checked':''}
									>
			  						${option.answer}
			  					</label>
							</g:each>

							<legend>Answer I'll accept...</legend>
							<g:each var="option" in="${options}">
								<label class="checkbox">
									<input type="checkbox" class="acceptableAnswer" name="acceptableAnswer"
										data-ordinal="${id}" id="acceptable-answer-${option.id }" value="${option.id}"
										${previousAnswer && !previousAnswer.skipped && previousAnswer.acceptableAnswerIds?.contains(option.id)?'checked':''}
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
										${(!previousAnswer && importance.ordinal() == 0) || (previousAnswer && !previousAnswer.skipped && importance.name() == previousAnswer.importance?.name())?'checked':''}>
			  						${importance.label}
			  					</label>
							</g:each>

							<legend>Explain your answer (optional)</legend>
							<textarea name="userAnswerExplanation" rows="5" class="userAnswerExplanation span6">${previousAnswer && !previousAnswer.skipped? previousAnswer.userAnswerExplanation:''}</textarea>
						</fieldset>
					</div>
				</div>
				<div class="row-fluid">
					<div>
						<a id="skip-question" class="btn">Skip &gt;</a>
						<a id="next-question" class="btn btn-success disabled">Save Answer &gt;</a>
					</div>
				</div>
			</div>
		</div>
		
	</body>
</html>
