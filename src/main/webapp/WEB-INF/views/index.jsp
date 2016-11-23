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
					$('#projectsTable').DataTable( {

					    serverSide: true,
				        ajax: {
							url: '<c:url value="/project/view/"/>',
					        type: 'POST',
					        data: function ( d ) {
							      return JSON.stringify( d );
							    },	        
						    contentType: "application/json; charset=utf-8",
						    dataType: "json",
						    dataSrc: function ( json ) {
						    	for(var i=0, ien=json.data.length; i<ien ; i++ ) {
						        	json.data[i]["name"] = '<a href="/spms/project/view/'+json.data[i]["id"]+'/">'+json.data[i]["name"]+'</a>';
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
	<!-- createProjectModal -->
	<div class="modal fade" id="createProjectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Create Project</h4>
	      </div>
	      <form name="createProjectForm" id="createProjectForm" onsubmit="onSubmitCreateProjectForm();"
	        	action="/spms/project/create/" method="post">
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
	
	
    <!-- <img src="resources/thor.png">${greeting} . Our system has ${numberOfUsers} users. -->
    <script>
    $('#projectsTable').DataTable();
    </script>
<%@include file="footer.jsp"%>
