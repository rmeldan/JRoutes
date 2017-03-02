<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="container adminHome">
	<div class=row>
		<div class="col-md-6 col-offset-md-6">
			<h3 class="border-bottom-green">
				<spring:message code="editRouteConn.editConnection" />
			</h3>

			<form:form action="${pageContext.request.contextPath}/updateRC/${id}"
				method="POST" role="form" commandName="dto">
				<c:if test="${not empty dangerResultEdit}">
					<div class="alert alert-danger"><spring:message code="${dangerResultEdit}" /></div>
				</c:if>
				<div class="row">
					<div class="col-md-6 col-sm-6">
						<div class="from-to">
							<spring:message code="editRouteConn.from" />
						</div>
						<div class="form-group">
							<select  name="countryA" class="form-control"
								id="fromCountryEditRC">
								<c:forEach items="${countryList}" var="country">
									<option value="${country.id}" 
										<c:if test="${countryAId==country.id}">selected </c:if>>${country.name}</option>
								</c:forEach>
							</select>
						</div>

						<div class="form-group">
							<font color="red"><form:errors path="routePointA" /></font> <form:select 
								path="routePointA" class="form-control" id="fromCityEditRC">
								<c:forEach items="${routePointFrom}" var="routePoint">
									<option value="${routePoint.id}"
										<c:if test="${pointAId==routePoint.id}">selected </c:if>>${routePoint.name}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="col-md-6 col-sm-6">
						<div class="from-to">
							<spring:message code="editRouteConn.to" />
						</div>
						<div class="form-group">
							<select  name="countryB" class="form-control" id="toCountryEditRC">
								<c:forEach items="${countryList}" var="country">
									<option value="${country.id}"
										<c:if test="${countryBId==country.id}">selected </c:if>>${country.name}</option>
								</c:forEach>
							</select>
						</div>
						<font color="red"><form:errors path="routePointB" /></font>
						<form:select  path="routePointB" class="form-control"
							id="toCityEditRC">
							<c:forEach items="${routePointTo}" var="routePoint">
								<option value="${routePoint.id}"
									<c:if test="${pointBId==routePoint.id}">selected </c:if>>${routePoint.name}</option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<div class="row">
						<div class="col-md-6">
							<div class="title">
								<spring:message code="editRouteConn.price" />
							</div>
							<font color="red"><form:errors path="price" /></font>
							<form:input class="form-control" path="price" placeholder="price"
								value="${price}" />
						</div>
						<div class="col-md-6">
							<div class="title">
								<spring:message code="editRouteConn.time" />
							</div>
							<font color="red"><form:errors path="time" /></font>
							<form:input class="form-control" path="time" placeholder="time"
								value="${time}" />
						</div>
						<div class="col-md-6">
							<span class="title">
							<spring:message code="editRouteConn.isBlocked" />
							 </span> <input type="checkbox"
								name="isBlocked" <c:if test="${isBlocked==true}">checked </c:if>>
						</div>


					</div>
				</div>
				<div class="form-group">
					<div class="title">
					<spring:message code="editRouteConn.transport" />
					</div>
					<select name="transport" class="form-control">
						<c:forEach items="${transportList}" var="transport">
							<option value="${transport.id}"
								<c:if test="${transportId==transport.id}">selected </c:if>>${transport.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">

					<div class="text-right">
						<button type="submit" class="btn btn-success btn-sm">
							<i class="glyphicon glyphicon-ok"></i> 
							<spring:message code="editRouteConn.edit" />
						</button>
						<span class="cancelRC"><button type="button"
								class="btn btn-default btn-sm btn-danger" data-dismiss="modal"
								onclick="location.href='${pageContext.request.contextPath}/routeconnection'">
								<i class="glyphicon glyphicon-remove"></i> 
								<spring:message code="editRouteConn.cancel" />
							</button></span>
					</div>
				</div>
			</form:form>


		</div>
	</div>
</div>




<script>
	$(document)
			.ready(
					function() {

						$('#fromCountryEditRC').change(function() {
							SelectComboRCC(this, 'fromCityEditRC');

						});
						$('#toCountryEditRC').change(function() {
							SelectComboRCC(this, 'toCityEditRC');

						});
						function SelectComboRCC(combo1, combo2) {
							comboBox2 = document.getElementById(combo2);
							ClearComboRCC(comboBox2);

							$
									.ajax({
										url : '${pageContext.request.contextPath}/getCities.html',
										datatype : 'json',
										data : ({
											sendValue : combo1.options[combo1.selectedIndex].value
										}),
										success : function(resultDo) {
											var myObject = eval('(' + resultDo
													+ ')');
											FillComboRCC(myObject, comboBox2);
										}
									});
						}

						function ClearComboRCC(combo) {
							while (combo.length > 0) {
								combo.remove(combo.length - 1);
							}
						}

						function FillComboRCC(json, combo) {
							for (var i = 0; i < json.length; i++) {
								combo.options[combo.length] = new Option(
										json[i].name, json[i].id);
							}
						}

					});
</script>


















