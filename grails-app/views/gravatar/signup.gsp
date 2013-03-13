<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<style>
			div.alert {
				font-size: 16px;
			}
		</style>
	</head>
	<body>
		<div id="wrapper" class="full lr-page span8 offset4">
			<g:if test="${success}">
				<div class="row-fluid">
					<div class="span12 alert alert-success gravatar-create-message">
						<g:message code="gravatar.signup.success" args="${[user.email]}"/>
					</div>
				</div>
			</g:if>
			<g:else>
				<div class="row-fluid">
					<div class="span12 alert alert-error gravatar-create-message">
						<g:message code="gravatar.signup.error" args="${[user.email]}"/>
					</div>
				</div>
			</g:else>
			<div class="row-fluid">
				<a href="/" class="span12 btn btn-primary">
					<i class="fa-icon-home"></i>Go to Home Page
				</a>
			</div>
		</div>
	</body>
</html>
