<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
	</head>
	<body>
		<div class="row-fluid">
			<legend>Edit questions</legend>
		</div>
		<g:each var="q" in="${questions}">
			<div class="row-fluid">
				<a class="question-link span12" href="<g:createLink uri="/question/${q.assignedId}"/>">${q.question}</a>
			</div>
		</g:each>
	</div>
	</body>
</html>
