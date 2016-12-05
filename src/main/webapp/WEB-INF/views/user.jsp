<%@include file="header.jsp"%>
<div class="row">
	<div class="col-sm-2 col-sm-offset-1">
		<c:choose>
			<c:when test="${empty user.linkToPhoto}">
				<img
					src="<%=request.getContextPath()%>/resources/img/photos/anonymous.png"
					class="img-rounded" style="width: 100%;">
			</c:when>
			<c:otherwise>
				<img
					src="<%=request.getContextPath()%>/resources/img/photos/${user.linkToPhoto}"
					class="img-rounded" style="width: 100%;">
			</c:otherwise>
		</c:choose>
	</div>
	<div class="col-sm-6">
		<h1>${user.firstName} ${user.secondName}
			${user.lastName}
		</h1>
	</div>
	<div class="col-sm-6">
		<security:authorize access="hasAnyAuthority('admin','hr')">
			<div class="btn-group">
				<button type="button" class="btn btn-success dropdown-toggle"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					Get Report About Student Activity In Project <span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<c:forEach items="${projects}" var="project">
						<li><a
							href="<c:url value="/user/report/${user.id}/${project.id}/" />">
								${project.name} </a></li>
					</c:forEach>
				</ul>
			</div>
		</security:authorize>
	</div>

</div>
<security:authorize access="hasAnyAuthority('admin','hr')">
<div class="row">
	<div class="col-sm-offset-9 col-sm-2">
		<a class="btn btn-success"
			href="<c:url value="/hrFeedback/create/${user.id}/" />"> <i
			class="fa fa-plus-circle" aria-hidden="true"></i> Create feedback
		</a>
	</div>
</div>
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">
		<hr />
		<div class="div-table">
			<table id="hrFeedbackTable"
				class="table table-striped table-hover table-bordered"
				style="min-width: 100%;">
				<thead>
					<tr>
						<td data-orderable="false">Topic</td>
						<td data-orderable="false"></td>
						<td data-orderable="false"></td>
						<td data-orderable="false"></td>
					</tr>
				<thead />
				<tbody>
					<c:forEach items="${feedbacks}" var="feedback">
						<tr class="feed-tr-${feedback.id}">
							<td>${feedback.topic}</td>

							<td><a class="btn btn-xs btn-primary"
								href="<c:url value="/hrFeedback/view/${feedback.id}/" />">View</a>
							</td>
							<td><a class="btn btn-xs btn-warning"
								href="<c:url value="/hrFeedback/edit/${feedback.id}/" />">
									<i class="fa fa-pencil" aria-hidden="true"></i> Edit</a>
							</td>
							<td>
								<a class="btn btn-xs btn-danger"
								href="<c:url value="/hrFeedback/delete/${feedback.id}/" />">
									<i class="fa fa-trash" aria-hidden="true"></i> Delete</a>
							</td>
						</tr>
					</c:forEach>
			</table>
		</div>
	</div>
</div>
</security:authorize>
<script type="text/javascript">  $('#hrFeedbackTable').DataTable();</script>
<%@include file="footer.jsp"%>