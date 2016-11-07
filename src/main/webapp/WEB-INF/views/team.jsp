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
<%@include file="footer.jsp"%>