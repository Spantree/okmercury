<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>okmercury</title>
	</head>
	<body>
		<div class="row">
			<div class="span11 definition">
				<em>Mercury:</em> the patron god of financial gain, commerce, eloquence,
				messages/communication, travelers, boundaries and luck.
				<g:if test="${oauth}">
					<div class="row">
						<div class="span11">
							<h4>Your okMercury account has been connected with your ${oauth} account.</h4>
						</div>
					</div>
				</g:if>
			</div>
		</div>
		<div class="row main-links">
			<g:if test="${!user.authorities.contains('ROLE_ADMIN')}">
				<a id="answer-questions" class="span2 btn btn-success"
					href="<g:createLink uri="/user/${user.id}/question/unanswered"/>"
				>
					<i class="fa-icon-ok"></i>Answer Questions
				</a>
			</g:if>
			<g:else>
				<a id="reset" class="span2 btn btn-danger"
					href="<g:createLink uri="/reset"/>"
				>
					<i class="fa-icon-trash"></i>Reset Application
				</a>
			</g:else>
			<a id="add-question" class="span2 btn btn-success" 
				href="<g:createLink uri="/question"/>" 
				>
				<i class="fa-icon-plus"></i>Add Questions
			</a>
			<a id="review-questions" class="span2 btn btn-success"
				href="<g:createLink uri="/question/list"/>">
				<i class="fa-icon-tasks"></i>Review Questions
			</a>
			<g:if test="${!user.authorities.contains('ROLE_ADMIN')}">
				<a id="view-matches" class="span2 btn btn-success"
					href="<g:createLink uri="/user/${user.id}/matches"/>"
				>
					<i class="fa-icon-asterisk"></i>View Matches
				</a>
			</g:if>
		</div>
	</body>
</html>
