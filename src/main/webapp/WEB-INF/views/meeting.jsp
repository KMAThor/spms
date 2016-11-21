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
				<a href="/spms/team/view/${meeting.team.id}/"><--- Back to Team</a>
			</h6>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
	
			<div class="div-table">
				<table id="membersTable" class="table table-striped table-hover table-bordered" style="min-width: 100%;">
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
						<c:forEach items="${members}" var="member">
							<c:if test="${member.role.id == 3}">
								<tr>
   									<td>${member.id}</td>
   									<td>${member.email}</td>
   									<td>${member.firstName}</td>
   									<td>${member.secondName}</td>
   									<td>${member.lastName}</td>
   									<td>
   										<a href="<c:url value="/meetingFeedback/create/${member.id}/${meeting.id}" />" class="btn btn-warning">Evaluate</a>
									</td>
   								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<!-- createMeetingModal -->
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
				    <input type="text" class="form-control" name="topic" id="topic" value="${meeting.topic}" placeholder="Enter topic" required>
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
						<input type="hidden" name="team_id" id="team_id" value="${meeting.team.id}">
	      			</div>
	      			<div class="modal-footer">
	        			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        			<button type="button" class="btn btn-danger" onclick="deleteMeetingM(${meeting.id});">Delete</button>
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

	$('#membersTable').DataTable();

</script>

<%@include file="footer.jsp"%>