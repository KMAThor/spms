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
				  		<a class="btn btn-danger" href="<c:url value="/delete/team/${team.id}/" />">
				  		<i class="fa fa-trash" aria-hidden="true"></i>
				  		Delete Team 	</a>
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
						data-toggle="modal" data-target="#addMentorToTeamModal">
							<i class="fa fa-plus-circle" aria-hidden="true"></i>
							Add mentor
						</button>
					</div>
  				</div>
  				<div class="tab-pane fade" id="students">
  					<div  class="panel-center">
  						<button type="button" class="btn btn-success"
						data-toggle="modal" data-target="#addStudentToTeamModal">
							<i class="fa fa-plus-circle" aria-hidden="true"></i>
							Add student
						</button>
					</div>
  				</div>
  				<div class="tab-pane fade" id="meetings">
  					<div  class="panel-center">
  						<button type="button" class="btn btn-success"
						data-toggle="modal" data-target="#addMeetingToTeamModal">
							<i class="fa fa-plus-circle" aria-hidden="true"></i>
							Add meeting
						</button>
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
	      		<form name="createProjectForm" id="createProjectForm" onsubmit="onSubmitEditTeamNameForm();"
	        		  action="/spms/update/team/${team.id}/" method="post">
	      			<div class="modal-body">
	        
						<div class="form-group">
							<label for="name">New Team Name</label>
				    		<input type="text" class="form-control" name="name" id="newTeamName" placeholder="Enter new team name" value="${team.name}" required>
						</div>

	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        			<button type="submit" value="Submit" class="btn btn-primary" >Update</button>
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
	        <h4 class="modal-title" id="myModalLabel">Create Team</h4>
	      </div>
	      <form name="addMentorToTeamForm" id="addMentorToTeamForm" onsubmit="onSubmitAddMentorToTeamForm();"
	        	action="/spms/${team.id}/add/mentor/" method="post">
	      	<div class="modal-body">
	        
				<div class="form-group">
					<label for="name">Add mentor: </label>
				    
				</div>

	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        <button type="submit" value="Submit" class="btn btn-primary" >Add</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
<%@include file="footer.jsp"%>