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
						<div class="text">Login with Facebook</div>
					</a>
					<a href="" class="twitter_connect">
						<div class="img"><i class="fa-icon-twitter"></i></div>
						<div class="text">Login with Twitter</div>
					</a>
					<a href="" class="linkedin_connect">
						<div class="img"><i class="fa-icon-linkedin"></i></div>
						<div class="text">Login with LinkedIn</div>
					</a>
					<div class="page-title-small">
						<h3 style="background:#f0f0f0">or</h3>
					</div>
					<form method="post" action="">
						<div class="row-fluid">
							<input class="span12" id="user" name="user" type="text" value="${params.user}" placeholder="username"/>
							<input class="span12" id="pass" name="pass" type="password" value="" placeholder="password"/>
						</div>
						<div class="row-fluid">
							<div class="remember">
								<input id="remember" name="remember" type="checkbox" value="1"/> Remember me!
							</div>
							<div class="register">
								<a href="/user/register">Register</a>
							</div>
						</div>

						<div class="actions">
							<button type="submit" class="btn span12">Login!</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>
