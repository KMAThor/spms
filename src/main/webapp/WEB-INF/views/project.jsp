<%@include file="header.jsp"%>

<c:set var="isUserChiefMentorOfThisProject" value="${false}"/>
<security:authorize access="@spmsWebSecurityService.isUserChiefMentorOfProject(principal, #project.id)">
	<c:set var="isUserChiefMentorOfThisProject" value="${true}"/>
</security:authorize>
<div class="row">
	<div class="col-sm-10 col-sm-offset-1">
		<h1>${project.name}
	    	<div class="btn-group btn-group-sm" role="group" aria-label="..."  >
	    		<security:authorize access="hasAnyAuthority('admin','hr') || ${isUserChiefMentorOfThisProject}">
	    		<a type="button" class="btn btn-success"
			  		  href="<c:url value="/project/report/${project.id}/" />" >
			  		<i class="fa fa-bars" aria-hidden="true"></i>
			  		Generate Report
			  	</a>
			  	</security:authorize>
	    		<security:authorize access="hasAuthority('admin') || ${isUserChiefMentorOfThisProject}">
	    		<button type="button" class="btn btn-primary"
			  		  data-toggle="modal" data-target="#projectTraitsManagerModal"
			  		  >
			  		<i class="fa fa-bars" aria-hidden="true"></i>
			  		Project Traits Manager
			  	</button>
			  	<button type="button" class="btn btn-warning"
			  		  data-toggle="modal" data-target="#editProjectModal"
			  		  >
			  		<i class="fa fa-pencil" aria-hidden="true"></i>
			  		Edit Project
			  	</button>
			  	</security:authorize>
			  	<security:authorize access="hasAuthority('admin')">
			  	<button type="button" class="btn btn-danger"
			  		data-toggle="modal" data-target="#deleteProjectModal"
			  		>
			  		<i class="fa fa-trash" aria-hidden="true"></i>
			  		Delete Project
			  	</button>
			  	</security:authorize>
			</div>
		  
		</h1>
		<h3>Description</h3>
		<p> <c:choose>
				<c:when test="${empty project.description}">
					Empty
				</c:when>
				<c:otherwise>
					${project.description}
				</c:otherwise>
			</c:choose>
		</p>
	</div>
</div>
<div class="row">
	<div class="col-sm-5 col-sm-offset-1">
		<h3>Start Date</h3>
		<fmt:parseDate value="${project.startDate}" var="dateObject"
            pattern="yyyy-MM-dd HH:mm:ss"></fmt:parseDate>
		<p><fmt:formatDate value="${dateObject }" pattern="dd/MM/yyyy hh:mm a"></fmt:formatDate></p>
	</div>
	<div class="col-sm-5">
		<h3>End Date</h3>
		<fmt:parseDate value="${project.endDate}" var="dateObject"
            pattern="yyyy-MM-dd HH:mm:ss" ></fmt:parseDate>
		<p><fmt:formatDate value="${dateObject }" pattern="dd/MM/yyyy hh:mm a"></fmt:formatDate></p>
	</div>
</div>
<div class="row">
	<div class="col-sm-5 col-sm-offset-1">
		<h3>Status</h3>
		<p>
			<c:choose>
				<c:when test="${project.isCompleted}">
					Completed
				</c:when>
				<c:otherwise>
					Active
				</c:otherwise>
			</c:choose>
		</p>
	</div>
	<div class="col-sm-5">
			<h3>Chief Mentor</h3>
			<p>
				<c:choose>
					<c:when test="${project.chiefMentor ne null}">
						${project.chiefMentor.firstName} ${project.chiefMentor.lastName}
					</c:when>
					<c:otherwise>
						Not assigned yet
					</c:otherwise>
				</c:choose>
		</p>
	</div>
</div>
<hr>
<div class="row">
	<div class="col-sm-10 col-sm-offset-1">
		<h2>Files
		<security:authorize access="hasAuthority('admin') || ${isUserChiefMentorOfThisProject}">
			<button type="button" class="btn btn-success"
				data-toggle="modal" data-target="#addFileToProjectModal" onclick="toggleFileDialog();">
				<i class="fa fa-plus-circle" aria-hidden="true"></i>
					Add file
			</button>
		</security:authorize>
		
	</div>	
