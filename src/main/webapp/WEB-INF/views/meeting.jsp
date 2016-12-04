<%@include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<h1>
				<p id="meetingTopic">${meeting.topic}</p>
				<p id="meetingStartDate">${meeting.startDate}</p>
				<security:authorize access="hasAnyAuthority('admin','mentor')">
		    	<div class="btn-group btn-group-sm" role="group" aria-label="..."  >
				  	<button type="button" class="btn btn-warning"
				  		  data-toggle="modal" data-target="#editMeetingModal"
				  		 >
				  		<i class="fa fa-pencil" aria-hidden="true"></i>
				  		Edit Meeting
				  	</button>
				  	<button type="button" class="btn btn-danger"
				  		  data-toggle="modal" data-target="#deleteMeetingModal"
				  		  >
				  		<i class="fa fa-trash" aria-hidden="true"></i>
				  		Delete Meeting
				  	</button>
				</div>
				</security:authorize>
			</h1>
			<h6>
				<a href="<%=request.getContextPath()%>/team/view/${meeting.team.id}/"><--- Back to Team</a>
			</h6>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
	
			<div class="div-table">
				<table id="membersTable" class="table table-striped table-hover table-bordered" style="min-width: 100%;">
					<thead>
						<tr>
							<td>Name</td>
							<td data-orderable="false">Precence</td>
							<td data-orderable="false"></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${members}" var="member" varStatus="loop">
							<c:choose>
								<c:when test="${member.value.status.name == 'left_project'}">
									<tr style="background-color: #DCDCDC;">
								</c:when>
								<c:otherwise>
									<tr>
								</c:otherwise>
							</c:choose>
						
   								<td>
   									${member.key.firstName} ${member.key.secondName} ${member.key.lastName}
   								</td>
   								<td>
   									<c:set var="participates" value="false" />
   									<c:forEach var="participant" items="${meeting.participants}">
  										<c:if test="${participant.id eq member.key.id}">
    										<c:set var="participates" value="true" />
 										</c:if>
									</c:forEach>
									
									<c:choose>
										<c:when test="${member.value.status.name == 'left_project'}">
											<c:choose>
												<c:when test="${participates}">
										    		<div>
  														<input type="checkbox" checked disabled>
													</div>
												</c:when>
												<c:otherwise>
													<div>
  														<input type="checkbox" disabled>
													</div>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
   											<c:choose>
												<c:when test="${participates}">
										    		<div>
  														<input id="input-true" type="checkbox" onclick="deleteParticipant(${member.key.id});" checked>
													</div>
												</c:when>
												<c:otherwise>
													<div>
  														<input id="input-false" type="checkbox" onclick="addParticipant(${member.key.id});">
													</div>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
   								</td>
   								<td>
   								<security:authorize access="hasAnyAuthority('admin','mentor')">	
   									<c:choose>
										<c:when test="${empty feedbacks[loop.index]}">
										    <a href="<c:url value="/meetingFeedback/create/${member.key.id}/${meeting.id}/" />" class="btn btn-success">
										    	<i class="fa fa-plus-circle" aria-hidden="true"></i>
										    	Leave feedback
										    </a>
										</c:when>
										<c:otherwise>
										    <a href="<c:url value="/meetingFeedback/edit/${feedbacks[loop.index].id}/" />" class="btn btn-warning">
										    	<i class="fa fa-pencil" aria-hidden="true"></i>
										   		Edit my feedback
										   	</a>
										</c:otherwise>
									</c:choose>
								</security:authorize>
								<security:authorize access="hasAuthority('hr')">
									<div class="btn-group">
									  <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									    View Feedback Of<span class="caret"></span>
									  </button>
									  <ul class="dropdown-menu">
									  	<c:forEach items="${mentorFeedbacksOnStudents[loop.index]}"
									  		var="mentorFeedback">
									    	<li>
									    		<a href="<c:url value="/meetingFeedback/view/${mentorFeedback.id}/" />">
											    	${mentorFeedback.author.firstName}
											    	${mentorFeedback.author.lastName}
											    </a>
									    	</li>
									    </c:forEach>
									  </ul>
									</div>
								</security:authorize>
								<security:authorize access="hasAuthority('admin') || @spmsWebSecurityService.isUserChiefMentorOfProjectWithMeeting(principal, #meeting.id)">
									<c:choose>
									<c:when test="${empty mentorFeedbacksOnStudents[loop.index]}">
									</c:when>
									<c:otherwise>
									<div class="btn-group">
									  <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									    Edit Feedback Of<span class="caret"></span>
									  </button>
									  <ul class="dropdown-menu">
									  	<c:forEach items="${mentorFeedbacksOnStudents[loop.index]}"
									  		var="mentorFeedback">
									    	<li>
									    		<a href="<c:url value="/meetingFeedback/edit/${mentorFeedback.id}/" />">
											    	${mentorFeedback.author.firstName}
											    	${mentorFeedback.author.lastName}
											    </a>
									    	</li>
									    </c:forEach>
									  </ul>
									</div>
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
	</div>
