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
				  	</button>
				</div>
			</h1>
		</div>
	</div>
	<hr>
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<h2>Users
				<button type="button" class="btn btn-success"
					data-toggle="modal" data-target="#addUserToTeamModal">
					<i class="fa fa-plus-circle" aria-hidden="true"></i>
						Add user
				</button>
			</h2>
			<c:forEach items="${users}" var="user">
				<h3>
					${user.email}
				</h3>
			</c:forEach>
		</div>
	</div>
	<hr>
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<h2>Meetings
				<button type="button" class="btn btn-success"
					data-toggle="modal" data-target="#addMeetingModal">
					<i class="fa fa-plus-circle" aria-hidden="true"></i>
						Add Meeting
				</button>
			</h2>
			<c:forEach items="${meetings}" var="meeting">
				<h3>
					${meeting.topic}
				</h3>
			</c:forEach>
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
<%@include file="footer.jsp"%>