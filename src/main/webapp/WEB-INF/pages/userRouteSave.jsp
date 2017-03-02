<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="<c:url value="/resources/js/jQuery.js" />"></script>
<script>
	$(document).ready(function() {
		document.getElementById("validName").style.display = "none";
		$('#save').click(function() {
			var c = $('#nameFieldId').val();
			var fieldValid = /^[a-zA-Z ']{3,}$/;

			if ($('#nameFieldId').val() == "") {
				showLabel("validName");
				return false;
			}

			if (fieldValid.test(c) == false) {
				alert("Невірний формат вводу!");
				return false;
			}
		});
		function showLabel(label) {
			document.getElementById(label).style.display = "block";
			setTimeout(function() {
				hideLabel(label)
			}, 3000);
		}
		function hideLabel(label) {
			document.getElementById(label).style.display = "none";
		}
	});
</script>
<div class="container adminHome">
	<div class=row>
		<div class="col-md-6 col-offset-md-6">
			<h3 class="border-bottom-green">
				<spring:message code="saveRoute.title" />
			</h3>
			<c:if  test="${isValid==false}">  
                <div class="alert alert-danger">
                <c:if  test="${message!=''}">  
                	<spring:message code='${message}' /> 
                	</c:if>
                </div>     
            </c:if> 
			<form class="form-horizontal" role="form" method="post"
				action="savingRoute" commandName="routeConnectionDTO">
				<div class="form-group">
					<label class="col-sm-4 control-label"><spring:message
							code="saveRoute.routeName" /></label>
					<div class="col-sm-8">
						<font id="validName" size="2" color="red"><spring:message
								code="index.validator" /></font> <input type="text" id="nameFieldId"
							class="form-control" name="name"
							placeholder="<spring:message code="saveRoute.nameToEnter" />">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10 text-right">
						<button type="submit" id="save" class="btn btn-success btn-sm">
							<i class="glyphicon glyphicon-ok"></i>
							<spring:message code="saveRoute.save" />
						</button>
						<button type="button" class="btn btn-default btn-sm btn-danger"
							onClick=history.back() data-dismiss="modal">
							<i class="glyphicon glyphicon-remove"></i>
							<spring:message code="saveRoute.cancel" />
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>