<security:authorize access="hasAnyAuthority('admin','mentor')">
	<!-- editMeetingModal -->
	<div class="modal fade" id="editMeetingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Edit meeting</h4>
	      </div>
	      <form>
	      	<div class="modal-body">
	        
				<div class="form-group">
					<label for="name">Topic:</label>
				    <input type="text" class="form-control" maxlength="255" name="topic" id="topic" value="${meeting.topic}" placeholder="Enter topic" required>
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
				        $('#datetimepicker').data("DateTimePicker").date(moment('${meeting.startDate}'));
				    });
				</script>
			
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        <button type="button" class="btn btn-warning" onclick="editMeeting(${meeting.id});">Edit</button>
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
	        			<button type="button" class="btn btn-danger" onclick="deleteMeeting(${meeting.id});">Delete</button>
	     			</div>
	      		</form>
	    	</div>
	  	</div>
	</div>
</security:authorize>	
<script>

	$('#membersTable').DataTable();
	
	function addParticipant(userId){
		
		$('#loadingModal').modal('show');
		var meetingId = "${meeting.id}";
		alert("try to add " + userId);
		
		$.ajax({
		    url: getContextPath()+'/meeting/addParticipant/',
		    data: {
		    	userId: userId,
		    	meetingId: meetingId
		    },
		    type: "POST",
		    dataType : "text",
			timeout: 15000
		})
		.done(function(message) {
			document.getElementById('input-false').onclick=function(){
				deleteParticipant(userId);
			}
		    alert("added " + userId);
		    $('#loadingModal').modal('hide');
		})
		.fail(function( xhr, status, errorThrown ) {
			$('#loadingModal').modal('hide');
			$('#networkErrorModal').modal('show');
		})
		.always(function( xhr, status ) {
		});;
		
	}
	
	function deleteParticipant(userId){
		
		$('#loadingModal').modal('show');
		var meetingId = "${meeting.id}";
		alert("try to delete " + userId);
		
		$.ajax({
		    url: getContextPath()+'/meeting/deleteParticipant/',
		    data: {
		    	userId: userId,
		    	meetingId: meetingId
		    },
		    type: "POST",
		    dataType : "text",
			timeout: 15000
		})
		.done(function(message) {
			document.getElementById('input-true').onclick=function(){
				addParticipant(userId);
			}
		    alert("deleted " + userId);
		    $('#loadingModal').modal('hide');
		})
		.fail(function( xhr, status, errorThrown ) {
			$('#loadingModal').modal('hide');
			$('#networkErrorModal').modal('show');
		})
		.always(function( xhr, status ) {
		});;
		
	}

	function editMeeting(id) {
		
		$('#editMeetingModal').modal('hide');
		$('#loadingModal').modal('show');
		
		var topic = $('#topic').val();
		alert(topic);
		var startDate = $("#datetimepicker").find("input").val();
		alert(startDate);
		
		$.ajax({
		    url: getContextPath()+"/update/meeting/",
		    data: {
		    	id: id,
		    	topic: topic,
		        startDate: startDate
		    },
		    type: "POST",
		    dataType : "text",
			timeout: 15000
		})
		.done(function(message) {
			$('#meetingTopic').text(topic);
			$('#meetingStartDate').text(startDate);
		    $('#loadingModal').modal('hide');
		})
		.fail(function( xhr, status, errorThrown ) {
			$('#loadingModal').modal('hide');
			$('#networkErrorModal').modal('show');
		})
		.always(function( xhr, status ) {
		});;
	}

	function deleteMeeting(id) {
		
		$('#deleteMeetingModal').modal('hide');
		$('#loadingModal').modal('show');

		var teamId = "${meeting.team.id}";
		
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
		    $('#loadingModal').modal('hide');
		    window.location = '<%=request.getContextPath()%>/team/view/' + teamId + '/';
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