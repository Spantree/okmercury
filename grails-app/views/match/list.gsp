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
						<th>Overall Score</th>
						<th>Their Score for You</th>
						<th>Your Score for Them</th>
						<th>Questions in Common</th>
					</tr>
				</thead>
				<tbody>
					<g:each var="match" in="${matches}">
						<g:set var="matchUser" value="${userMap[match.matchUserId]}"/>
						<tr>
							<td>${matchUser.firstName} ${matchUser.lastName}</th>
							<td><g:formatNumber type="percent" minFractionDigits="2" number="${match.overallScore}"/></td>
							<td><g:formatNumber type="percent" minFractionDigits="2" number="${match.principalPercentageScore}"/></td>
							<td><g:formatNumber type="percent" minFractionDigits="2" number="${match.matchPercentageScore}"/></td>
							<td>&nbsp;</td>
						</tr>
					</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>
