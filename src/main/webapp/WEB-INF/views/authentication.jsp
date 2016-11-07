<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

	<head>
		<title>SPMS</title>
		
		<!-- Shortcut -->
		<link href="/spms/resources/img/shortcut.ico" rel="shortcut icon">
		
		<!-- jQuery -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    	
    	<!-- Bootstrap Core JavaScript -->
    	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    	
    	<!-- Bootstrap Core CSS -->
    	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    	
    	<!-- CSS -->
    	<link href="/spms/resources/css/styles.css" type="text/css" rel="stylesheet">
	</head>
	</body>
	<body class="background">
	
		<div class="main">
			<div class="content">
        		<div class="login-panel panel panel-default">
            		<div class="panel-heading">
                    	<h1 class="panel-title">Sign In</h1>
                	</div>
                	<div class="panel-body">
                	<c:url value="/j_spring_security_check" var="loginUrl" />
                    	 <form action="${loginUrl}" method="post">
                        	<fieldset>
                            	<div class="form-group">
                               		<input class="form-control" placeholder="Email addresse" name="j_username" type="text" required="required" autofocus="autofocus"/>
                            	</div>
                            	<div class="form-group">
                                	<input class="form-control" placeholder="Password" name="j_password" type="password" value="" required="required"/>
                            	</div>
                            	<button type="submit" class="btn btn-sm btn-default">Sign in</button>
                        	</fieldset>
                    	</form>
                	</div>
            	</div>
        	</div>
    	</div>
	</body>
	
</html>