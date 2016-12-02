<%@include file="header.jsp"%>

<c:set var="isUserChiefMentorOfProjectWithThisTeam" value="${false}"/>
<security:authorize access="@spmsWebSecurityService.isUserChiefMentorOfProjectWithTeam(principal, #team.id)">
	<c:set var="isUserChiefMentorOfProjectWithThisTeam" value="${true}"/>
</security:authorize>
<div class="row">
	<div class="col-sm-10 col-sm-offset-1">
		<h1>
			<p id="teamName">${team.name}</p>
			<security:authorize access="hasAuthority('admin') || ${isUserChiefMentorOfProjectWithThisTeam}">
				<div class="btn-group btn-group-sm" role="group" aria-label="...">
					<button type="button" class="btn btn-warning" data-toggle="modal"
						data-target="#editTeamNameModal">
						<i class="fa fa-pencil" aria-hidden="true"></i> Edit Team Name
					</button>
					<button type="button" class="btn btn-danger" data-toggle="modal"
						data-target="#deleteModal" id="deleteTeamButton"
						onclick="deleteInit('team', ${team.id});">
						<i class="fa fa-trash" aria-hidden="true"></i> Delete Team
					</button>
				</div>
			</security:authorize>
		</h1>
		<h6>
			<a href="<%=request.getContextPath()%>/project/view/${team.project.id}/"><--- Back to
				Project</a>
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
				<div class="panel-center">
					<security:authorize access="hasAnyAuthority('admin','mentor')">
						<button type="button" id="createMeetingButton"
							class="btn btn-success" data-toggle="modal"
							data-target="#createMeetingModal">
							<i class="fa fa-plus-circle" aria-hidden="true"></i> 
							Create meeting
						</button>
					</security:authorize>
				</div>
				<div class="div-table">
					<table id="meetingsTable"
						class="table table-striped table-hover table-bordered"
						style="min-width: 100%;">
						<thead>
							<tr>
								<td data-orderable="false">Topic</td>
								<td>Date</td>
								<td data-orderable="false"></td>
								<td data-orderable="false"></td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${team.meetings}" var="meeting">
								<tr class="meet-tr-${meeting.id}">
									<td>${meeting.topic}</td>
									<td>${meeting.startDate}</td>
									<td>
										<a class="btn btn-xs btn-warning" href="<c:url value="/meeting/view/${meeting.id}/" />">View</a>
									</td>
									<td>
									<security:authorize access="hasAnyAuthority('admin','mentor')">
										<c:choose>
											<c:when test="${team.project.isCompleted eq true}">
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('meeting', ${meeting.id});" 
													title="Cannot delete a meeting in completed project."
													disabled>
													<i class="fa fa-trash" aria-hidden="true"></i> Delete
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('meeting', ${meeting.id});">
													<i class="fa fa-trash" aria-hidden="true"></i> Delete
												</button>
											</c:otherwise>
										</c:choose>
									</security:authorize>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="tab-pane fade" id="files">
				<security:authorize access="hasAnyAuthority('admin', 'mentor')">
					<div class="panel-center">
						<button type="button" id="addFileButton" class="btn btn-success"
							data-toggle="modal" data-target="#addFileModal">
							<i class="fa fa-plus-circle" aria-hidden="true"></i> Add file
						</button>
					</div>
				</security:authorize>
				<div class="div-table">
					<c:forEach items="${team.files}" var="file">
						<p>
							${file.id}
							<c:choose>
								<c:when test="${team.project.isCompleted eq true}">
									<button type="button" class="btn btn-danger"
										data-toggle="modal" data-target="#deleteModal"
										onclick="deleteInit('file', ${file.id});" 
										title="Cannot delete a file in completed project."
										disabled>
										<i class="fa fa-trash" aria-hidden="true"></i> Delete
									</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-danger"
										data-toggle="modal" data-target="#deleteModal"
										onclick="deleteInit('file', ${file.id});">
										<i class="fa fa-trash" aria-hidden="true"></i> Delete
									</button>
								</c:otherwise>
							</c:choose>
						</p>
					</c:forEach>
				</div>
			</div>
			<div class="tab-pane fade" id="mentors">
				<security:authorize access="hasAuthority('admin') || ${isUserChiefMentorOfProjectWithThisTeam}">
					<div class="panel-center">
						<button type="button" id="addMentorButton" class="btn btn-success" 
							data-toggle="modal" data-target="#addMentorModal" 
							onclick="getFreeMentors();">
							<i class="fa fa-plus-circle" aria-hidden="true"></i> Add mentor
						</button>
					</div>
				</security:authorize>
				<hr>
				<div class="div-table">
					<div id="block-mentors" class="row">
						<c:forEach items="${team.members}" var="member">
							<c:if test="${member.key.role.name == 'mentor'}">
								<div class="col-lg-3 padtop" id="user-${member.key.id}">
									<c:choose>
										<c:when test="${empty member.key.linkToPhoto}">
											<img src="<%=request.getContextPath()%>/resources/img/anonymous.jpg"
												class="img-rounded" style="width: 150px; height: 150px">
										</c:when>
										<c:otherwise>
											<img src="${member.key.linkToPhoto}" class="img-rounded"
												style="width: 150px; height: 150px">
										</c:otherwise>
									</c:choose>
									<p style="padding-top: 10px">${member.key.firstName}
										${member.key.secondName}</p>
									<h6>${member.key.email}</h6>
									<p>
										<security:authorize access="hasAuthority('admin') || ${isUserChiefMentorOfProjectWithThisTeam}">
										<c:choose>
											<c:when test="${team.project.isCompleted eq true}">
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('user', ${member.key.id});" 
													title="Cannot delete a mentor in completed project."
													disabled>
													<i class="fa fa-trash" aria-hidden="true"></i> Delete
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('user', ${member.key.id});">
													<i class="fa fa-trash" aria-hidden="true"></i> Delete
												</button>
											</c:otherwise>
										</c:choose>
										</security:authorize>
									</p>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="students">
				<security:authorize access="hasAuthority('admin') || ${isUserChiefMentorOfProjectWithThisTeam}">
					<div class="panel-center">
						<button type="button" id="addStudentButton" class="btn btn-success" 
							data-toggle="modal" data-target="#addStudentModal" 
							onclick="getFreeStudents();">
							<i class="fa fa-plus-circle" aria-hidden="true"></i> Add student
						</button>
					</div>
				</security:authorize>
				<hr>
				<div class="div-table">
					<div id="block-students" class="row">
						<c:forEach items="${team.members}" var="member">
							<c:if test="${member.key.role.name == 'student'}">
								<div class="col-lg-3" id="user-${member.key.id}">
									
									<c:choose>
										<c:when test="${member.value.status.name == 'left_project'}">
											<div id="div-color-${member.key.id}" style="border-radius: 5px; margin-right: 15px; background-color: #DCDCDC; padding-top: 15px; padding-bottom: 5px; margin-bottom: 20px;">
										</c:when>
										<c:when test="${member.value.status.name == 'interview_was_scheduled'}">
											<div id="div-color-${member.key.id}" style="border-radius: 5px; margin-right: 15px; background-color: #D3FFAA; padding-top: 15px; padding-bottom: 5px; margin-bottom: 20px;">
										</c:when>
										<c:when test="${member.value.status.name == 'got_job_offer'}">
											<div id="div-color-${member.key.id}" style="border-radius: 5px; margin-right: 15px; background-color: #D8F2FF; padding-top: 15px; padding-bottom: 5px; margin-bottom: 20px;">
										</c:when>
										<c:otherwise>
											<div id="div-color-${member.key.id}" style="border-radius: 5px; margin-right: 15px; padding-top: 15px; padding-bottom: 5px; margin-bottom: 20px;">
										</c:otherwise>
									</c:choose>
								
									<c:choose>
										<c:when test="${empty member.key.linkToPhoto}">
											<img src="<%=request.getContextPath()%>/resources/img/anonymous.jpg"
												class="img-rounded" style="width: 150px; height: 150px">
										</c:when>
										<c:otherwise>
											<img src="${member.key.linkToPhoto}" class="img-rounded"
												style="width: 150px; height: 150px">
										</c:otherwise>
									</c:choose>
									<p style="padding-top: 10px">${member.key.firstName}
										${member.key.secondName}</p>
									<h6>${member.key.email}</h6>
									
									<c:choose>
										<c:when test="${member.value.status.name == 'left_project'}">
											<abbr id="tStatusName-${member.key.id}" title="${member.value.comment}" class="initialism">
												<p id="pStatusName-${member.key.id}">
													LEFT PROJECT <i class="fa fa-info-circle" aria-hidden="true"></i>
												</p>
											</abbr>
										</c:when>
										<c:when test="${member.value.status.name == 'interview_was_scheduled'}">
											<abbr id="tStatusName-${member.key.id}" title="${member.value.comment}" class="initialism">
												<p id="pStatusName-${member.key.id}">
													INTERVIEW WAS SCHEDULED <i class="fa fa-info-circle" aria-hidden="true"></i>
												</p>
											</abbr>
										</c:when>
										<c:when test="${member.value.status.name == 'got_job_offer'}">
											<abbr id="tStatusName-${member.key.id}" title="${member.value.comment}" class="initialism">
												<p id="pStatusName-${member.key.id}">
													GOT JOB OFFER <i class="fa fa-info-circle" aria-hidden="true"></i>
												</p>
											</abbr>
										</c:when>
										<c:otherwise>
											<abbr id="tStatusName-${member.key.id}" title="${member.value.comment}" class="initialism">
												<p id="pStatusName-${member.key.id}">
													ACTIVE <i class="fa fa-info-circle" aria-hidden="true"></i>
												</p>
											</abbr>
										</c:otherwise>
									</c:choose>
									<p style="padding-top: 10px">
										<button type="button" class="btn btn-xs btn-warning" id="butStatus-${member.key.id}"
													data-toggle="modal" data-target="#changeStatusModal"
													onclick="initStatusChanging(${member.key.id}, '${member.value.status.id}', '${member.value.comment}');">
													<i class="fa fa-pencil" aria-hidden="true"></i>Change status
										</button>
										<security:authorize access="hasAuthority('admin') || ${isUserChiefMentorOfProjectWithThisTeam}">
										<c:choose>
											<c:when test="${team.project.isCompleted eq true}">
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('user', ${member.key.id});" 
													title="Cannot delete a student in completed project."
													disabled>
													<i class="fa fa-trash" aria-hidden="true"></i> Delete
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('user', ${member.key.id});">
													<i class="fa fa-trash" aria-hidden="true"></i> Delete
												</button>
											</c:otherwise>
										</c:choose>
										</security:authorize>
									</p>
								</div></div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- editTeamNameModal -->