</div>
<hr>
<div class="row">
	<div class="col-sm-10 col-sm-offset-1">
		<h2>Teams
			<security:authorize access="hasAuthority('admin') || ${isUserChiefMentorOfThisProject}">
			<button type="button" class="btn btn-success"
				data-toggle="modal" data-target="#createTeamModal"
				id="createTeamButton">
				<i class="fa fa-plus-circle" aria-hidden="true"></i>
					Create team
				</button>
			</security:authorize>
		</h2>
		<security:authorize access="hasAuthority('admin') || hasAuthority('hr') || ${isUserChiefMentorOfThisProject}">
		<c:forEach items="${teams}" var="team">
				<h3>
					<a href="<c:url value="/team/view/${team.id}/" />" class="btn btn-primary">${team.name}</a>
				</h3>
		</c:forEach>
		</security:authorize>
		<security:authorize access="!(hasAuthority('admin') || hasAuthority('hr') || ${isUserChiefMentorOfThisProject})">
		<c:forEach items="${teams}" var="team">
				<h3>
					
						<button class="btn btn-primary" onclick="window.location.href='<c:url value="/team/view/${team.id}/" />' "
						<security:authorize access="!@spmsWebSecurityService.isUserMemberOfTeam(principal, #team.id)">
							disabled
						</security:authorize>

						>
							${team.name}
						</button>
					
				</h3>
		</c:forEach>
		</security:authorize>
	</div>
</div>

