<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>okmercury</title>
	</head>
	<body>
		<div class="row profile">
			<div class="span3">
				<img src="http://gravatar.com/avatar/${user?.gravatarHash}?s=222&d=identicon" width="222" height="222"/>
				<h3>${user.name}</h3>
				<hr/>
				<div>
					<i class="fa-icon-group"></i>${user.companyName}
				</div>
				<div>
					<i class="fa-icon-briefcase"></i>${user.jobTitle}
				</div>
				<div>
					<i class="fa-icon-inbox"></i><a href="mailto:${user.email}"/>${user.email}</a>
				</div>
				<hr/>
				<div class="row">
					<div class="span4">
						<em>${answers.size()}</em> questions answered<br/>
					</div>
					<div class="span4">
						<em>${numberSkipped}</em> questions skipped
					</div>
				</div>
			</div>
			<div class="span9">
				<legend>${user.firstName}'s Answers</legend>
				<g:each var="answer" in="${answers}">
					<h4 class="question">${answer.question.question}</h4>
					<span class="answer">${answer.userAnswer.answer}</span>
				</g:each>
			</div>
		</div>
	</body>
</html>