<div class="modal fade" id="editTeamNameModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Team Name</h4>
			</div>
			<form>
				<div class="modal-body">

					<div class="form-group">
						<label for="name">New Team Name</label> <input type="text"
							class="form-control" maxlength="255" id="newTeamName"
							placeholder="Enter new team name" value="${team.name}" required>
					</div>

				</div>
			</form>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-warning"
					onclick="updateTeamName(${team.id});">Update</button>
			</div>

		</div>
	</div>
</div>
<security:authorize access="hasAnyAuthority('admin','mentor')">
<!-- createMeetingModal -->
<div class="modal fade" id="createMeetingModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Create meeting</h4>
			</div>
			<form>
				<div class="modal-body">

					<div class="form-group">
						<label for="name">Topic:</label> <input type="text"
							class="form-control" maxlength="255" id="topic"
							placeholder="Enter topic" required>
					</div>

					<div class="form-group">
						<label for="startDate">Start date:</label>
						<div class='input-group date' id='datetimepicker'>
							<input type='text' class="form-control" name="startDate" 
								id="meetingStartDate" placeholder="Enter start date" required />
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
					
					<div class="checkbox">
  						<label>
  							<input id="repeat" type="checkbox" value="">Repeat every week till the end of the project
  						</label>
					</div>

					<div class="form-group">
	        			<label for="endDate">Project End Date:</label>
	            		<div id='datetimepicker2'>
	              			<input type='text' class="form-control" name="endDate"
	              			id="projectEndDate" required disabled/>
						</div>
					
					</div>
					
					<script type="text/javascript">
						$(function () {
						
							$('#datetimepicker').datetimepicker({format: 'DD/MM/YYYY hh:mm a'});
			        		$('#datetimepicker').data("DateTimePicker").maxDate(moment('${team.project.endDate}'));
			        		
							$('#datetimepicker2').datetimepicker({
								format: 'DD/MM/YYYY hh:mm a',
								useCurrent: false
							});

							$('#datetimepicker2').data("DateTimePicker").date(moment('${team.project.endDate}'));
						});
					</script>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary"
						onclick="generateMeetingCreating();">Create</button>
				</div>
			</form>
		</div>
	</div>
