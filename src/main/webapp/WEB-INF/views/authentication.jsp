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
	
	<body class="background">
		<div class="container" style="width: 300px;">
    <c:url value="/j_spring_security_check" var="loginUrl" />
    <form action="${loginUrl}" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="form-control" name="j_username" placeholder="Email address" required="required" autofocus="autofocus">
        <input type="password" class="form-control" name="j_password" placeholder="Password" value="" required="required">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Enter</button>
    </form>
</div>
	</body>
</html>