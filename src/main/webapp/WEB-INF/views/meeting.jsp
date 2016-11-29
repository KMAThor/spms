<%@include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<h1>
				<p id="meetingTopic">${meeting.topic}</p>
				<p id="meetingStartDate">${meeting.startDate}</p>
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
				  		<i class="fa fa-pencil" aria-hidden="true"></i>
				  		Delete Meeting
				  	</button>
				</div>
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
   									<c:forEach var="participant" items="${participants}">
  										<c:if test="${participant.id eq member.key.id}">
    										<c:set var="participates" value="true" />
 										</c:if>
									</c:forEach>
   									<c:choose>
										<c:when test="${participates}">
										    <div onclick="deleteParticipant(${member.key.id});" >
  												<input type="checkbox" checked>
											</div>
										</c:when>
										<c:otherwise>
											<div onclick="addParticipant(${member.key.id});">
  												<input type="checkbox">
											</div>
										</c:otherwise>
									</c:choose>
   								</td>
   								<td>
   									<c:choose>
										<c:when test="${empty feedbacks[loop.index]}">
										    <a href="<c:url value="/meetingFeedback/create/${member.key.id}/${meeting.id}/" />" class="btn btn-success">Leave feedback</a>
										</c:when>
										<c:otherwise>
										    <a href="<c:url value="/meetingFeedback/edit/${feedbacks[loop.index].id}/" />" class="btn btn-warning">Edit feedback</a>
										</c:otherwise>
									</c:choose>
								</td>
   							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
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
	
<script>

	$('#membersTable').DataTable();
	
	function addParticipant(user_id){
		
		$('#loadingModal').modal('show');
		var meeting_id = "${meeting.id}";
		
		$.ajax({
		    url: getContextPath()+'/meeting/addParticipant/',
		    data: {
		    	user_id: user_id,
		    	meeting_id: meeting_id
		    },
		    type: "POST",
		    dataType : "text",
			timeout: 15000
		})
		.done(function(message) {
		    $('#loadingModal').modal('hide');
		})
		.fail(function( xhr, status, errorThrown ) {
			$('#loadingModal').modal('hide');
			$('#networkErrorModal').modal('show');
		})
		.always(function( xhr, status ) {
		});;
		
	}
	
	function deleteParticipant(user_id){
		
		$('#loadingModal').modal('show');
		var meeting_id = "${meeting.id}";
		
		$.ajax({
		    url: getContextPath()+'/meeting/deleteParticipant/',
		    data: {
		    	user_id: user_id,
		    	meeting_id: meeting_id
		    },
		    type: "POST",
		    dataType : "text",
			timeout: 15000
		})
		.done(function(message) {
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
		var start_date = $("#datetimepicker").find("input").val();
		alert(start_date);
		
		$.ajax({
		    url: getContextPath()+"/update/meeting/",
		    data: {
		    	id: id,
		    	topic: topic,
		        start_date: start_date
		    },
		    type: "POST",
		    dataType : "text",
			timeout: 15000
		})
		.done(function(message) {
			$('#meetingTopic').text(topic);
			$('#meetingStartDate').text(start_date);
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

		var team_id = "${meeting.team.id}";
		
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
		    window.location = '<%=request.getContextPath()%>/team/view/' + team_id + '/';
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