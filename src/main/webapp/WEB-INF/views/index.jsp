<%@include file="header.jsp"%>
	<div  class="panel-center">
		<h1>Welcome to Student Practice Management System!</h1>
	</div>
	<hr>
	<div  class="panel-center">
		<h3>
			Active projects
			<button type="button" class="btn btn-success"
					data-toggle="modal" data-target="#createProjectModal">
				<i class="fa fa-plus-circle" aria-hidden="true"></i>
				Create project
			</button>
		</h3>
	</div>
	
	<div class="div-table">
		<table id="projectsTable" class="table">
			<thead>
				<tr>
					<td>Id</td>
					<td>Name</td>
					<td>Start Date</td>
					<td>End Date</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${projects}" var="project">
					<tr>
						<td>${project.id}</td> <!-- delete!!! -->
						<td><a href="<c:url value="/project/${project.id}" />">${project.name}</a></td>
						<td>${project.startDate}</td>
						<td>${project.endDate}</td>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<!-- createProjectModal -->
	<div class="modal fade" id="createProjectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Create Project</h4>
	      </div>
	      <div class="modal-body">
	        <form>
				<div class="form-group">
					<label for="newCategoryName">Project name:</label>
				    <input type="text" class="form-control" id="newProjectName" placeholder="Enter new project name">
				</div>
				<div class="form-group">
					<label for="newCategoryName">Project description:</label>
				    <textarea class="form-control" rows="3" id="description" placeholder="Enter project description"></textarea>
				</div>
				<div class="form-group">
					<label for="newCategoryName">Start date:</label>
				    <input type="text" class="form-control" id="startDate" placeholder="Enter start date">
				</div>
				<div class="form-group">
					<label for="newCategoryName">End Date:</label>
				    <input type="text" class="form-control" id="endDate" placeholder="Enter end date">
				</div>
			</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        <button type="button" class="btn btn-primary" onclick="createProject();">Create</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
    <!-- <img src="resources/thor.png">${greeting} . Our system has ${numberOfUsers} users. -->
    <script>
    $('#projectsTable').DataTable();
    </script>
<%@include file="footer.jsp"%>