<%@include file="header.jsp"%>
	<div class="row">
	<div class="col-sm-2 col-sm-offset-1">
		<c:choose>
			<c:when test="${empty user.linkToPhoto}">
				<img src="<%=request.getContextPath()%>/resources/img/photos/anonymous.png" class="img-rounded" style="width: 100%;">
			</c:when>
			<c:otherwise>
				<img src="<%=request.getContextPath()%>/resources/img/photos/${user.linkToPhoto}" class="img-rounded" style="width: 100%;">
			</c:otherwise>
		</c:choose>
		
	</div>
	<div class="col-sm-8">
		<h1>${user.firstName} ${user.secondName} ${user.lastName}
		</h1>
	</div>
	
	<security:authorize access="hasAuthority('hr')">
		<div class="btn-group">
			<button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					View Feedback Of
					<span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<c:forEach items="${hrFeedbacksOnStudents[loop.index]}"
					var="hrFeedback">
					<li>
						<a href="<c:url value="/hrFeedback/view/${hrFeedback.id}/" />">
							${hrFeedback.author.firstName}
							${hrFeedback.author.lastName}
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</security:authorize>

	
</div>
<%@include file="footer.jsp"%>