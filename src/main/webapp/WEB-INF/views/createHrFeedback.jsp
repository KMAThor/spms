<%@include file="header.jsp"%>
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">
		<h1>Leave Feedback for ${student.firstName} ${student.lastName} </h1> 
	</div>
</div>
<form name="createHrFeedback" 
	  action="<%=request.getContextPath()%>/hrFeedback/create/${student.id}/" method="post">
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">

		
			<div class="row">
					<hr/>
				<div class="col-sm-offset-1 col-sm-11">	
				</div>
			</div>
			
		
	</div>
	<hr/>
	
	<div class="col-sm-offset-9 col-sm-2">
	<h4><strong>If author is not you<strong/></h4>



<button type="button" class="btn btn-warning"  data-toggle="modal" data-target="#editProjectModal" >
			  		<i class="fa fa-pencil" aria-hidden="true"></i>
			  		Choose author
			  	</button></div>

<div class=" col-sm-9">
		<h4 ><strong> Description</strong></h4>
		<textarea name="description" class="form-control" placeholder="This field is required"></textarea> 
	</div>

<div class="col-sm-9">
								<h4 ><strong> Comment</strong></h4>
								<textarea name="comments" class="gradeComment form-control" placeholder="This field is required"></textarea>
							</div>
							<div class="col-sm-9">
												
							</div>
						
							
<div class="row">

<div class="col-sm-offset-9 col-sm-2">

		<br>
		
		<button type="submit" value="Submit" class="form-control btn btn-success" >Submit Feedback</button>
	</div>
	
</div>
</form>
<%@include file="footer.jsp"%>