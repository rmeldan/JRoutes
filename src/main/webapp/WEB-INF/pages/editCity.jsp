<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script src="<c:url value="/resources/js/jQuery.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/citiesValidation.js" />"></script>

<div class="container adminHome">
	<div class="row">
		<div class="col-md-6 col-offset-md-6">
			<h3 class="border-bottom-green"><spring:message code="cities.edit" /></h3>
			<form class="form-horizontal" role="form" method="post" action="edit" commandName="dto">
				<div class="form-group">
				    <label for="editRole" class="col-sm-3 control-label"><spring:message code="cities.city" /></label>
				    <div class="col-sm-9">
				    	<input type="text" class="form-control" hidden="true" id="text" name="id" value="${city.id}"> 
						<input type="text" id="textbox_id" class="form-control" name="name" value="${city.name}" placeholder=""> 
				    </div>
				</div>
				<div class="form-group">
				    <label for="editRole" class="col-sm-3 control-label"><spring:message code="cities.country" /></label> 
				    <div class="col-sm-9">
				    	<input type="text" class="form-control" hidden="true" id="editCityId" name="id" value="${city.id}">
						<select name="country" class="form-control">
							<c:forEach items="${countryList}" var="country">
								<option value="${country.id}">${country.name}</option>
							</c:forEach>
						</select>
				    </div>
				</div>
				<div class="form-group">
				    <div class="col-sm-offset-3 col-sm-9">
						<div class="checkbox">
						  <label>
						    <input type="checkbox" name="isBlocked" <c:if test="${city.isBlocked==true}"></c:if>> <spring:message code="cities.block" />
						  </label>
						</div>
					</div>
			  	</div>
			  	<div class="form-group">
				    <div class="col-sm-offset-3 col-sm-9">
				      <button type="submit" id="submit_id" class="btn btn-success btn-sm">
							<i class="glyphicon glyphicon-ok"></i> <spring:message code="cities.save" />
						</button>

						<button type="button" class="btn btn-default btn-sm btn-danger"
							onclick="location.href='${pageContext.request.contextPath}/cities/citiesList'"
							data-dismiss="modal">
							<i class="glyphicon glyphicon-remove"></i> <spring:message code="cities.cancel" />
						</button>
				    </div>
				</div>
			</form>
		</div>
</div>
</div>

