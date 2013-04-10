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
						<strong>Error:</strong> <g:message code="${messageKey}" args="${[params.user]}"/>
					</div>
				</div>
			</g:if>
			<div class="row-fluid">
				<div id="login-form">
					<form action="/sssignin/facebook" method="POST" style="margin: 0px">
					<button class="facebook_connect" type="submit">
							<div class="img"><i class="fa-icon-facebook"></i></div>
							<div class="text">Login with Facebook</div>
					</button>
					</form>
					<form action="/sssignin/twitter" method="POST" style="margin: 0px">
					<button class="twitter_connect" type="submit">
						<div class="img"><i class="fa-icon-twitter"></i></div>
						<div class="text">Login with Twitter</div>
					</button>
					</form>
					<a href="" class="linkedin_connect">
						<div class="img"><i class="fa-icon-linkedin"></i></div>
						<div class="text">Login with LinkedIn</div>
					</a>
					<div class="page-title-small">
						<h3 style="background:#f0f0f0">or</h3>
					</div>
					<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
						<div class="row-fluid">
							<input class="span12" id="user" name='j_username' type="text" value="${params.user}" placeholder="email"/>
							<input class="span12" id="pass" name='j_password' type="password" value="" placeholder="password"/>
						</div>
						<div class="row-fluid">
							<!--
							<div class="remember">
								<input id="remember" name='${rememberMeParameter}' type="checkbox" value="1"/>
								<label for='remember'><g:message code="springSecurity.login.remember.me.label"/></label>
							</div>
							-->
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
