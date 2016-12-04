<%@include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<div  class="panel-center">
				<h1>Welcome to Student Practice Management System!</h1>
			</div>
			<hr>
			<div  class="panel-center">
				<h3>
					Projects
					<security:authorize access="hasAuthority('admin')">
					<button type="button" class="btn btn-success"
							data-toggle="modal" data-target="#createProjectModal">
						<i class="fa fa-plus-circle" aria-hidden="true"></i>
						Create project
					</button>
					</security:authorize>
				</h3>
			</div>
			
			<div class="div-table">
				<table id="projectsTable" class="table table-striped table-hover table-bordered">
					<thead>
						<tr>
							<td>Id</td>
							<td>Name</td>
							<td>Start Date</td>
							<td>End Date</td>
							<td>Status</td>
						</tr>
					</thead>
				</table>
				
				<script type="text/javascript">
				
				var token = $("meta[name='_csrf']").attr("content");
		        var header = $("meta[name='_csrf_header']").attr("content");
		        
					$('#projectsTable').DataTable( {

					    serverSide: true,
				        ajax: {
							url: getContextPath()+'/project/view/',
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
						        	json.data[i]["name"] = '<a href="<%=request.getContextPath()%>/project/view/'+json.data[i]["id"]+'/">'+json.data[i]["name"]+'</a>';
						        	if(json.data[i]["completed"] === true) json.data[i]["completed"] = "completed";
						        	if(json.data[i]["completed"] === false) json.data[i]["completed"] = "active";

						      	}
						      	return json.data;
							}
					    },
					    columns: [
						    { data: "id" },
						    { data: "name" },
						    { data: "startDate" },
						    { data: "endDate" },
						    { data: "completed" }
						  ]
					} );
				</script>
			</div>
		</div>
	</div>
<security:authorize access="hasAuthority('admin')">
	<!-- createProjectModal -->
	<div class="modal fade" id="createProjectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Create Project</h4>
	      </div>
	      <form name="createProjectForm" id="createProjectForm" onsubmit="onSubmitCreateProjectForm();"
	        	action="<%=request.getContextPath()%>/project/create/" method="post">
	        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	      	<div class="modal-body">
	        
				<div class="form-group">
					<label for="name">Project name:</label>
				    <input type="text" class="form-control" name="name" id="newProjectName" placeholder="Enter new project name" required>
				</div>
				<div class="form-group">
					<label for="description">Project description:</label>
				    <textarea class="form-control" rows="3" name="description" id="newProjectDescription" placeholder="Enter project description"></textarea>
				</div>

		        <div class="form-group">
		        	<label for="startDate">Start date:</label>
		            <div class='input-group date' id='datetimepicker6'>
		                <input type='text' class="form-control" name="startDate" id="endProjectStartDate" placeholder="Enter start date" required/>
		                <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
		        </div>

		        <div class="form-group">
		        	<label for="endDate">End Date:</label>
		            <div class='input-group date' id='datetimepicker7'>
		                <input type='text' class="form-control" name="endDate" id="endProjectStartDate" placeholder="Enter end date" required/>
		                <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
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
							Not assigned yet
						</p>
						<input id="cheifMentorId" name="cheifMentorId" type="hidden" value="${project.chiefMentor.id}"/>
					</div>
				</div>
				<script type="text/javascript">
				    $(function () {
				        $('#datetimepicker6').datetimepicker({ format: 'DD/MM/YYYY hh:mm a' });
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
				    });
				</script>
	      	</div>
	      	<div class="modal-footer">
	       		<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        	<button type="submit" value="Submit" class="btn btn-primary" >Create</button>
	      	</div>
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
	      		<div class="modal-body">
	      			<div class="div-table">
						<table id="usersTable" class="table table-striped table-hover table-bordered">
							<thead>
								<tr>
									<td>Id</td>
									<td>Email</td>
									<td>First Name</td>
									<td>Second Name</td>
									<td>Last Name</td>
									<td>Role</td>
								</tr>
							</thead>
						</table>
						<script type="text/javascript">
						
						var token = $("meta[name='_csrf']").attr("content");
				        var header = $("meta[name='_csrf_header']").attr("content");
				        
							$('#usersTable').DataTable( {

							    serverSide: true,
						        ajax: {
							        url: getContextPath()+'/user/view/mentor/',
							        type: 'POST',
							        data: function ( d ) {
									      return JSON.stringify( d );
									    },
									    beforeSend: function(xhr) {
								            xhr.setRequestHeader(header, token);
								        },
								    contentType: "application/json; charset=utf-8",
								    dataType: "json",
								    dataSrc: function ( json ) {
								    	for(var i=0, ien=json.data.length; i<ien ; i++ ) {
								        	json.data[i]["email"] = '<a class="clickable" onclick="chooseChiefMentor('+json.data[i]["id"]+',&quot;'+json.data[i]["firstName"]+" "+json.data[i]["lastName"]+'&quot;'+');">'+json.data[i]["email"]+'</a>';
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
								    { data: "role" }
								  ]
							    
							} );
						</script>
					</div>
	      		</div>
	      	</div>
	    </div>
	</div>
</security:authorize>	
    <!-- <img src="resources/thor.png">${greeting} . Our system has ${numberOfUsers} users. -->
    <script>
    $('#projectsTable').DataTable();
    </script>
<%@include file="footer.jsp"%>
