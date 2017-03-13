<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="<c:url value="/resources/js/jQuery.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/country.valid.js" />"></script>
<script>
$(document).ready(function(){
	var usermanagementmanagement = '<spring:message code="usermanagement.userRoleManagement" />';
	var usermanagementhave = '<spring:message code="usermanagement.haveRoles" />';
	var usermanagementnothave = '<spring:message code="usermanagement.notHaveRoles" />';
	var usermanagementremove = '<spring:message code="usermanagement.removeRole" />';
	var usermanagementadd = '<spring:message code="usermanagement.addRole" />';
	var usermanagementsave = '<spring:message code="usermanagement.save" />';
	var usermanagementback = '<spring:message code="usermanagement.back" />';

	var userManLocVar = [usermanagementmanagement, usermanagementhave, usermanagementnothave, 
	                   usermanagementremove, usermanagementadd, usermanagementsave, 
	                   usermanagementback];
	fLocaliz(userManLocVar);
});
</script>



<div class="container adminHome">
	<div class="row">
		<div class="col-md-6 col-offset-md-6">
			<h3 class="border-bottom-green"> <spring:message code="usermanagement.userRoleManagement" />
			 "${user.lastName}"</h3>
			<br>
			<!--label id="useridd">hello</label-->
			<!--form role="form" method="post" action="editRoles"
				commandName="roles"-->
				<div class="row">
				<div class="alert alert-success" id="userRolesManagmentResult">hello</div>
					<div class="col-md-6 col-sm-6">
						<label for="editRole" class="control-label"><spring:message code="usermanagement.haveRoles" /></label>
						<div class="form-group">
							<select name="drop1" id="haveRoles" size="8" class="form-control">
								<c:forEach var="role" items="${user.roles}">
									<option value="${role.id}">${role.name}</option>
								</c:forEach>
							</select>
						</div>
						<label id="removeRole" title="Button3"
							class="btn btn-sm btn-success"><spring:message code="usermanagement.removeRole" />  &#62;&#62;&#62;</label>
					</div>
					<div class="col-md-6 col-sm-6">
						<label for="editRole" class="control-label"><spring:message code="usermanagement.notHaveRoles" /></label>
						<div class="form-group">
							<select name="drop1" id="notHaveRoles" size="8"
								class="form-control">
								<c:forEach var="role" items="${exceptRoles}">
									<option value="${role.id}">${role.name}</option>
								</c:forEach>
							</select>
						</div>
						<label id="addRole" title="Button4"
							class="btn btn-sm btn-success">&#60;&#60;&#60; <spring:message code="usermanagement.addRole" /></label>

					</div>
				</div>
				<div class="form-group">

				</div>
				<br>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10 text-right">
						<!--a href="${pageContext.request.contextPath}/users/list"-->
							<button class="btn btn-success btn-sm" id="reff">
							<i class="glyphicon glyphicon-ok"></i> <spring:message code="usermanagement.save" />
						</button>

						<a href="${pageContext.request.contextPath}/users/list">
							<button type="button" class="btn btn-default btn-sm btn-danger"
								data-dismiss="modal">
								<i class="glyphicon glyphicon-remove"></i><spring:message code="usermanagement.back" />
							</button>
						</a>

					</div>
				</div>
			<!--/form-->
			<label id="userid" style="color: white;">${userID}</label>
		</div>
	</div>
</div>