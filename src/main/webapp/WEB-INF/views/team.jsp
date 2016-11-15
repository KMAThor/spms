<%@include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<h1>${team.name}
		    	<div class="btn-group btn-group-sm" role="group" aria-label="..."  >
				  	<button type="button" class="btn btn-warning"
				  		  data-toggle="modal" data-target="#editTeamNameModal"
				  		  >
				  		<i class="fa fa-pencil" aria-hidden="true"></i>
				  		Edit Team Name
				  	</button>
				  	<button type="button" class="btn btn-danger"
				  		  data-toggle="modal" data-target="#deleteTeamModal"
				  		  >
				  		<i class="fa fa-pencil" aria-hidden="true"></i>
				  		Delete Team
				  	</button>
				</div>
			</h1>
			<h6><a href="/spms/view/project/${team.project.id}/"><--- Back to Project</a></h6>
		</div>
	</div>
	<hr>
	
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
		
			<ul class="nav nav-tabs">
  				<li class="active"><a href="#mentors" data-toggle="tab">Mentors</a></li>
  				<li><a href="#students" data-toggle="tab">Students</a></li>
  				<li><a href="#meetings" data-toggle="tab">Meetings</a></li>
			</ul>

			<div class="tab-content padtop">
  				<div class="tab-pane fade in active" id="mentors">
  					<div  class="panel-center">
  						<button type="button" class="btn btn-success"
						data-toggle="modal" data-target="#addMentorModal">
							<i class="fa fa-plus-circle" aria-hidden="true"></i>
							Add mentor
						</button>
					</div>
					<div class="div-table">
						<table id="mentorsTable" class="table table-striped table-hover table-bordered" style="min-width: 100%;">
							<thead>
								<tr>
									<td>Id</td>
									<td>Email</td>
									<td>First Name</td>
									<td>Second Name</td>
									<td>Last Name</td>
									<td data-orderable="false"></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${users}" var="user">
									<c:if test="${user.role.id == 1}">
										<tr>
   											<td>${user.id}</td>
   											<td>${user.email}</td>
   											<td>${user.firstName}</td>
   											<td>${user.secondName}</td>
   											<td>${user.lastName}</td>
   											<td>
   												<a href="<c:url value="/${team.id}/deleteUser/${user.id}/" />"
													data-original-title="Delete" data-toggle="tooltip"
													type="button" class="btn btn-sm btn-danger">Delete
													<i class="glyphicon glyphicon-remove"></i>
												</a>
											</td>
   										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
  				</div>
  				<div class="tab-pane fade" id="students">
  					<div class="panel-center">
  						<button type="button" class="btn btn-success"
						data-toggle="modal" data-target="#addStudentModal">
							<i class="fa fa-plus-circle" aria-hidden="true"></i>
							Add student
						</button>
					</div>
					<div class="div-table">
						<table id="studentsTable" class="table table-striped table-hover table-bordered" style="min-width: 100%;">
							<thead>
								<tr>
									<td>Id</td>
									<td>Email</td>
									<td>First Name</td>
									<td>Second Name</td>
									<td>Last Name</td>
									<td data-orderable="false"></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${users}" var="user">
									<c:if test="${user.role.id == 3}">
										<tr>
   											<td>${user.id}</td>
   											<td>${user.email}</td>
   											<td>${user.firstName}</td>
   											<td>${user.secondName}</td>
   											<td>${user.lastName}</td>
   											<td>
   												<a href="<c:url value="/${team.id}/deleteUser/${user.id}/" />"
													data-original-title="Delete" data-toggle="tooltip"
													type="button" class="btn btn-sm btn-danger">Delete
													<i class="glyphicon glyphicon-remove"></i>
												</a>
											</td>
   										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
  				</div>
  				<div class="tab-pane fade" id="meetings">
  					<div  class="panel-center">
  						<button type="button" class="btn btn-success"
						data-toggle="modal" data-target="#createMeetingModal">
							<i class="fa fa-plus-circle" aria-hidden="true"></i>
							Create meeting
						</button>
					</div>
  					<div class="div-table">
						<table id="meetingsTable" class="table table-striped table-hover table-bordered" style="min-width: 100%;">
							<thead>
								<tr>
									<td>ID</td>
									<td data-orderable="false">Topic</td>
									<td>Date</td>
									<td data-orderable="false"></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${meetings}" var="meeting">
									<tr>
   										<td>${meeting.id}</td>
   										<td>${meeting.topic}</td>
   										<td>${meeting.startDate}</td>
   										<td>
   											<a href="<c:url value="/${meeting.id}/delete/meeting" />"
												data-original-title="Delete" data-toggle="tooltip"
												type="button" class="btn btn-sm btn-danger">Delete
												<i class="glyphicon glyphicon-remove"></i>
											</a>
										</td>
   									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
  			</div>
		</div>
	</div>

	<!-- editTeamNameModal -->
	<div class="modal fade" id="editTeamNameModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title" id="myModalLabel">Edit Team Name</h4>
	      		</div>
	      		<form name="createProjectForm" id="createProjectForm" action="/spms/update/team/${team.id}/" method="post">
	      			<div class="modal-body">
	        
						<div class="form-group">
							<label for="name">New Team Name</label>
				    		<input type="text" class="form-control" name="name" id="newTeamName" placeholder="Enter new team name" value="${team.name}" required>
						</div>

	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        			<button type="submit" value="Submit" class="btn btn-warning" >Update</button>
	     			</div>
	      		</form>
	    	</div>
	  	</div>
	</div>
	
	<!-- deleteTeamModal -->
	<div class="modal fade" id="deleteTeamModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title" id="myModalLabel">Delete Team</h4>
	      		</div>
	      		<form name="deleteTeamForm" id="deleteTeamForm" action="/spms/delete/team/${team.id}/" method="post">
	      			<div class="modal-body">
	        
						<div class="form-group">
							<label>Are you sure to delete team ${team.name}???</label>
							<input type="hidden" name="project_id" value="${team.project.id}">
				    	</div>

	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        			<button type="submit" value="Submit" class="btn btn-danger" >Delete</button>
	     			</div>
	      		</form>
	    	</div>
	  	</div>
	</div>
	
	<!-- addMentorToTeamModal -->
	<div class="modal fade" id="addMentorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Add mentor</h4>
	      </div>
	      <form name="addMentorToTeamForm" id="addMentorToTeamForm" method="post">
	      	<div class="modal-body">
	        
				<div class="form-group">
				    <table id="allMentorsTable" class="table table-striped table-hover table-bordered" style="min-width: 100%;">
							<thead>
								<tr>
									<td>Id</td>
									<td>Email</td>
									<td>First Name</td>
									<td>Second Name</td>
									<td>Last Name</td>
									<td data-orderable="false"></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${all_users}" var="user">
									<c:if test="${user.role.id == 1}">
										<tr>
   											<td>${user.id}</td>
   											<td>${user.email}</td>
   											<td>${user.firstName}</td>
   											<td>${user.secondName}</td>
   											<td>${user.lastName}</td>
   											<td>
   												<a href="<c:url value="/${team.id}/addUser/${user.id}/" />"
													data-original-title="Edit" data-toggle="tooltip"
													type="button" class="btn btn-sm btn-warning">Add
													<i class="glyphicon glyphicon-edit"></i>
												</a>
   											</td>
   										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
				</div>

	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	<!-- addStudentToTeamModal -->
	<div class="modal fade" id="addStudentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Add student</h4>
	      </div>
	      <form name="addMeetingForm" id="addMeetingForm" onsubmit="onSubmitAddMeetingForm();"
	        	action="/spms/${team.id}/add/meeting/" method="post">
	      	<div class="modal-body">
	        
				<div class="form-group">
				    <table id="allStudentsTable" class="table table-striped table-hover table-bordered" style="min-width: 100%;">
							<thead>
								<tr>
									<td>Id</td>
									<td>Email</td>
									<td>First Name</td>
									<td>Second Name</td>
									<td>Last Name</td>
									<td data-orderable="false"></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${all_users}" var="user">
									<c:if test="${user.role.id == 3}">
										<tr>
   											<td>${user.id}</td>
   											<td>${user.email}</td>
   											<td>${user.firstName}</td>
   											<td>${user.secondName}</td>
   											<td>${user.lastName}</td>
   											<td>
   												<a href="<c:url value="/${team.id}/addUser/${user.id}/" />"
													data-original-title="Edit" data-toggle="tooltip"
													type="button" class="btn btn-sm btn-warning">Add
													<i class="glyphicon glyphicon-edit"></i>
												</a>
   											</td>
   										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
				</div>

	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	<!-- createMeetingModal -->
	<div class="modal fade" id="createMeetingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Create meeting</h4>
	      </div>
	      <form name="createMeetingForm" id="createMeetinhForm" onsubmit="onSubmitCreateMeetingForm();"
	        	action="/spms/${team.id}/create/meeting/" method="post">
	      	<div class="modal-body">
	        
				<div class="form-group">
					<label for="name">Topic:</label>
				    <input type="text" class="form-control" name="topic" id="topic" placeholder="Enter topic" required>
				</div>

		        <div class="form-group">
		        	<label for="startDate">Start date:</label>
		            <div class='input-group date' id='datetimepicker'>
		                <input type='text' class="form-control" name="startDate" id="meetingStartDate" placeholder="Enter start date" required/>
		                <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
		        </div>
			
				<script type="text/javascript">
				    $(function () {
				        $('#datetimepicker').datetimepicker({
				            useCurrent: false
				        });
				    });
				</script>
			
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        <button type="submit" value="Submit" class="btn btn-primary" >Create</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
<script>

	$('#mentorsTable').DataTable();
	$('#studentsTable').DataTable();
	$('#allMentorsTable').DataTable();	
	$('#allStudentsTable').DataTable();
	$('#meetingsTable').DataTable();

</script>
<%@include file="footer.jsp"%>