</div>
</security:authorize>
<!-- deleteModal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Deleting</h4>
			</div>
			<form>
				<div class="modal-body">

					<div class="form-group">
						<p>Are you sure?</p>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-danger"
						onclick="deleteFunc();">Delete</button>
				</div>
			</form>
		</div>
	</div>
</div>
<security:authorize access="hasAuthority('admin') || ${isUserChiefMentorOfProjectWithThisTeam}">
<!-- addMentorModal -->
<div class="modal fade" id="addMentorModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Add mentor</h4>
			</div>
			<form>
				<div class="modal-body">

					<div class="form-group">
						<table id="allMentorsTable"
							class="table table-striped table-hover table-bordered"
							style="min-width: 100%;">
							<thead>
								<tr>
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

<!-- addStudentModal -->
<div class="modal fade" id="addStudentModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Add student</h4>
			</div>
			<form>
				<div class="modal-body">

					<div class="form-group">
						<table id="allStudentsTable"
							class="table table-striped table-hover table-bordered"
							style="min-width: 100%;">
							<thead>
								<tr>
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
</security:authorize>

<!-- changeStatusModal -->
<div class="modal fade" id="changeStatusModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Change Status</h4>
			</div>
			<form>
				<div class="modal-body">

					<div class="form-group">
						<security:authorize access="hasAnyAuthority('admin','mentor')">
						<div class="radio">
  							<label><input type="radio" name="optradio" id="optradio1">Active</label>
						</div>
						<div class="radio">
  							<label><input type="radio" name="optradio" id="optradio2">Left Project</label>
						</div>
						</security:authorize>
						<security:authorize access="hasAnyAuthority('admin','hr') || ${isUserChiefMentorOfProjectWithThisTeam}">
						<div class="radio">
  							<label><input type="radio" name="optradio" id="optradio3">Interview was scheduled</label>
						</div>
						<div class="radio">
  							<label><input type="radio" name="optradio" id="optradio4">Got job offer</label>
						</div>
						</security:authorize>
					</div>
					
					<div class="form-group">
						<label for="name">Comment</label> 
						<input type="text" class="form-control" maxlength="255" id="newComment"
							placeholder="Enter comment">
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-warning"
						onclick="changeStatus();">Change</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>

