<%@include file="header.jsp"%>
<div class="row">
	<div class="col-sm-10 col-sm-offset-1">
		<h1>
			<p id="teamName">${team.name}</p>
			<security:authorize access="hasAuthority('admin')">
				<div class="btn-group btn-group-sm" role="group" aria-label="...">
					<button type="button" class="btn btn-warning" data-toggle="modal"
						data-target="#editTeamNameModal">
						<i class="fa fa-pencil" aria-hidden="true"></i> Edit Team Name
					</button>
					<button type="button" class="btn btn-danger" data-toggle="modal"
						data-target="#deleteModal" id="deleteTeamButton"
						onclick="deleteInit('team', ${team.id});">
						<i class="fa fa-pencil" aria-hidden="true"></i> Delete Team
					</button>
				</div>
			</security:authorize>
		</h1>
		<h6>
			<a href="/spms/project/view/${team.project.id}/"><--- Back to
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
										<c:choose>
											<c:when test="${team.project.isCompleted eq true}">
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('meeting', ${meeting.id});" 
													title="Cannot delete a meeting in completed project."
													disabled>
													<i class="fa fa-pencil" aria-hidden="true"></i> Delete
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('meeting', ${meeting.id});">
													<i class="fa fa-pencil" aria-hidden="true"></i> Delete
												</button>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="tab-pane fade" id="files">
				<security:authorize access="hasAuthority('admin')">
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
										<i class="fa fa-pencil" aria-hidden="true"></i> Delete
									</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-danger"
										data-toggle="modal" data-target="#deleteModal"
										onclick="deleteInit('file', ${file.id});">
										<i class="fa fa-pencil" aria-hidden="true"></i> Delete
									</button>
								</c:otherwise>
							</c:choose>
						</p>
					</c:forEach>
				</div>
			</div>
			<div class="tab-pane fade" id="mentors">
				<security:authorize access="hasAuthority('admin')">
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
											<img src="/spms/resources/img/anonymous.jpg"
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
										<c:choose>
											<c:when test="${team.project.isCompleted eq true}">
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('user', ${member.key.id});" 
													title="Cannot delete a mentor in completed project."
													disabled>
													<i class="fa fa-pencil" aria-hidden="true"></i> Delete
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('user', ${member.key.id});">
													<i class="fa fa-pencil" aria-hidden="true"></i> Delete
												</button>
											</c:otherwise>
										</c:choose>
									</p>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="students">
				<security:authorize access="hasAuthority('admin')">
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
								<div class="col-lg-3 padtop" id="user-${member.key.id}">
									<c:choose>
										<c:when test="${empty member.key.linkToPhoto}">
											<img src="/spms/resources/img/anonymous.jpg"
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
										<c:choose>
											<c:when test="${team.project.isCompleted eq true}">
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('user', ${member.key.id});" 
													title="Cannot delete a student in completed project."
													disabled>
													<i class="fa fa-pencil" aria-hidden="true"></i> Delete
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-xs btn-danger"
													data-toggle="modal" data-target="#deleteModal"
													onclick="deleteInit('user', ${member.key.id});">
													<i class="fa fa-pencil" aria-hidden="true"></i> Delete
												</button>
											</c:otherwise>
										</c:choose>
									</p>
								</div>
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
	        url: '/spms/user/view/free/mentor/',
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
	        url: '/spms/user/view/free/student/',
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
	
	var team_id = "${team.id}";
	var name = $('#newTeamName').val();

	$.ajax({
	    url: "/spms/team/update/",
	    data: {
	    	team_id: team_id,
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

	var team_id = "${team.id}";
	var project_id = "${team.project.id}";
	
	$.ajax({
	    url: "/spms/team/delete/",
	    data: {
	        team_id: team_id
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
	    $('#loadingModal').modal('hide');
	    window.location = '/spms/project/view/' + project_id + '/';
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
	
	var team_id = "${team.id}";
	var topic = $('#topic').val();
	var start_date = $('#meetingStartDate').val();

	$.ajax({
		url: "/spms/meeting/create/"	,
	    data: {
	    	team_id: team_id,
	    	topic: topic,
	    	start_date: start_date
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(meeting_id) {
	    $('#loadingModal').modal('hide');
	    window.location = "/spms/meeting/view/" + meeting_id + "/";
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
	
	var team_id = "${team.id}";
	var topic = $('#topic').val();
	var date = $("#meetingStartDate").val();
	var end_date = $("#projectEndDate").val();
	
	$.ajax({
		url: "/spms/meeting/createSeveral/"	,
	    data: {
	    	team_id: team_id,
	    	topic: topic,
	    	date: date,
	    	end_date: end_date
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(meeting_id) {
	    $('#loadingModal').modal('hide');
	    window.location = "/spms/meeting/view/" + meeting_id + "/";
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
	    url: "/spms/meeting/delete/",
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

function addMentor(user_id) {
	
	$('#addMentorModal').modal('hide');
	$('#loadingModal').modal('show');

	var team_id = "${team.id}";
	
	$.ajax({
		url: "/spms/team/addUser/"	,
	    data: {
	    	user_id: user_id,
	    	team_id: team_id
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
										<button id="but' + user.id + '" type="button" class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button>\
	  								</p>\
								  </div>');
		}
		else {
			$('#block-mentors').append('<div class="col-lg-3 padtop" id="user-' + user.id + '">\
									<img src="/spms/resources/img/anonymous.jpg" class="img-rounded" style="width:150px; height:150px">\
									<p style="padding-top: 10px">' + user.firstName + ' ' + user.secondName +'</p>\
									<h6>' + user.email + '</h6>\
									<p>\
										<button id="but' + user.id + '" type="button" class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button>\
		  							</p>\
								</div>');
		}
		var element = document.getElementById('but' + user.id);
		element.onclick = function () {
			entityToDelete = 'user';
			idToDelete = user_id;
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

function addStudent(user_id) {
	
	$('#addStudentModal').modal('hide');
	$('#loadingModal').modal('show');

	var team_id = "${team.id}";
	
	$.ajax({
		url: "/spms/team/addUser/"	,
	    data: {
	    	user_id: user_id,
	    	team_id: team_id
	    },
	    type: "POST",
	    dataType : "json",
		timeout: 15000
	})
	.done(function( user ) {
		var isPhoto = (user.linkToPhoto != null);
		if (isPhoto) {
			$('#block-students').append('<div class="col-lg-3 padtop" id="user-' + user.id + '">\
									<img src="' + user.linkToPhoto + '" class="img-rounded" style="width:150px; height:150px">\
									<p style="padding-top: 10px">' + user.firstName + ' ' + user.secondName +'</p>\
									<h6>' + user.email + '</h6>\
									<p>\
										<button id="but' + user.id + '" type="button" class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button>\
	  								</p>\
								  </div>');
		}
		else {
			$('#block-students').append('<div class="col-lg-3 padtop" id="user-' + user.id + '">\
									<img src="/spms/resources/img/anonymous.jpg" class="img-rounded" style="width:150px; height:150px">\
									<p style="padding-top: 10px">' + user.firstName + ' ' + user.secondName +'</p>\
									<h6>' + user.email + '</h6>\
									<p>\
										<button id="but' + user.id + '" type="button" class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button>\
	  								</p>\
								</div>');
		}
		var element = document.getElementById('but' + user.id);
		element.onclick = function () {
			entityToDelete = 'user';
			idToDelete = user_id;
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
	
	var user_id = idToDelete;
	var team_id = "${team.id}";
	
	$.ajax({
	    url: "/spms/team/deleteUser/",
	    data: {
	    	user_id: user_id,
	    	team_id: team_id
	    },
	    type: "POST",
	    dataType : "text",
		timeout: 15000
	})
	.done(function(message) {
		$('#user-'+ user_id).remove();
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