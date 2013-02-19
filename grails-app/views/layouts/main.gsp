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
						<g:if test="${session.user}">
							<a href="/logout" class="btn logout fa-icon-signout">
								Logout ${session.user.name}
							</a>
						</g:if>
					</g:if>
				</a>
			</div>
			<g:if test="${!isLogin && actionName != 'profile'}">
				<div class="row">
					<okm:bestMatch cssClass="bestMatch span12" prefix="We think you should meet" user="${user}"/>
				</div>
			</g:if>
			<g:layoutBody/>
		</div>
		<div id="feature-not-available" class="modal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>Feature Not Yet Available</h3>
		  	</div>
			<div class="modal-body">
				<p>Sorry, this feature is not yet available.</p>
				<r:img uri="images/okay-guy.png" class="okay-guy"/>
		  	</div>
			<div class="modal-footer">
				<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Close</button>
			</div>
		</div>
		<r:layoutResources/>
	</body>
</html>
