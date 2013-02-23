<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
	</head>
	<body>
		<g:if test="${success}">
			<div class="row-fluid"/>
				<div class="span6 alert alert-success">
					Successfully added question!
				</div>
			</div>
		</g:if>
		<div class="row-fluid" id="question-edit">
			<div id="error-message" class="alert">
				An error occurred
			</div>
			
			<form class="question-create">
				<input type="hidden" id="question-id" name="id" value="${id}"/>
  				<fieldset>
					<legend>Create your question</legend>
					<label>Your match question</label>
					<textarea class="match-question" rows="5" placeholder="Type your question...">${questionText}</textarea>

					<label>Possible answers</label>
					<g:each var="i" in="${(1..5)}">
						<g:set var="required" value=""/>
						<g:if test="${i<3}">
							<input type="text" class="possible-answer" required
								placeholder="Possible answer ${i} (required)"
								data-answer-number="${i}"
								value="${options?.getAt(i-1)}"
							/>
						</g:if>
						<g:else>
							<input type="text" class="possible-answer"
								placeholder="Possible answer ${i}"
								data-answer-number="${i}"
								value="${options?.getAt(i-1)}"
								class="span6"
							/>
						</g:else>
					</g:each>
  				</fieldset>
			</form>
			<div>
				<a id="save-question-and-add-another" class="btn btn-info">Add Another Question &gt;</a>
				<a id="save-question-and-go-home" class="btn btn-success" href="/">Done Adding Questions</a>
			</div>
		</div>
	</body>
</html>