$('#allMentorsTable').DataTable();
$('#allStudentsTable').DataTable();
var meetingsTable = $('#meetingsTable').DataTable();

var entityToDelete = "null";
var idToDelete = 0;

if ("${team.project.isCompleted}" === "true"){
	document.getElementById('deleteTeamButton').disabled = true;
	$("#deleteTeamButton").attr("title", "Cannot delete a team in completed project.");
	document.getElementById('createMeetingButton').disabled = true;
	$("#createMeetingButton").attr("title", "Cannot create a meeting in completed project.");
	document.getElementById('addFileButton').disabled = true;
	$("#addFileButton").attr("title", "Cannot add file to completed project.");
	document.getElementById('addMentorButton').disabled = true;
	$("#addMentorButton").attr("title", "Cannot add mentor to completed project.");
	document.getElementById('addStudentButton').disabled = true;
	$("#addStudentButton").attr("title", "Cannot add student to completed project.");
}

function deleteInit(entity, id) {
	entityToDelete = entity;
	idToDelete = id;
}

function deleteFunc(){
	
	$('#deleteModal').modal('hide');
	
	if (entityToDelete == 'user'){
		deleteUser();
	}
	else if (entityToDelete == 'meeting'){
		deleteMeeting();
	}
	else if (entityToDelete == 'team'){
		deleteTeam();
	}
	else {
		deleteFile();
	}
}

