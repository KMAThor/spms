<%@include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<h1>${project.name}
		    	<div class="btn-group btn-group-sm" role="group" aria-label="..."  >
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
				  		<a class="btn btn-danger" href="<c:url value="/delete/project/${project.id}/" />">
				  		<i class="fa fa-trash" aria-hidden="true"></i>
				  		Delete Project 	</a>
				  	</button>
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
			<p>${project.startDate}</p>
		</div>
		<div class="col-sm-5">
			<h3>End Date</h3>
			<p>${project.endDate}</p>
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
				<h3>Cheid Mentor</h3>
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
				<button type="button" class="btn btn-success"
					data-toggle="modal" data-target="#addFileToProjectModal">
					<i class="fa fa-plus-circle" aria-hidden="true"></i>
						Add file
				</button>
			</h2>
			
		</div>
	</div>
	<hr>
	
	<div class="row">
		<div class="col-sm-10 col-sm-offset-1">
			<h2>Teams
				<button type="button" class="btn btn-success"
					data-toggle="modal" data-target="#createTeamModal">
					<i class="fa fa-plus-circle" aria-hidden="true"></i>
						Create team
				</button>
			</h2>
			<c:forEach items="${teams}" var="team">
					<h3>
						<a href="<c:url value="/view/team/${team.id}/" />" class="btn btn-warning">${team.name}</a>
					</h3>
			</c:forEach>
		</div>
	</div>

<!-- editProjectModal -->
	<div class="modal fade" id="editProjectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Edit Project</h4>
	      </div>
	      <form name="createProjectForm" id="createProjectForm" onsubmit="onSubmitEditProjectForm();"
	        	action="/spms/update/project/${project.id}/" method="post">
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
				        $('#datetimepicker6').datetimepicker();
						
				        $('#datetimepicker7').datetimepicker({
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
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        <button type="submit" value="Submit" class="btn btn-primary" >Update</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>


	<!-- projectTraitsManagerModal -->
	<div class="modal fade" id="projectTraitsManagerModal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Project Traits Manager
	       
		      
			</h4>
	      </div>
	      
	       <div class="modal-body">
	        <div id="categoriesPanelGroup" class="panel-group">
				<c:forEach var="traitCategory" items="${traitCategories}">
				  	<div id="category-${traitCategory.id}" class="panel panel-default" >
					    <div class="panel-heading clickable" data-toggle="collapse" href="#category-${traitCategory.id}-traits">
					    	<div class="row">
								<div class="col-sm-7">
								  	
							      	<h3 id="traitCategory-${traitCategory.id}-name" class="categoryTitle">${traitCategory.name}
							      		

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
											<!--<c:if test="${contains}">
												    		<c:out value="checked"/>
												  		</c:if>


											<div class="col-sm-3 text-right" >
										      	<div class="btn-group btn-group-xs" role="group" aria-label="..."  >
												  <button type="button" class="btn btn-warning"
												  		  data-toggle="modal" data-target="#editTraitModal"
												  		  onclick="editTrait(${trait.id}, '${trait.name}', ${traitCategory.id});">
												  	<i class="fa fa-pencil" aria-hidden="true"></i>
												  	Edit
												  </button>
												  <button type="button" class="btn btn-danger"
												  		  onclick="deleteTrait(${trait.id});">
												  	<i class="fa fa-trash" aria-hidden="true"></i>
												  	Delete
												  	</button>
												</div>
											</div>-->
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
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Create Team</h4>
	      </div>
	      <form name="createTeamForm" id="createTeamForm" onsubmit="onSubmitCreateTeamForm();"
	        	action="/spms/${project.id}/create/team/" method="post">
	      	<div class="modal-body">
	        
				<div class="form-group">
					<label for="name">Team name:</label>
				    <input type="text" class="form-control" name="name" id="newTeamName" placeholder="Enter new team name" required>
				</div>

	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        <button type="submit" value="Submit" class="btn btn-primary" >Create</button>
	      </div>
	      </form>
	    </div>
	  </div>
	</div>
<%@include file="traitManagerModals.jsp"%>
<%@include file="footer.jsp"%>