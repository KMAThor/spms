<%@include file="header.jsp"%>
<div class="row">
	<div class="col-sm-12">
		<h1>
			Students
		</h1>
	</div>
</div>
<div class="row">
	<div class="col-sm-offset-1 col-sm-10">
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
				        url: getContextPath()+'/user/view/student/',
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
					        	json.data[i]["email"] = '<a class="clickable" href="<%=request.getContextPath()%>/user/view/'+json.data[i]["id"]+'/">'+json.data[i]["email"]+'</a>';
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
<%@include file="footer.jsp"%>
