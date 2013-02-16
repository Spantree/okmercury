<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="okmercury"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<r:require modules="bootstrap, application"/>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
		<g:set var="isLogin" value="${controllerName == 'login'}"/>
		<div class="container">
			<div class="row">
				<a href="/" class="logo ${isLogin?'center':''}">
					<r:img uri="images/logo.png" class="logo"/>
					<g:if test="${!isLogin}">
						<a href="/login" class="btn logout fa-icon-signout">
							Logout
						</a>
					</g:if>
				</a>
			</div>
			<g:layoutBody/>
		</div>
		<r:layoutResources />
	</body>
</html>
