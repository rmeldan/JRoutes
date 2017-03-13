<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$( document ).ready(function() {
	$("#editSecurityRole").validate({
		rules:{
			name:{
				required:true,
				minlength:2,
				rolefirstletter:true,
				rolelastletter:true,
				rolecheckinput:true,
			}		
		},
		messages:{
			name:{
				required:"Це поле не може бути пусте",
				minlength: "Назва не може бути менша ніж 2 символа"
			}
		}
		
	});
});
</script>

<div class="container adminHome">
	<div class="row">
		<div class="col-md-6 col-offset-md-6">
			<h3 class="border-bottom-green">Редагувати назву ролі</h3>
			<form class="form-horizontal" role="form" method="post" action="edit"
				commandName="securityRole" id="editSecurityRole">
				<div class="form-group">
					<label for="editRole" class="col-sm-6 control-label">Назва
						ролі</label> <input type="text" class="form-control" id="editRoleId"
						hidden="true" name="id" value="${role.id}">
					<div class="col-sm-6">
						<input type="text" class="form-control" id="editRole" name="name"
							value="${role.name}">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10 text-right">
						<button type="submit" class="btn btn-success btn-sm">
							<i class="glyphicon glyphicon-ok"></i> Зберегти
						</button>
						<a href="${pageContext.request.contextPath}/securityRoles/roles">
							<button type="button" class="btn btn-default btn-sm btn-danger"
								data-dismiss="modal">
								<i class="glyphicon glyphicon-remove"></i> Відмінити
							</button>
						</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
