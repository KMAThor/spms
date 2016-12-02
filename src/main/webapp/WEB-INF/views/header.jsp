<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>SPMS</title>

	<!-- Shortcut -->
	<link href="<%=request.getContextPath()%>/resources/img/shortcut.ico" rel="shortcut icon">

	<!-- jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

	<!-- jQuery DataTable -->
	<script src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.2/moment.min.js"></script>
	
	<!-- Bootstrap Core JavaScript -->
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
	<!-- Datetimepicker JavaScript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/js/bootstrap-datetimepicker.min.js"></script>

	<!-- Bootstrap DataTable JavaScript -->
	<script src="https://cdn.datatables.net/1.10.10/js/dataTables.bootstrap.min.js"></script>

	<!-- Bootstrap Core CSS -->
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

	<!-- Bootstrap DataTable CSS -->
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.10/css/dataTables.bootstrap.min.css">
	
	<!-- Datetimepicker CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css">
	
	<!-- Icons -->
	<script src="https://use.fontawesome.com/7b15c48d6d.js"></script>
	
	<!-- Bootstrap Slider CSS -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/9.4.1/css/bootstrap-slider.min.css" type="text/css" rel="stylesheet">
	
	<!-- Bootstrap Slider Javascript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/9.4.1/bootstrap-slider.min.js"></script>
	
	<!-- CSS -->
	<link href="<%=request.getContextPath()%>/resources/css/styles.css" type="text/css" rel="stylesheet">
	
	<script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
	<script type="text/javascript">function getContextPath() { return "<%=request.getContextPath()%>";}</script>
</head>

<body>
<div class="container">
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header menuitem">
				<a href="<%=request.getContextPath()%>/" class="navbar-brand" style="padding-top: 20px;">SPMS</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<security:authorize access="hasAnyAuthority('admin','hr')">
					<li>
						<a href="<%=request.getContextPath()%>/reports/">
							<img src="<%=request.getContextPath()%>/resources/img/view_reports.png" style="width:30px; height:30px">
							View Reports
						</a>
					</li>
					</security:authorize>
					<!--<li>
                        <a href="<%=request.getContextPath()%>/archive/">
                            <img src="<%=request.getContextPath()%>/resources/img/project_archive.png" style="width:30px; height:30px">
                            Project Archive
                        </a>
                    </li>-->
                    <security:authorize access="hasAnyAuthority('admin','hr')">
					<li>
						<a href="<%=request.getContextPath()%>/user/">
							<img src="<%=request.getContextPath()%>/resources/img/students.png" style="width:30px; height:30px">
							Students
						</a>
					</li>
					</security:authorize>
					<security:authorize access="hasAuthority('admin')">
					<li style="border: 1px;">
							<a href="<%=request.getContextPath()%>/traitManager/">
								<img src="<%=request.getContextPath()%>/resources/img/trait_manager.png" style="width:30px; height:30px">
								Trait Manager
							</a>
					</li>
					</security:authorize>
				</ul>
			</div>
			<div>
			<form action="${logoutUrl}" method="post">
				<ul class="nav navbar-nav navbar-right">
					<li> 

						<p style="padding-top: 18px;">Logged in as: <strong><security:authentication property="principal.username"/></strong>
						</p>
					</li>
					<li>
					<c:url value="" var="logoutUrl" />
						<a href="<%=request.getContextPath()%>/j_spring_security_logout" style="padding-top: 18px;">Logout</a>
					
						
									<!--
						<c:url value="" var="logoutUrl" />
						<a href="<%=request.getContextPath()%>/j_spring_security_logout" style="padding-top: 18px;">Logout</a>
						<!-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> -->
					</li>
				</ul>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>	
			</div>
		</div>
	</nav>
</div>
<div class="container">