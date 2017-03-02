<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script src="<c:url value="/resources/js/jQuery.js" />"></script>
<script src="<c:url value="/resources/js/editCountry.valid.js" />"></script>
<script>
$(document).ready(function(){
	var formatErrLocal = '<spring:message code="countriesPage.formatErrLocal" />';
	var caseErrLocal = '<spring:message code="countriesPage.caseErrLocal" />';
	var lengthErrLocal = '<spring:message code="countriesPage.lengthErrLocal" />';
	var emptyErrLocal = '<spring:message code="countriesPage.emptyErrLocal" />';
	var x = [formatErrLocal,caseErrLocal,lengthErrLocal,emptyErrLocal];
	fLocaliz(x);
});
</script>

<div class="container adminHome">
	<div class=row>
		<div class="col-md-6 col-offset-md-6">
			<h3 class="border-bottom-green">
				<spring:message code="editCountry.editTitle" />
			</h3>
			<form:form class="form-horizontal" id="main_form" role="form" method="post" action="edit/${country.id}" commandName="countryDTO">
				<div class="form-group">
					<label for="editRole" class="col-sm-4 control-label">
						<spring:message code="editCountry.countryName" />
					</label>
					 <input type="text" class="form-control"  hidden="true"
						id="editCountryId" name="id" value="${country.id}">
					<div class="col-sm-8">
						<input type="text" id="textbox_id" class="form-control" id="editCountry"
							name="name" value="${country.name}">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10 text-right">
					 
						<button type="submit" id="submit_id"  class="btn btn-success btn-sm">
							<i class="glyphicon glyphicon-ok"></i> 
							<spring:message code="editCountry.countrySave" />
						</button>
						<button type="button" id="cancel_id" class="btn btn-default btn-sm btn-danger"
								onclick="location.href='${pageContext.request.contextPath}/countries/countryList'"
								data-dismiss="modal">
							<i class="glyphicon glyphicon-remove"></i> 
								<spring:message code="editCountry.editCancel" />
						</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