<security:authorize access="hasAuthority('admin') || @spmsWebSecurityService.isUserChiefMentorOfProject(principal, #project.id)">
<!-- editProjectModal -->
<div class="modal fade" id="editProjectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Edit Project</h4>
      </div>
      	<form name="createProjectForm" id="createProjectForm" onsubmit="onSubmitEditProjectForm();"
        	action="<%=request.getContextPath()%>/project/update/" method="post">
        <input type="hidden" name="id" value="${project.id}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      	<div class="modal-body">
			<div class="form-group">
				<label for="name">Project name:</label>
			    <input type="text" class="form-control" name="name" id="newProjectName" placeholder="Enter new project name" value="${project.name}" required>
			</div>
			<div class="form-group">
				<label for="description">Project description:</label>
			    <textarea class="form-control" rows="3" name="description" id="newProjectDescription" placeholder="Enter project description" ></textarea>
			</div>
			<div class="form-group">
	        	<label for="startDate">Start date:</label>
	            <div class='input-group date' id='datetimepicker6'>
	                <input type='text' class="form-control" name="startDate" id="newProjectStartDate" placeholder="Enter start date"  required/>
	                <span class="input-group-addon">
	                    <span class="glyphicon glyphicon-calendar"></span>
	                </span>
	            </div>
	        </div>

	        <div class="form-group">
	        	<label for="endDate">End Date:</label>
	            <div class='input-group date' id='datetimepicker7'>
	                <input type='text' class="form-control" name="endDate" id="newProjectEndDate" placeholder="Enter end date" required/>
	                <span class="input-group-addon">
	                    <span class="glyphicon glyphicon-calendar"></span>
	                </span>
				</div>
			</div>
				<script type="text/javascript">
					$(function () {
						console.log(new Date('${project.startDate}'));
						$('#datetimepicker6').datetimepicker({format: 'DD/MM/YYYY hh:mm a'});

						$('#datetimepicker7').datetimepicker({
							format: 'DD/MM/YYYY hh:mm a',
							useCurrent: false //Important! See issue #1075
						});

						$("#datetimepicker6").on("dp.change", function (e) {
							$('#datetimepicker7').data("DateTimePicker").minDate(e.date);
						});
						$("#datetimepicker7").on("dp.change", function (e) {
							$('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
						});
						$('#datetimepicker6').data("DateTimePicker").date(moment('${project.startDate}'));
						$('#datetimepicker7').data("DateTimePicker").date(moment('${project.endDate}'));
					});
				</script>
			<div class="form-group">
				<label for="isCompleted">Status:</label><br>
			  	<p> <input type="checkbox" name="isCompleted" 
						<c:if test="${project.isCompleted}">
							<c:out value="checked"/>
						</c:if>
					/>mark project as completed
				 </p>
			</div>
			<div class="form-group">
				<label for="chief">Chief mentor:</label>
				<button type="button" class="btn btn-xs btn-primary" data-toggle="modal"
						data-target="#chooseChiefMentorModal">
		  			<i class="fa fa-thumb-tack" aria-hidden="true"></i>
		  			Choose chief mentor
		  		</button>
			  	<br>
				<div class="btn-group">
					<p id="cheifMentorName">
						<c:choose>
							<c:when test="${project.chiefMentor ne null}">
								${project.chiefMentor.firstName} ${project.chiefMentor.lastName}
							</c:when>
							<c:otherwise>
								Not assigned yet
							</c:otherwise>
						</c:choose>
					</p>
					<input id="cheifMentorId" name="cheifMentorId" type="hidden" value="${project.chiefMentor.id}"/>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<button type="submit" value="Submit" class="btn btn-primary" >Update</button>
			</div>
		</div>
		</form>
		</div>
	</div>
</div>

<!-- deleteProjectModal -->
<div class="modal fade" id="deleteProjectModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Delete Project</h4>
			</div>
			<form action="<c:url value="/project/delete/" />" method="POST">
				<div class="modal-body">
					<div class="form-group">
						<input type="hidden" name="id" value="${project.id}"/>
						<p>Are you sure?</p>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="submit" value="Submit" class="btn btn-danger">Delete Project</button>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</div>
</div>
<!-- chooseChiefMentorModal -->
<div class="modal fade" id="chooseChiefMentorModal" tabindex="-1" role="dialog">
  	<div class="modal-dialog" role="document">
    	<div class="modal-content">
     		<div class="modal-header">
     			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
        		<h4 class="modal-title" id="myModalLabel">Choose chief mentor</h4>
      		</div>
      		<div class="modal-body row">
      			<div class="div-table">
					<table id="usersTable" class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<td>Id</td>
								<td>Email</td>
								<td>First Name</td>
								<td>Second Name</td>
								<td>Last Name</td>
								<td></td>
							</tr>
						</thead>
					</table>
					<script type="text/javascript">
					
					var token = $("meta[name='_csrf']").attr("content");
			        var header = $("meta[name='_csrf_header']").attr("content");
			        
						$('#usersTable').DataTable( {
						    serverSide: true,
					        ajax: {
						        url: getContextPath()+'/user/allWithRole/mentor/view/',
						        type: 'POST',
						        data: function ( d ) {
								      return JSON.stringify( d );
								    },	        
							    contentType: "application/json; charset=utf-8",
							    dataType: "json",
							    beforeSend: function(xhr) {
						            xhr.setRequestHeader(header, token);
						        },
							    dataSrc: function ( json ) {
							    	for(var i=0, ien=json.data.length; i<ien ; i++ ) {
							        	json.data[i]["role"] = '<a class="btn btn-sm  btn-success" onclick="chooseChiefMentor('+json.data[i]["id"]+',&quot;'+json.data[i]["firstName"]+" "+json.data[i]["lastName"]+'&quot;'+');">Choose</a>';
							      	}
							      	return json.data;
								}
						    },
						    columns: [
							    { data: "id" },
							    { data: "email" },
							    { data: "firstName" },
							    { data: "secondName" },
							    { data: "lastName" },
							    { data: "role", orderable: false }
							  ]
						    
						} );
					</script>
				</div>
      		</div>
      	</div>
    </div>
</div>
<!-- projectTraitsManagerModal -->
<div class="modal fade" id="projectTraitsManagerModal" tabindex="-1" role="dialog">
  	<div class="modal-dialog" role="document">
    	<div class="modal-content">
     		<div class="modal-header">
     			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
        		<h4 class="modal-title" id="myModalLabel">Project Traits Manager</h4>
      		</div>
      
       		<div class="modal-body">
        		<div id="categoriesPanelGroup" class="panel-group">
					<c:forEach var="traitCategory" items="${traitCategories}">
			  		<div id="category-${traitCategory.id}" class="panel panel-default" >
					    <div class="panel-heading clickable" data-toggle="collapse" href="#category-${traitCategory.id}-traits">
					    	<div class="row">
								<div class="col-sm-7">
							      	<h3 id="traitCategory-${traitCategory.id}-name" class="categoryTitle">
							      	${traitCategory.name}	
						      </h3>
							    </div>
							    <div class="col-sm-5 text-right" >
							    	<div class="btn-group btn-group-xs" role="group"  >
									  	<button type="button" class="btn btn-success"
									  		onclick="selectAllTraitAction(${traitCategory.id}, ${project.id})">
										  	<i class="fa fa-check-square-o " aria-hidden="true"></i>
									  		Select All
									  	</button>
									  	<button type="button" class="btn btn-warning"
									  	onclick="deselectAllTraitAction(${traitCategory.id}, ${project.id})">
									  		<i class="fa fa-square-o" aria-hidden="true"></i>
									  		Deselect All
									  	</button>
									</div>
							    </div>
							</div>  
				    	</div>

				    	<div id="category-${traitCategory.id}-traits" class="panel-collapse collapse in">
				    		<c:forEach var="trait" items="${traitCategory.traits}">
							<ul id="trait-${trait.id}" class="list-group traits-list">
								<li class="list-group-item">
									<div class="row">
										<div class="col-sm-9">
											<p id="trait-${trait.id}-name">
											<input type="checkbox"
												onchange="traitCheckboxAction(this,${trait.id},${project.id})" 
												<c:set var="contains" value="false" />
												<c:forEach var="item" items="${traitsAssociatedWithProject}">
												  <c:if test="${item.id eq trait.id}">
												    <c:set var="contains" value="true" />
												    <c:out value="checked"/>
												  </c:if>
												</c:forEach>
											/>
												${trait.name}
											</p>
										</div>
									</div>
								</li>
					      	</ul>
				   			</c:forEach>
				  		</div>
					</div>
					</c:forEach>
				</div>
      		</div>
    	</div>
 	</div>
</div>

<!-- createTeamModal -->
<div class="modal fade" id="createTeamModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">Create Team</h4>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			</div>
			<form>
				<div class="modal-body">
					<div class="form-group">
						<label for="name">Team name:</label>
						<input type="text" maxlength="255" class="form-control" id="newTeamName" placeholder="Enter new team name" required>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-success" onclick="createTeam();">Create</button>
				</div>
			</form>
		</div>
	</div>
</div>
</security:authorize>

<!-- uploadFileModal -->
<div class="modal fade" id="addFileToProjectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<!-- <form target="<c:url value="/project/${project.id}/upload?${_csrf.parameterName}=${_csrf.token}/" />" method="post" enctype="multipart/form-data"> -->
			<form action="<%=request.getContextPath()%>/project/uploadFile/" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${project.id}">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel2">Upload</h4>
				</div>
				<div class="modal-body">
					<input type="file" id="file" name="file" />
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="submit" class="btn btn-success">Upload</button>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</div>
</div>

<script>

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ready ( function(){
		if ("${project.isCompleted}" === "true"){
			document.getElementById('createTeamButton').disabled = true;
			$("#createTeamButton").attr("title", "Cannot create a team in completed project.");
		}
	});

	function createTeam() {
	
		$('#createTeamModal').modal('hide');
		$('#loadingModal').modal('show');
	
		var name = $('#newTeamName').val();
		var projectId = "${project.id}";

		$.ajax({
			url: getContextPath()+"/team/create/",
	    	data: {
	    		projectId: projectId,
	        	name: name
	    	},
	    	type: "POST",
	    	dataType : "text",
			timeout: 15000,
			beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        }
		})
		.done(function(teamId) {
	    	$('#loadingModal').modal('hide');
	    	window.location = "<%=request.getContextPath()%>/team/view/" + teamId + "/";
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