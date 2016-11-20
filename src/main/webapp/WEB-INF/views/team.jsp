<%@include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<h1>
				<p id="teamName">${team.name}</p>
				<security:authorize access="hasAuthority('admin')">
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
				</security:authorize>
			</h1>
			<h6>
				<a href="/spms/project/view/${team.project.id}/"><--- Back to Project</a>
			</h6>
		</div>
	</div>
	
	<hr>
	
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
		
			<ul class="nav nav-tabs">
				<li class="active"><a href="#meetings" data-toggle="tab">Meetings</a></li>
				<li><a href="#files" data-toggle="tab">Files</a></li>
  				<li><a href="#mentors" data-toggle="tab">Mentors</a></li>
  				<li><a href="#students" data-toggle="tab">Students</a></li>
  				
			</ul>

			<div class="tab-content padtop">
				<div class="tab-pane fade in active" id="meetings">
  					<div  class="panel-center">
  						<security:authorize access="hasAuthority('admin')">
  							<button type="button" class="btn btn-success"
								data-toggle="modal" data-target="#createMeetingModal">
								<i class="fa fa-plus-circle" aria-hidden="true"></i>
								Create meeting
							</button>
						</security:authorize>
					</div>
  					<div class="div-table">
						<table id="meetingsTable" class="table table-striped table-hover table-bordered" style="min-width: 100%;">
							<thead>
								<tr>
									<td data-orderable="false">Topic</td>
									<td>Date</td>
									<td data-orderable="false"></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${meetings}" var="meeting">
									<tr class="tr-${meeting.id}">
   										<td><a href="<c:url value="/meeting/view/${meeting.id}/" />">${meeting.topic}</a></td>
   										<td>${meeting.startDate}</td>
   										<td>
   											<button type="button" class="btn btn-danger"
   												data-toggle="modal" data-target="#deleteMeetingModal"
				  		  						onclick="attachIdMeet(${meeting.id});">
				  								<i class="fa fa-pencil" aria-hidden="true"></i>
				  								Delete
				  							</button>
										</td>
   									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="tab-pane fade" id="files">
  					<security:authorize access="hasAuthority('admin')">
  						<div  class="panel-center">
  							<button type="button" class="btn btn-success"
								data-toggle="modal" data-target="#addFileModal">
								<i class="fa fa-plus-circle" aria-hidden="true"></i>
								Add file
							</button>
						</div>
					</security:authorize>
					<div class="div-table">
						<c:forEach items="${files}" var="file">
   							<p>
   								${file.id}
   								<button type="button" class="btn btn-warning"
   									data-toggle="modal" data-target="#deleteFileModal"
				  		  			onclick="attachIdFile(${file.id});"
				  		  			>
				  					<i class="fa fa-pencil" aria-hidden="true"></i>
				  					Delete
				  				</button>
   							</p>
   						</c:forEach>
					</div>
  				</div>
  				<div class="tab-pane fade" id="mentors">
  					<security:authorize access="hasAuthority('admin')">
  						<div  class="panel-center">
  							<button type="button" class="btn btn-success"
								data-toggle="modal" data-target="#addMentorModal">
								<i class="fa fa-plus-circle" aria-hidden="true"></i>
								Add mentor
							</button>
						</div>
					</security:authorize>
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
						</table>
					</div>
  				</div>
  				<div class="tab-pane fade" id="students">
  					<security:authorize access="hasAuthority('admin')">
  						<div class="panel-center">
  							<button type="button" class="btn btn-success"
								data-toggle="modal" data-target="#addStudentModal">
								<i class="fa fa-plus-circle" aria-hidden="true"></i>
								Add student
							</button>
						</div>
					</security:authorize>
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
	      		<form>
	      			<div class="modal-body">
	        			
						<div class="form-group">
							<label for="name">New Team Name</label>
				    		<input type="text" class="form-control" name="name" id="newTeamName" placeholder="Enter new team name" value="${team.name}" required>
						</div>
				
	      			</div>
	      		</form>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        			<button type="button" class="btn btn-warning" onclick="updateTeamName(${team.id});">Update</button>
	     			</div>
	      		
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
	      		<form>
	      			<div class="modal-body">
	        
						<div class="form-group">
							<p>Are you sure?</p>
							<input type="hidden" name="project_id" id="project_id" value="${team.project.id}">
				    	</div>

	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        			<button type="button" class="btn btn-danger" onclick="deleteTeam(${team.id});">Delete</button>
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
	      <form>
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
		        
				<input type="hidden" name="team_id" id="team_id" value="${team.id}">
				
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
	        <button type="button" class="btn btn-primary" onclick="createMeeting();">Create</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	<!-- deleteMeetingModal -->
	<div class="modal fade" id="deleteMeetingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
	    	<div class="modal-content">
	      		<div class="modal-header">
	        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        		<h4 class="modal-title" id="myModalLabel">Delete Meeting</h4>
	      		</div>
	      		<form>
	      			<div class="modal-body">
	        
						<div class="form-group">
							<p>Are you sure?</p>
				    	</div>

	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        			<button type="button" class="btn btn-danger" onclick="deleteMeetingT();">Delete</button>
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
	
	<!-- loadingModal -->
	<div class="modal fade" data-backdrop="static" data-keyboard="false" id="loadingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="text-center">
			<img src="/spms/resources/img/loading.gif" width="50px">
	    </div>
	  </div>
	</div>

	<div class="modal fade" data-backdrop="static" data-keyboard="false" id="networkErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="text-center">
			<p class="errorMessageInModal">Network error, please check your internet connection, reload page and try again.</p>
			<p class="errorMessageInModal">If error still occur contact our support team via support.spms@gmail.com</p>
	    </div>
	  </div>
	</div>
	
	
<script>

	$('#mentorsTable').DataTable();
	$('#studentsTable').DataTable();
	$('#allMentorsTable').DataTable();	
	$('#allStudentsTable').DataTable();
	var meetingsTable = $('#meetingsTable').DataTable();
	var idDelMeet = 0;
	var idDelFile = 0;

</script>
<%@include file="footer.jsp"%>