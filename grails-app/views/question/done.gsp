<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>okmercury</title>
	</head>
	<body>
		<div class="row">
			<h3 class="span12">
				Congratulations! You've completed all the questions!
			</h3>
		</div>
		<div class="row">
			<r:img class="span6" uri="images/success-kid.jpg"/>
		</div>
		<div class="row main-links" style="margin-top: 20px; margin-left: 0px;">
			<div>
				<a id="review-questions" class="btn btn-success"
					href="<g:createLink uri="/question"/>">
					<i class="fa-icon-tasks"></i>Review Questions
				</a>
				<a id="add-question" class="btn btn-success"><i class="fa-icon-plus"></i>Add More Questions</a>
				<a id="view-matches" class="btn btn-success"
					href="<g:createLink uri="/user/${user.id}/matches"/>"
				>
					<i class="fa-icon-asterisk"></i>View Your Matches
				</a>
			</div>
		</div>
	</body>
</html>
