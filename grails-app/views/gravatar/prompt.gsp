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
			<div class="row-fluid">
				<div class="span12 alert alert-error gravatar-create-message">
					<g:message code="gravatar.create.explanation" args="${[user.email]}"/>
				</div>
			</div>
			<div class="row-fluid">
				<a href="/user/${user.id}/gravatar/signup" class="span8 offset2 btn btn-primary">
					<i class="fa-icon-user"></i>Create Gravatar
				</a>
			</div>
			<div class="row-fluid">
				<a href="/" class="span8 offset2 btn">
					<i class="fa-icon-home"></i>Skip and Go to Home Page
				</a>
			</div>
		</div>
	</body>
</html>
