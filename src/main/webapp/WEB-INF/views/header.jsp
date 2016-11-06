<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>SPMS</title>
		
		<!-- Shortcut -->
		<link href="/spms/resources/img/shortcut.ico" rel="shortcut icon">
		
		<!-- jQuery -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    	
    	<!-- jQuery DataTable -->
    	<script src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
    	
    	<!-- Bootstrap Core JavaScript -->
    	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    	
    	<!-- Bootstrap DataTable JavaScript -->
    	<script src="https://cdn.datatables.net/1.10.10/js/dataTables.bootstrap.min.js"></script>
    	
    	<!-- Bootstrap Core CSS -->
    	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    	
    	<!-- Bootstrap DataTable CSS -->
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.10/css/dataTables.bootstrap.min.css">
		
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
      					<a href="/spms/" class="navbar-brand" style="padding-top: 20px;">SPMS</a>
    				</div>
    				<div>
      					<ul class="nav navbar-nav">
        					<li>
        						<a href="/spms/createProject/">
    								<img src="/spms/resources/img/create_project.png" style="width:30px; height:30px">
    								Create project
    							</a>						
    						</li>
    						
        					<li>
        						<a href="/spms/reports/">
    								<img src="/spms/resources/img/view_reports.png" style="width:30px; height:30px">
    								View Reports
    							</a>						
    						</li>
        					<li>
        						<a href="/spms/archive/">
    								<img src="/spms/resources/img/project_archive.png" style="width:30px; height:30px">
    								Project Archive
    							</a>						
    						</li>
        					<li>
        						<a href="/spms/students/">
    								<img src="/spms/resources/img/students.png" style="width:30px; height:30px">
    								Students
    							</a>						
    						</li>
    						<li style="border: 1px;">
        						<a href="/spms/traitManager/">
    								<img src="/spms/resources/img/trait_manager.png" style="width:30px; height:30px">
    								Trait Manager
    							</a>						
    						</li>
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