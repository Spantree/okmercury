<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>okmercury</title>
	</head>
	<body>
		<div class="row-fluid">
			<legend>People You Should Meet</legend>
		</div>
		<div class="row">
			<table class="table table-bordered table-striped span12">
				<thead>
					<tr>
						<th>Name</th>
						<th><a href="?sort=overallScore"/>Overall Score</a></th>
						<th><a href="?sort=principalPercentageScore"/>Their Score for You</a></th>
						<th><a href="?sort=matchPercentageScore"/>Your Score for Them</a></th>
						<th><a href="?sort=questionsInCommon"/>Questions in Common</th>
					</tr>
				</thead>
				<tbody>
					<g:each var="match" in="${matches}">
						<g:set var="matchUser" value="${userMap[match.matchUserId]}"/>
						<tr>
							<td>
								<a href="<g:createLink uri="/user/${matchUser.id}"/>">
									<img src="http://gravatar.com/avatar/${matchUser?.gravatarHash}?s=30&d=identicon" width="30" height="30"/>
									${matchUser?.name}
								</a>
							</th>
							<td><g:formatNumber type="percent" minFractionDigits="2" number="${match?.overallScore}"/></td>
							<td><g:formatNumber type="percent" minFractionDigits="2" number="${match?.principalPercentageScore}"/></td>
							<td><g:formatNumber type="percent" minFractionDigits="2" number="${match?.matchPercentageScore}"/></td>
							<td>
								<g:if test="${match.questionsInCommon}">${match?.questionsInCommon}</g:if>&nbsp;
							</td>
						</tr>
					</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>