function getFreeMentors() {
	
	$("#allMentorsTable").dataTable().fnDestroy();
	$('#allMentorsTable').DataTable( {

	    serverSide: true,
        ajax: {
	        url: getContextPath()+'/user/view/free/mentor/',
	        type: 'POST',
	        data: function ( d ) {
			      return JSON.stringify( d );
			    },	        
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    dataSrc: function ( json ) {
			    for(var i=0, ien=json.data.length; i<ien ; i++ ) {
			        json.data[i]["role"] = '<button type="button" class="btn btn-xs btn-success" onclick="addMentor(' + json.data[i]["id"] + ');">Add</button>';
			    }
			    return json.data;
			}
	    },
	    columns: [
		    { data: "email" },
		    { data: "firstName" },
		    { data: "secondName" },
		    { data: "lastName" },
		    { data: "role" }
		  ]
	    
	} );
}

function getFreeStudents() {
	
	$("#allStudentsTable").dataTable().fnDestroy();
	$('#allStudentsTable').DataTable( {

	    serverSide: true,
        ajax: {
	        url: getContextPath()+'/user/view/free/student/',
	        type: 'POST',
	        data: function ( d ) {
			      return JSON.stringify( d );
			    },	        
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    dataSrc: function ( json ) {
			    for(var i=0, ien=json.data.length; i<ien ; i++ ) {
			    	json.data[i]["role"] = '<button type="button" class="btn btn-xs btn-success" onclick="addStudent(' + json.data[i]["id"] + ');">Add</button>';
				    }
			    return json.data;
			}
	    },
	    columns: [
		    { data: "email" },
		    { data: "firstName" },
		    { data: "secondName" },
		    { data: "lastName" },
		    { data: "role" }
		  ]
	    
	} );
}

