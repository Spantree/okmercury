<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
	</head>
	<body>
		<div class="row-fluid">
			<legend>Edit questions</legend>
		</div>
		<div class="row review-questions">
			<table class="table table-bordered table-striped span12">
				<thead>
					<tr>
						<th class="span4">Question</th>
						<th class="span4">Your Answer</th>
						<th class="span4" colspan="2">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<g:each var="q" in="${questions}">
						<tr>
							<td>
								${q.question}
							</td>
							<td>
								<g:set var="answer" value="${answerMap?.getAt(q?.id)}"/>
								<g:if test="${answer?.skipped}">
									(Skipped)
								</g:if>
								<g:else>
									${answer?.userAnswer?.answer?:'(Unanswered)'}
								</g:else>
							</td>
							<td>
								<a id="change-answer-${q.id}" class="btn btn-small btn-primary"
									href="<g:createLink uri="/user/${user.id}/question/${q.id}"/>"
								>
									<i class="fa-icon-undo"></i>Change Answer
								</a>
							</td>
							<td>
								<a id="edit-question-${q.id}" class="btn btn-small btn-info ${q?.createdBy == user?'disabled':''}"
									href="<g:createLink uri="/question/${q.assignedId}"/>"
								>
									<i class="fa-icon-edit"></i>Edit Question
								</a>
							</td>
						</tr>
					</g:each>
				</tbody>
			</table>
		</div>
	</div>
	</body>
</html>
