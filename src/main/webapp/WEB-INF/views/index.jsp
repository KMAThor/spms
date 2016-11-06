<%@include file="header.jsp"%>
	<div  class="panel-center">
		<h1>Welcome to Student Practice Management System!</h1>
	</div>
	<hr>
	<div  class="panel-center">
		<h3>Active projects:</h3>
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
	<!-- 
    <div class="container">
    <hr>
    	<div class="row">
    		<div class="col-lg-3">
    			<div class="mypanel">
    				<div class="panel-body panel-center">
						<div style="width: 100%; height: 240px; overflow: hidden;" >
    						<a href="">
    							<img src="./resources/img/create_project.png" class="img-responsive">
    						</a>
    					</div>
    					<div class="clearfix" style="width: 100%; height: 60px; overflow: hidden;">
    						<a href="">
    							<h3>Create Project</h3>
    						</a>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="col-lg-3">
    			<div class="mypanel">
    				<div class="panel-body panel-center">
						<div style="width: 100%; height: 240px; overflow: hidden;" >
    						<a href="">
    							<img src="./resources/img/view_projects.png" class="img-responsive">
    						</a>
    					</div>
    					<div class="clearfix" style="width: 100%; height: 60px; overflow: hidden;">
    						<a href="">
    							<h3>View Projects</h3>
    						</a>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="col-lg-3">
    			<div class="mypanel">
    				<div class="panel-body panel-center">
						<div style="width: 100%; height: 240px; overflow: hidden;" >
    						<a href="">
    							<img src="./resources/img/create_team.png" class="img-responsive">
    						</a>
    					</div>
    					<div class="clearfix" style="width: 100%; height: 60px; overflow: hidden;">
    						<a href="">
    							<h3>Create Team</h3>
    						</a>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="col-lg-3">
    			<div class="mypanel">
    				<div class="panel-body panel-center">
						<div style="width: 100%; height: 240px; overflow: hidden;" >
    						<a href="">
    							<img src="./resources/img/view_teams.png" class="img-responsive">
    						</a>
    					</div>
    					<div class="clearfix" style="width: 100%; height: 60px; overflow: hidden;">
    						<a href="">
    							<h3>View Teams</h3>
    						</a>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
    	<hr>
    	<div class="row">
    		<div class="col-lg-3">
    			<div class="mypanel">
    				<div class="panel-body panel-center">
						<div style="width: 100%; height: 240px; overflow: hidden;" >
    						<a href="">
    							<img src="./resources/img/view_schedule.png" class="img-responsive">
    						</a>
    					</div>
    					<div class="clearfix" style="width: 100%; height: 60px; overflow: hidden;">
    						<a href="">
    							<h3>View Schedule</h3>
    						</a>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="col-lg-3">
    			<div class="mypanel">
    				<div class="panel-body panel-center">
						<div style="width: 100%; height: 240px; overflow: hidden;" >
    						<a href="">
    							<img src="./resources/img/make_a_review.png" class="img-responsive">
    						</a>
    					</div>
    					<div class="clearfix" style="width: 100%; height: 60px; overflow: hidden;">
    						<a href="">
    							<h3>Make a Review</h3>
    						</a>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="col-lg-3">
    			<div class="mypanel">
    				<div class="panel-body panel-center">
						<div style="width: 100%; height: 240px; overflow: hidden;" >
    						<a href="">
    							<img src="./resources/img/upload_file.png" class="img-responsive">
    						</a>
    					</div>
    					<div class="clearfix" style="width: 100%; height: 60px; overflow: hidden;">
    						<a href="">
    							<h3>Upload File</h3>
    						</a>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="col-lg-3">
    			<div class="mypanel">
    				<div class="panel-body panel-center">
						<div style="width: 100%; height: 240px; overflow: hidden;" >
    						<a href="">
    							<img src="./resources/img/view_students.png" class="img-responsive">
    						</a>
    					</div>
    					<div class="clearfix" style="width: 100%; height: 60px; overflow: hidden;">
    						<a href="">
    							<h3>View Students</h3>
    						</a>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
    <hr>
    </div> -->
    <!-- <img src="resources/thor.png">${greeting} . Our system has ${numberOfUsers} users. -->
    <script>
    $('#projectsTable').DataTable();
    </script>
<%@include file="footer.jsp"%>