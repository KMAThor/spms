<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>SPMS</title>
		
		<!-- Shortcut >
		<link href="/resources/img/shortcut.ico" rel="shortcut icon"-->
		
		<!-- jQuery -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    	
    	<!-- Bootstrap Core JavaScript -->
    	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    	
    	<!-- Bootstrap Core CSS -->
    	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    	<!-- Icons -->
        <script src="https://use.fontawesome.com/7b15c48d6d.js"></script>
    	<!-- CSS -->
    	<link href="/spms/resources/css/styles.css" type="text/css" rel="stylesheet">
        <script src="/spms/resources/js/main.js"></script>
	</head>
	
	<body>
		<div class="container">
			<nav class="navbar navbar-default">
  				<div class="container-fluid">
    				<div class="navbar-header menuitem">
      					<a class="navbar-brand" href="#">SPMS</a>
    				</div>
    				<div>
      					<ul class="nav navbar-nav">
        					<li><a href="#">Main page</a></li>
        					<li><a href="#">Item 1</a></li>
        					<li><a href="#">Item 2</a></li>
        					<li><a href="#">Item 3</a></li>
      					</ul>
    				</div>
    				<div>  
    					<ul class="nav navbar-nav navbar-right">
    						<li>
    						<c:url value="" var="logoutUrl" />							
        						<a href="j_spring_security_logout">Logout</a>
        					</li>
      					</ul>
    				</div>
  				</div>
			</nav>
		</div>