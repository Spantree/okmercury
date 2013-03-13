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
			<!-- start: Row -->
			<div class="row-fluid">
				<div class="span2">
					<img src="http://gravatar.com/avatar/${user.gravatarHash}?s=98&d=identicon"/>
				</div>
				<div class="span10 alert alert-success">
					<g:message code="user.created" args="${[user.email]}"/>
					<a href="/" class="span6 offset3 btn btn-primary" style="margin-top: 20px;">
						<i class="fa-icon-home"></i>Go to Home Page
					</a>
				</div>
			</div>
		</div>
	</body>
</html>
