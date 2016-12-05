<%@include file="header.jsp"%>
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">
		<h1>HR Feedback of ${student.firstName} ${student.lastName}</h1>
		<h6><a href="<%=request.getContextPath()%>/user/view/${student.id}/"><--- Back to student</a></h6>
	</div>
</div>
<form name="viewHrFeedback"
	action="<%=request.getContextPath()%>/hrFeedback/view/${hrFeedback.id}/"
	method="post">
<div class="row">
	<div class="col-sm-5 col-sm-offset-1">
		<h3>Author</h3>
		<p> ${author.firstName} ${author.lastName} </p>
	</div>
	<div class="col-sm-5">
		<h3>Added by</h3>
		<p>${addedBy.firstName} ${addedBy.lastName}</p>
	</div>
</div>

		<hr />

		<div class="col-sm-offset-1 col-sm-9">
			
			
		</div>

		<div class="col-sm-offset-1 col-sm-9">
			<h4>
				<strong>Topic</strong>
			</h4>
			<input name="topic" class="form-control"
				placeholder="This field is required" value="${hrFeedback.topic }" disabled>
		</div>

		<div class="col-sm-offset-1 col-sm-9">
			<h4>
				<strong>Summary</strong>
			</h4>
			<textarea name="summary" class="gradeComment form-control"
				placeholder="This field is required" disabled style="height: 400px;">${hrFeedback.summary }</textarea>
		</div>
		<div class="col-sm-9"></div>


		<div class="row">

			<div class="col-sm-offset-9 col-sm-2">

				<br>

				
			</div>

		</div>
		
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<%@include file="footer.jsp"%>