function updateTeamName() {
	
	$('#editTeamNameModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var teamId = "${team.id}";
	var name = $('#newTeamName').val();

	$.ajax({
	    url: getContextPath()+"/team/update/",
	    data: {
	    	teamId: teamId,
	        name: name
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
		$('#teamName').text(name);
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});;
}

function deleteTeam() {
	
	$('#loadingModal').modal('show');

	var teamId = "${team.id}";
	var projectId = "${team.project.id}";
	
	$.ajax({
	    url: getContextPath()+"/team/delete/",
	    data: {
	        teamId: teamId
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
	    $('#loadingModal').modal('hide');
	    window.location = '<%=request.getContextPath()%>/project/view/' + projectId + '/';
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function generateMeetingCreating(){
	if ($("#repeat").prop('checked')){
		createMeetings();
	}
	else{
		createMeeting();
	}
}

function createMeeting() {
	
	$('#createMeetingModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var teamId = "${team.id}";
	var topic = $('#topic').val();
	var startDate = $('#meetingStartDate').val();

	$.ajax({
		url: getContextPath()+"/meeting/create/"	,
	    data: {
	    	teamId: teamId,
	    	topic: topic,
	    	startDate: startDate
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(meetingId) {
	    $('#loadingModal').modal('hide');
	    window.location = "<%=request.getContextPath()%>/meeting/view/" + meetingId + "/";
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function createMeetings() {
	
	$('#createMeetingModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var teamId = "${team.id}";
	var topic = $('#topic').val();
	var date = $("#meetingStartDate").val();
	var endDate = $("#projectEndDate").val();
	
	$.ajax({
		url: getContextPath()+"/meeting/createSeveral/"	,
	    data: {
	    	teamId: teamId,
	    	topic: topic,
	    	date: date,
	    	endDate: endDate
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(meetingId) {
	    $('#loadingModal').modal('hide');
	    window.location = "<%=request.getContextPath()%>/meeting/view/" + meetingId + "/";
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function deleteMeeting() {
	
	$('#loadingModal').modal('show');
	
	var id = idToDelete;

	$.ajax({
	    url: getContextPath()+"/meeting/delete/",
	    data: {
	        id: id
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
		meetingsTable.row('.meet-tr-' + id).remove().draw(false);
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function addMentor(userId) {
	
	$('#addMentorModal').modal('hide');
	$('#loadingModal').modal('show');

	var teamId = "${team.id}";
	
	$.ajax({
		url: getContextPath()+"/team/addUser/"	,
	    data: {
	    	userId: userId,
	    	teamId: teamId
	    },
	    type: "POST",
	    dataType : "json",
		timeout: 15000
	})
	.done(function(user) {
		var isPhoto = (user.linkToPhoto != null);
		var aaa = 'user';
		if (isPhoto) {
			$('#block-mentors').append('<div class="col-lg-3 padtop" id="user-' + user.id + '">\
									<img src="' + user.linkToPhoto + '" class="img-rounded" style="width:150px; height:150px">\
									<p style="padding-top: 10px">' + user.firstName + ' ' + user.secondName +'</p>\
									<h6>' + user.email + '</h6>\
									<p>\
										<button id="but' + user.id + '" type="button" class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal">\
										<i class="fa fa-trash" aria-hidden="true"></i>Delete</button>\
	  								</p>\
								  </div>');
		}
		else {
			$('#block-mentors').append('<div class="col-lg-3 padtop" id="user-' + user.id + '">\
									<img src="<%=request.getContextPath()%>/resources/img/anonymous.jpg" class="img-rounded" style="width:150px; height:150px">\
									<p style="padding-top: 10px">' + user.firstName + ' ' + user.secondName +'</p>\
									<h6>' + user.email + '</h6>\
									<p>\
										<button id="but' + user.id + '" type="button" class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal">\
										<i class="fa fa-trash" aria-hidden="true"></i>Delete</button>\
		  							</p>\
								</div>');
		}
		var element = document.getElementById('but' + user.id);
		element.onclick = function () {
			entityToDelete = 'user';
			idToDelete = userId;
		};
		$('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function addStudent(userId) {
	
	$('#addStudentModal').modal('hide');
	$('#loadingModal').modal('show');

	var teamId = "${team.id}";
	
	$.ajax({
		url: getContextPath()+"/team/addUser/"	,
	    data: {
	    	userId: userId,
	    	teamId: teamId
	    },
	    type: "POST",
	    dataType : "json",
		timeout: 15000
	})
	.done(function( user ) {
		var isPhoto = (user.linkToPhoto != null);
		if (isPhoto) {
			$('#block-students').append('<div class="col-lg-3" id="user-' + user.id + '">\
											<div id="div-color-' + user.id + '" style="border-radius: 5px; margin-right: 15px; padding-top: 15px; padding-bottom: 5px; margin-bottom: 20px;">\
												<img src="' + user.linkToPhoto + '" class="img-rounded" style="width:150px; height:150px">\
												<p style="padding-top: 10px">' + user.firstName + ' ' + user.secondName +'</p>\
													<h6>' + user.email + '</h6>\
													<abbr id="tStatusName-' + user.id + '" title="" class="initialism"><p id="pStatusName-' + user.id + '">ACTIVE</p></abbr>\
													<p style="padding-top: 10px">\
														<button type="button" class="btn btn-xs btn-warning" id="butStatus-' + user.id + '"\
															data-toggle="modal" data-target="#changeStatusModal">\
															<i class="fa fa-pencil" aria-hidden="true"></i>Change status\
														</button>\
														<button id="but' + user.id + '" type="button" class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal">\
														<i class="fa fa-trash" aria-hidden="true"></i>Delete</button>\
	  												</p>\
								  				</div>\
								  			</div>');
		}
		else {
			$('#block-students').append('<div class="col-lg-3" id="user-' + user.id + '">\
											<div id="div-color-' + user.id + '" style="border-radius: 5px; margin-right: 15px; padding-top: 15px; padding-bottom: 5px; margin-bottom: 20px;">\
												<img src="<%=request.getContextPath()%>/resources/img/anonymous.jpg" class="img-rounded" style="width:150px; height:150px">\
												<p style="padding-top: 10px">' + user.firstName + ' ' + user.secondName +'</p>\
													<h6>' + user.email + '</h6>\
													<abbr id="tStatusName-' + user.id + '" title="" class="initialism"><p id="pStatusName-' + user.id + '">ACTIVE</p></abbr>\
													<p style="padding-top: 10px">\
														<button type="button" class="btn btn-xs btn-warning" id="butStatus-' + user.id + '"\
															data-toggle="modal" data-target="#changeStatusModal">\
															<i class="fa fa-pencil" aria-hidden="true"></i>Change status\
														</button>\
														<button id="but' + user.id + '" type="button" class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal">\
														<i class="fa fa-trash" aria-hidden="true"></i>Delete</button>\
	  												</p>\
												</div>\
											</div>');
		}
		var element = document.getElementById('but' + user.id);
		element.onclick = function () {
			entityToDelete = 'user';
			idToDelete = userId;
		};
		var element = document.getElementById('butStatus-' + userId);
		element.onclick = function () {
			initStatusChanging(userId, 0, "");
		};
		$('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

function deleteUser() {
	
	$('#loadingModal').modal('show');
	
	var userId = idToDelete;
	var teamId = "${team.id}";
	
	$.ajax({
	    url: getContextPath()+"/team/deleteUser/",
	    data: {
	    	userId: userId,
	    	teamId: teamId
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
		$('#user-'+ userId).remove();
	    $('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

var idStatChange = 0;

function initStatusChanging(id, status, comment){
	idStatChange = id;
	if (status == 0 && document.getElementById("optradio1") !== null){
		document.getElementById("optradio1").checked = true;
	}
	else if (status == 1 && document.getElementById("optradio1") !== null){
		document.getElementById("optradio2").checked = true;
	}
	else if (status == 2){
		document.getElementById("optradio3").checked = true;
	}
	else {
		document.getElementById("optradio4").checked = true;
	}
	document.getElementById('newComment').value = comment;
}

function changeStatus(){
	
	$('#changeStatusModal').modal('hide');
	$('#loadingModal').modal('show');
	
	var teamId = "${team.id}";
	var userId = idStatChange;
	var newStatus = -1;
	if (document.getElementById("optradio1") !== null && document.getElementById("optradio1").checked == true){
		newStatus = 0;
	}
	else if (document.getElementById("optradio1") !== null && document.getElementById("optradio2").checked == true){
		newStatus = 1;
	}
	else if (document.getElementById("optradio3").checked == true){
		newStatus = 2;
	}
	else {
		newStatus = 3;
	}
	var newComment = $('#newComment').val();
	
	$.ajax({
	    url: getContextPath()+"/user/changeStatus/",
	    data: {
	    	teamId: teamId,
	    	userId: userId,
	    	newStatus: newStatus,
	    	newComment: newComment
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
		if (newStatus == 0){
			document.getElementById("div-color-" + userId).style.backgroundColor = '#FFFFFF';
			document.getElementById("pStatusName-" + userId).innerHTML = "ACTIVE";
		}
		if (newStatus == 1){
			document.getElementById("div-color-" + userId).style.backgroundColor = '#DCDCDC';
			document.getElementById("pStatusName-" + userId).innerHTML = "LEFT PROJECT";
		}
		if (newStatus == 2){
			document.getElementById("div-color-" + userId).style.backgroundColor = '#D3FFAA';
			document.getElementById("pStatusName-" + userId).innerHTML = "INTERVIEW WAS SCHEDULED";
		}
		if (newStatus == 3){
			document.getElementById("div-color-" + userId).style.backgroundColor = '#D8F2FF';
			document.getElementById("pStatusName-" + userId).innerHTML = "GOT JOB OFFER";
		}
		document.getElementById('tStatusName-' + userId).title = newComment;
		var element = document.getElementById('butStatus-' + userId);
		element.onclick = function () {
			initStatusChanging(userId, newStatus, newComment);
		};
		$('#loadingModal').modal('hide');
	})
	.fail(function( xhr, status, errorThrown ) {
		$('#loadingModal').modal('hide');
		$('#networkErrorModal').modal('show');
	})
	.always(function( xhr, status ) {
	});
}

</script>

<%@include file="commonModalPopUps.jsp"%>
<%@include file="footer.jsp"%>