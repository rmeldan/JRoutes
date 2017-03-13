<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
$( document ).ready(function() {
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-z]+$/i.test(value);
		}, '<spring:message code="transport.error" />'); 
	$("#form-transport").validate({
		rules:{
			name:{
				required:true,
				minlength:2,
				lettersonly: true
			}
			
		},
		messages:{
			name:{
				required:'<spring:message code="countriesPage.emptyErrLocal" />',
				minlength: '<spring:message code="transport.errorSymbols" />'

			}
		}
		
	});
});
</script>
<div class="container adminHome">
	<div class=row>
		<div class="col-md-6 col-offset-md-6">
			<h3 class="border-bottom-green"><spring:message code="transport.editName" /></h3>
			<form class="form-horizontal" role="form" method="post" action="edit"
				commandName="transportDTO" id="form-transport">
				<div class="form-group">
					<label class="col-sm-4 control-label"><spring:message code="transport.name" /></label> 
						<input type="text" class="form-control" hidden="true" id="editTransportId" name="id" value="${transport.id}">
					<div class="col-sm-8">
						<input type="text" class="form-control" id="saveUserRoute" name="name" value="${transport.name}">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10 text-right">
						<button type="submit" class="btn btn-success btn-sm">
							<i class="glyphicon glyphicon-ok"></i> <spring:message code="transport.save" />
						</button>
						<button type="button" class="btn btn-default btn-sm btn-danger"
							onclick="location.href='${pageContext.request.contextPath}/transport/transportList'"
							data-dismiss="modal">
							<i class="glyphicon glyphicon-remove"></i> <spring:message code="transport.close" />
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

