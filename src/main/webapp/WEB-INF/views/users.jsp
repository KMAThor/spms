<%@include file="header.jsp"%>
<div class="row">
	<div class="col-sm-12">
		<h1>
			Users
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
				$('#usersTable').DataTable( {

				    serverSide: true,
			        ajax: {
				        url: '<c:url value="/view/users/"/>',
				        type: 'POST',
				        data: function ( d ) {
						      return JSON.stringify( d );
						    },	        
					    contentType: "application/json; charset=utf-8",
					    dataType: "json"
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
