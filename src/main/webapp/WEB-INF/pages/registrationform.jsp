<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Home page</title>
<link href='<c:url value="/resources/css/bootstrap.css" />'
	rel="stylesheet">
<link href='<c:url value="/resources/css/component.css" />'
	rel="stylesheet">
<link href='<c:url value="/resources/css/normalize.css" />'
	rel="stylesheet">
<link href='<c:url value="/resources/css/style.css" />' rel="stylesheet">
<link href='<c:url value="/resources/fonts/fonts.css" />'
	rel="stylesheet">

<!--[if lt IE 9]>
    <script src="<c:url value="/resources/js/html5shiv.js" />"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h2 class="modal-title" id="myModalLabel-register">Registration
					form</h2>
			</div>
			<div class="modal-body">
				<form:form class="form-horizontal" role="form" method="post"
					action="registrationUser" commandName="user">
					<div class="form-group">
						<label for="inputEmail" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control" path="email"
								id="inputEmail" placeholder="Email" />
							<span class="help-inline"><FONT color="red"><form:errors
										path="email" /></FONT></span>
						</div>
					</div>
					<div class="form-group">
						<label for="inputName" class="col-sm-2 control-label">First&nbspname</label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control" path="firstName"
								id="inputName" placeholder="Ім'я" />
							<span class="help-inline"><FONT color="red"><form:errors
										path="firstName" /></FONT></span>
						</div> 
					</div>
					<div class="form-group">
						<label for="inputLastName" class="col-sm-2 control-label">Last&nbspname</label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control" path="lastName"
								id="inputLastName" placeholder="Прізвище" />
							<span class="help-inline"><FONT color="red"><form:errors
										path="lastName" /></FONT></span>
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-sm-2 control-label">Password</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" name="password"
								id="inputPassword" placeholder="Пароль"> <span
								class="help-inline"><FONT color="red"><form:errors
										path="password" /></FONT></span>
						</div>
					</div>
					<div class="form-group">
						<label for="inputPasswordConfirm" class="col-sm-2 control-label">Confirm
							password</label>
						<div class="col-sm-10">
							<input type="password" class="form-control"
								name="confirmPassword" id="inputPasswordConfirm"
								placeholder="Пароль">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" onclick="location.href='${pageContext.request.contextPath}'"
							class="btn btn-danger">Cancel</button>
						<button type="submit" class="btn btn-success">Registration</button>
					</div>
				</form:form>
			</div>

		</div>
	</div>
	<script src="<c:url value="/resources/js/jQuery.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.js" />"></script>
</body>
</html>
