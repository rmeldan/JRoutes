<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<div class="container adminHome">
	<div id="home">
		<div class="row">
			<div class="col-md-6 col-sm-6">
				<h2 class="border-bottom-green">Особисті дані</h2>

				<div class="form-group border-bottom">
					<span class="pill-title">First name:</span><span>${user.firstName}</span>
				</div>
				<div class="form-group border-bottom">
					<span class="pill-title">Last name:</span><span>${user.lastName}</span>
				</div>
				<div class="form-group border-bottom">
					<span class="pill-title">Email:</span><span>${user.email}</span>
				</div>
				
					<FONT color="green"><h1>${data}</h1></FONT>
				
				<!--div class="alert alert-success" id="userEditingMsg">hello</div-->
			
			</div>
			<div class="col-md-6 col-sm-6">
				<h2 class="border-bottom-green">Редагувати особисті дані</h2>
				<form:form class="form-horizontal" role="form" id="admin-edit"
					method="post" action="editProfile" commandName="userDTO">

					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">Ім'я</label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control" path="firstName"
								id="inputEmail" placeholder="First name" />
							<span class="help-inline"><FONT color="red"><form:errors
										path="firstName" /></FONT></span>
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">Прізвище</label>
						<div class="col-sm-10">
							<form:input type="text" class="form-control" path="lastName"
								id="inputEmail" placeholder="Last name" />
							<span class="help-inline"><FONT color="red"><form:errors
										path="lastName" /></FONT></span>
						</div>
					</div>
				
					<div class="form-group">
						<label for="password" class="col-sm-2 control-label">Пароль</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" name="password"
								id="inputPassword" placeholder="Пароль"> <span
								class="help-inline"><FONT color="red"><form:errors
										path="password" /></FONT></span>
						</div>
					</div>
					<div class="form-group">
						<label for="confirm-password" class="col-sm-2 control-label">Повторити
							пароль</label>
						<div class="col-sm-10">
							<input type="password" class="form-control"
								name="confirmPassword" id="inputPasswordConfirm"
								placeholder="Пароль">
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button id="userEditSubmitButton" type="submit" class="btn btn-success btn-sm">
								<i class="glyphicon glyphicon-check"></i> Зберегти зміни
							</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
