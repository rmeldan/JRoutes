<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<h3 class="modal-title" id="securityRoleLabel">Видалити роль
				"${role.name}"?</h3>
		</div>
		<div class="modal-body">
			<form class="form-horizontal" role="form" method="post"
				action="delete" commandName="securityRole">
				<div class="form-group">
					<input type="text" class="form-control" id="deleteRoleId"
						hidden="true" name="id" value="${role.id}"> 
					<input type="text" class="form-control" id="deleteRoleName"
						hidden="true" name="name" value="${role.name}"> 
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10 text-right">
						<a href="${pageContext.request.contextPath}/securityRoles/roles">
							<button type="button" class="btn btn-default btn-sm btn-danger"
								data-dismiss="modal">
								<i class="glyphicon glyphicon-remove"></i> Відмінити
							</button>
						</a>
						<button type="submit" class="btn btn-success btn-sm">
							<i class="glyphicon glyphicon-ok"></i> Видалити
						</button>

					</div>
				</div>
			</form>
		</div>
	</div>
</div>