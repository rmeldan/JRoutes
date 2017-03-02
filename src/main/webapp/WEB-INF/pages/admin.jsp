<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div id="entrance">
		<div id="entrance-title">Enter to system</div>
		<form:form class="login-form" action="j_spring_security_check"
			method="post">
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
				<div class= "alert alert-danger"> Your login attempt was not successful due
					to
				<c:out value="wrong email or password" />.
				</div>
			</c:if>
			<div class="form-group">
				<label for="j_username">Email address</label> <input type="email"
					class="form-control" id="j_username" name="j_username"
					placeholder="Enter email">
			</div>
			<div class="form-group">
				<label for="j_password">Password</label> <input type="password"
					class="form-control" id="j_password" name="j_password"
					placeholder="Password">
			</div>
			<input type="submit" class="btn btn-success" value="Login" />
			<button type="button"
				onclick="location.href='${pageContext.request.contextPath}'"
				class="btn btn-danger">Cancel</button>



		</form:form>
	</div>
</div>


</body>
</html>