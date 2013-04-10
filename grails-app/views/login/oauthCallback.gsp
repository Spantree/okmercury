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
		<div id="wrapper" class="full lr-page span4 offset4">
			<!-- start: Row -->
			<g:if test="${messageKey && !created}">
				<div class="row-fluid">
					<div class="span12 alert alert-error">
						Error: <g:message code="${messageKey}"/>
					</div>
				</div>
			</g:if>
			<div class="row-fluid">
				<div id="login-form">
					<h5>Welcome! We just need a bit more information to add you to our system.</h5>
					<div class="page-title-small">
						<h3 style="background:#f0f0f0">or</h3>
					</div>
					<form method="post" action="">
						<div class="row-fluid">
							<input class="span12" id="first-name" name="firstName" type="text"
								value="${profile.firstName}" placeholder="First Name"
							/>
							<input class="span12" id="first-name" name="lastName" type="text"
								value="${profile.lastName}" placeholder="Last Name"
							/>
							<input class="span12" id="company-name" name="companyName" type="text"
								value="${params.companyName}" placeholder="Company Name"
							/>
							<input class="span12" id="job-title" name="jobTitle" type="text"
								value="${params.jobTitle}" placeholder="Job Title"
							/>
							<input class="span12" id="user" name="user" type="text"
								value="${profile.email}" placeholder="Email"
							/>
							<input type="hidden" name="provider" value="${provider}" />
							<input type="hidden" name="username" value="${profile.username}" />
						</div>

						<div class="actions">
							<button type="submit" class="btn span12">Register and Login!</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>
