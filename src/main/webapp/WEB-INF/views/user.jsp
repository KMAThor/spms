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
	
	<security:authorize access="hasAnyAuthority('admin','hr')">
		<div class="btn-group">
			<button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					Report
					<span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<c:forEach items="${projects}" var="project">
					<li>
						<a href="<c:url value="/user/report/${user.id}/${project.id}/" />">
							${project.name}
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
    </security:authorize>
	
</div>
<%@include file="footer.jsp"%>