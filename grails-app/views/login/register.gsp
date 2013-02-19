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
					<a href="" class="facebook_connect">
						<div class="img"><i class="fa-icon-facebook"></i></div>
						<div class="text">Register with Facebook</div>
					</a>
					<a href="" class="twitter_connect">
						<div class="img"><i class="fa-icon-twitter"></i></div>
						<div class="text">Register with Twitter</div>
					</a>
					<a href="" class="linkedin_connect">
						<div class="img"><i class="fa-icon-linkedin"></i></div>
						<div class="text">Register with LinkedIn</div>
					</a>
					<div class="page-title-small">
						<h3 style="background:#f0f0f0">or</h3>
					</div>
					<form method="post" action="">
						<div class="row-fluid">
							<input class="span12" id="first-name" name="firstName" type="text"
								value="${params.firstName}" placeholder="First Name"
							/>
							<input class="span12" id="first-name" name="lastName" type="text"
								value="${params.lastName}" placeholder="Last Name"
							/>
							<input class="span12" id="company-name" name="companyName" type="text"
								value="${params.companyName}" placeholder="Company Name"
							/>
							<input class="span12" id="job-title" name="jobTitle" type="text"
								value="${params.jobTitle}" placeholder="Job Title"
							/>
							<input class="span12" id="user" name="user" type="text"
								value="${params.user}" placeholder="Email"
							/>
							<input class="span12" id="pass" name="pass" type="password" value="" placeholder="Password"/>
							<input class="span12" id="pass-confirm" name="confirmPass" type="password" value="" placeholder="Confirm Password"/>
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
