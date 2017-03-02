<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(document)
			.ready(
					function() {
						$.validator.addMethod("rolefirstletter", function(
								value, element) {
							return this.optional(element)
									|| /^[A-Za-z0-9]/i.test(value);
						}, "Перший символ повинен бути буквою або цифрою");

						$.validator.addMethod("rolelastletter", function(value,
								element) {
							return this.optional(element)
									|| /[A-Za-z0-9]$/i.test(value);
						}, "Останній символ повинен бути буквою або цифрою");

						$.validator
								.addMethod(
										"rolecheckinput",
										function(value, element) {
											return this.optional(element)
													|| /^[A-Za-z0-9][A-Za-z0-9_\-]*[A-Za-z0-9]$/i
															.test(value);
										},
										"Назва ролі може містити букви, цифри та символи '_', '-'");
						$("#addingRoleForm")
								.validate(
										{
											rules : {
												name : {
													required : true,
													minlength : 2,
													rolefirstletter : true,
													rolelastletter : true,
													rolecheckinput : true,
												}
											},
											messages : {
												name : {
													required : "Це поле не може бути пусте",
													minlength : "Назва не може бути менша ніж 2 символи"
												}
											}
										});

					});
</script>

<div class="container adminHome">
	<div id="roles">
		<div class="row">
			<div class="col-md-8">
				<h2 class="border-bottom-green">
					<i class="glyphicon glyphicon-star-empty"></i> Ролі
				</h2>
				<div class="transport">
					<c:if test="${messageType=='INFO'}">
						<div class="alert alert-success">${roleMessage}</div>
					</c:if>
					<c:if test="${messageType=='ERROR'}">
						<div class="alert alert-danger">${roleMessage}</div>
					</c:if>
					<ul class="list-group">

						<c:forEach var="role" items="${roles}">
							<!--li class="list-group-item"-->
							<li class="list-group-item"><span class="badge badge-danger">
									<a class="del-role" id="${role.id}" name="${role.name}"
									href="#"> <i class="glyphicon glyphicon-trash"></i></a>
							</span> <span class="badge badge-warning"><a
									href="editForm/${role.id}"><i
										class="glyphicon glyphicon-edit"></i></a></span> <c:out
									value="${role.name}" /></li>
						</c:forEach>
					</ul>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="editTransport" tabindex="-1"
					role="dialog" aria-labelledby="TransportLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h3 class="modal-title" id="TransportLabel">Редагувати</h3>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
									<div class="form-group">
										<label for="inputSR" class="col-sm-6 control-label">Назва
											транспорту</label>
										<div class="col-sm-6">
											<input type="text" class="form-control" id="inputTransport"
												value="Літак">
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-2 col-sm-10 text-right">
											<button type="button"
												class="btn btn-default btn-sm btn-danger"
												data-dismiss="modal">
												<i class="glyphicon glyphicon-remove"></i> Відмінити
											</button>
											<button type="submit" class="btn btn-success btn-sm">
												<i class="glyphicon glyphicon-ok"></i> Зберегти
											</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<h2 class="border-bottom-green">
					<i class="glyphicon glyphicon-ok"></i> Додати роль
				</h2>
				<form class="form-horizontal" role="form" method="post" action="add"
					id="addingRoleForm" commandName="securityRole">
					<div class="form-group">
						<label for="inputSR" class="col-sm-3 control-label">Назва
							ролі</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="inputSecurityRole"
								name="name" placeholder="Введіть роль">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10 text-right">
							<button type="submit" class="btn btn-success btn-sm">
								<i class="glyphicon glyphicon-ok"></i> Зберегти
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
