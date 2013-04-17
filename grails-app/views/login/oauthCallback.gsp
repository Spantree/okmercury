<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
<style>
div.alert {
	font-size: 16px;
}
</style>
</head>
<body>
	<div id="wrapper" class="full lr-page span4 offset4">
		<g:if test="${messageKey && !created}">
			<div class="row-fluid">
				<div class="span12 alert alert-error">
					Error:
					<g:message code="${messageKey}" />
				</div>
			</div>
		</g:if>
		<g:if test="${confirmation}">
			<div class="row-fluid">
				<div id="login-form">
					<g:if test="${messageKey == 'user.multiple.facebook'}">
						<form action="/sssignin/facebook" method="POST"
							style="margin: 0px">
							<button class="facebook_connect" type="submit">
								<div class="img">
									<i class="fa-icon-facebook"></i>
								</div>
								<div class="text">Login with Facebook</div>
							</button>
						</form>
					</g:if>
					<g:elseif test="${messageKey == 'user.multiple.twitter'}">
						<form action="/sssignin/twitter" method="POST"
							style="margin: 0px">
							<button class="twitter_connect" type="submit">
								<div class="img">
									<i class="fa-icon-twitter"></i>
								</div>
								<div class="text">Login with Twitter</div>
							</button>
						</form>
					</g:elseif>
					<g:elseif test="${messageKey == 'user.multiple.linkedin'}">
						<form action="/sssignin/linkedin" method="POST"
							style="margin: 0px">
							<button class="linkedin_connect" type="submit">
								<div class="img">
									<i class="fa-icon-linkedin"></i>
								</div>
								<div class="text">Login with LinkedIn</div>
							</button>
						</form>
					</g:elseif>
						<g:else>
							<h5>
								The user
								${user}
								already exists. Confirm your OkMercury password to merge this
								social account.
							</h5>
							<h5>
								Not
								${user}? <a href="/login/auth">Re-Login Here</a>
							</h5>
							<div class="page-title-small">
								<h3 style="background: #f0f0f0"></h3>
							</div>
							<form method="post" action="">
								<div class="row-fluid">
									<input class="span12" id="pass" name="pass" type="password"
										value="" placeholder="Password" /> <input type="hidden"
										name="provider" value="${provider}" /> <input type="hidden"
										name="username" value="${username}" /> <input type="hidden"
										name="user" value="${user}" />
								</div>

								<div class="actions">
									<button type="submit" class="btn span12">
										Merge
										${provider}
										account!
									</button>
								</div>
							</form>
						</g:else>
				</div>

			</div>

		</g:if>
		<g:else>
			<div class="row-fluid">
				<div id="login-form">
					<h5>Welcome! We just need a bit more information to add you to
						our system.</h5>
					<div class="page-title-small">
						<h3 style="background: #f0f0f0">or</h3>
					</div>
					<form method="post" action="">
						<div class="row-fluid">
							<input class="span12" id="first-name" name="firstName"
								type="text" value="${profile.firstName}"
								placeholder="First Name" /> <input class="span12"
								id="first-name" name="lastName" type="text"
								value="${profile.lastName}" placeholder="Last Name" />
							<input class="span12" id="company-name" name="companyName"
								type="text" value="${params.companyName}"
								placeholder="Company Name" /> <input class="span12"
								id="job-title" name="jobTitle" type="text"
								value="${params.jobTitle}" placeholder="Job Title" /> <input
								class="span12" id="user" name="user" type="text"
								value="${profile.email}" placeholder="Email" /> <input
								type="hidden" name="provider" value="${provider}" /> <input
								type="hidden" name="username" value="${profile.username}" />
						</div>

						<div class="actions">
							<button type="submit" class="btn span12">Register and
								Login!</button>
						</div>
					</form>
				</div>
			</div>
		</g:else>
	</div>
</body>
</html>
