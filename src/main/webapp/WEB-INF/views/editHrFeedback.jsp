<%@include file="header.jsp"%>
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">
		<h1>Leave Feedback for ${student.firstName} ${student.lastName}</h1>

	</div>
</div>
<form name="createHrFeedback"
	action="<%=request.getContextPath()%>/hrFeedback/edit/${hrFeedback.id}/"
	method="post">
	<input type="hidden" name="studentId" value="${student.id}" />
	<input type="hidden" id="hrFeedbackAuthorId" name="authorId"/>
<hr/>
	<div class="row">
		<div class="col-sm-offset-1 col-sm-10">
			<div class="row">
				<div class="col-sm-offset-1 col-sm-12"></div>
			</div>
		</div>
		<div class="col-sm-offset-9 col-sm-2">
			<h4 id="hrFeedbackAuthorName">
				${author.firstName} ${author.lastName}
			</h4>
			<button type="button" class="btn btn-warning" data-toggle="modal"
				data-target="#chooseAuthorModal">
				<i class="fa fa-pencil" aria-hidden="true"></i> Change author
			</button>
		</div>

		<div class=" col-sm-9">
			<h4>
				<strong>Topic</strong>
			</h4>
			<input name="topic" class="form-control"
				placeholder="This field is required" value="${hrFeedback.topic }">
		</div>

		<div class="col-sm-9">
			<h4>
				<strong>Summary</strong>
			</h4>
			<textarea name="summary" class="gradeComment form-control"
				placeholder="This field is required">${hrFeedback.summary }</textarea>
		</div>
		<div class="col-sm-9"></div>

		<div class="row">
			<div class="col-sm-offset-9 col-sm-2">
				<br>
				<button type="submit" value="Submit"
					class="form-control btn btn-success">Submit Feedback</button>
			</div>
			<input type="hidden" name="id" value="${hrFeedback.id}"> 
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</div>
</form>
<!-- chooseAuthorModal -->
<div class="modal fade" id="chooseAuthorModal" tabindex="-1" role="dialog">
  	<div class="modal-dialog" role="document">
    	<div class="modal-content">
     		<div class="modal-header">
     			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
        		<h4 class="modal-title" id="myModalLabel">Choose Author</h4>
      		</div>
      		<div class="modal-body row">
      			<div class="div-table">
					<table id="usersTable" class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
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
						        url: getContextPath()+'/user/all/view/',
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
							        	json.data[i]["role"] = '<a class="btn btn-sm  btn-success" onclick="chooseAuthor('+json.data[i]["id"]+',&quot;'+json.data[i]["firstName"]+" "+json.data[i]["lastName"]+'&quot;'+');">Choose</a>';
							      	}
							      	return json.data;
								}
						    },
						    columns: [
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

<%@include file="footer.jsp"%>